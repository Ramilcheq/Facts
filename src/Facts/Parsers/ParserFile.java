package Facts.Parsers;

import Facts.*;
import Facts.Expression.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserFile implements IParser {
    private static final String SEPARATOR = "----------------------------------------------------------------";
    private int lineNumber;

    // Чтение файла и создание модели
    public Model parse(String source[]) throws Exception {
        List<Rule> rules = new ArrayList<>();
        Set<String> facts = new HashSet<>();
        lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(
                new FileReader(source[1]))) {
            String line;
            ReaderState state = ReaderState.READING_EXPRESSIONS;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (isEmptyOrSpaces(line)) {
                    continue;
                }
                switch (state) {
                    case READING_EXPRESSIONS:
                        if (line.equals(SEPARATOR)) {
                            state = ReaderState.READING_FACTS;
                        } else {
                            rules.add(parseRule(line));
                        }
                        break;
                    case READING_FACTS:
                        for (String fact : line.split(",")) {
                            facts.add(parseFact(fact));
                        }
                        state = ReaderState.END;
                        break;
                    case END:
                        throw new IncorrectLineException("Error in line #" + lineNumber + ": facts already read");
                }
            }
            return new Model(rules, facts);
        }
    }

    // Парсинг правила
    private Rule parseRule(String line) throws IncorrectLineException {
        String[] splitArrow = line.split("->");
        if (splitArrow.length != 2)
            throw new IncorrectLineException("Error in line #" + lineNumber);

        return new Rule(parseExpression(splitArrow[0]), parseFact(splitArrow[1]));
    }

    // Парсинг "ИЛИ"-выражения
    private Expression parseExpression(String line) throws IncorrectLineException {
        String[] andParts = line.split("\\|\\|");
        if (andParts.length == 1)
            return parseAndExpression(line);

        List<Expression> andExprList = new ArrayList<>();
        for (String part : andParts) {
            andExprList.add(parseAndExpression(part));
        }
        return new OrExpression(andExprList);
    }

    // Парсинг "И"-выражения
    private Expression parseAndExpression(String line) throws IncorrectLineException {
        String[] factParts = line.split("&&");
        if (factParts.length == 1)
            return new FactExpression(parseFact(line));

        List<Expression> factExprList = new ArrayList<>();
        for (String part : factParts) {
            factExprList.add(new FactExpression(parseFact(part)));
        }
        return new AndExpression(factExprList);
    }

    // Парсинг выражения-факта
    private String parseFact(String line) throws IncorrectLineException {
        line = line.trim();
        Matcher m = Pattern.compile(FACT_PATTERN)
                .matcher(line);
        if (!m.matches())
            throw new IncorrectLineException("Error in fact, line #" + lineNumber);

        return line;
    }

    // Проверка строки на пустоты и пробелы
    private boolean isEmptyOrSpaces(String s) {
        return Pattern.compile("\\s*").matcher(s).matches();
    }
}
