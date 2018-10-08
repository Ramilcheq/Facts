import java.util.*;
import java.util.regex.Pattern;

public class Solver {
    private static final String SEPARATOR = "----------------------------------------------------------------";
    private List<Rule> rules = new ArrayList<>();
    private Set<Fact> trueFacts = new HashSet<>();

    Set<Fact> getAllFacts(List<String> strings) throws IncorrectLineException {
        extractRulesFactsFromStrings(strings);
        boolean noChanges = true;
        while (true) {
            for (Rule rule : rules) {
                if (rule.isSolved()) continue;
                if (solveRule(rule)) {
                    trueFacts.add(rule.getUndefinedFact());
                    rule.setSolved(true);
                    noChanges = false;
                }
            }
            if (noChanges) break;
            noChanges = true;
        }
        return trueFacts;
    }

    private void extractRulesFactsFromStrings(List<String> strings) throws IncorrectLineException {
        boolean afterSeparator = false;
        for (int lineNumber = 0; lineNumber < strings.size(); lineNumber++) {
            if (isEmptyOrSpaces(strings.get(lineNumber))) {
                continue;
            }
            if (strings.get(lineNumber).equals(SEPARATOR)) {
                afterSeparator = true;
                continue;
            }
            if (afterSeparator) {
                if (Parser.parseFacts(strings.get(lineNumber)) == null) {
                    throw new IncorrectLineException("Error in facts, line #", (lineNumber + 1));
                }
                trueFacts.addAll(Parser.parseFacts(strings.get(lineNumber)));
            } else {
                if (Parser.parseRule(strings.get(lineNumber)) == null) {
                    throw new IncorrectLineException("Error in line #", (lineNumber + 1));
                }
                rules.add(Parser.parseRule(strings.get(lineNumber)));
            }
        }
    }

    private boolean isEmptyOrSpaces(String s) {
        return Pattern.compile("\\s*").matcher(s).matches();
    }

    private boolean solveRule(Rule rule) {
        if (rule.getFactList().size() == 1)
            return rule.getFactList().get(0).isTrueFact(trueFacts);
        else {
            int prevORIndex = 0;
            boolean ANDresult = true;
            boolean result = false;
            for (int i = 0; i < rule.getOperatorList().size(); i++) {
                if (rule.getOperatorList().get(i) == Operator.OR) {
                    for (int j = prevORIndex; j <= i; j++) {
                        ANDresult = ANDresult && rule.getFactList().get(j).isTrueFact(trueFacts);
                    }
                    result = result || ANDresult;
                    ANDresult = true;
                    prevORIndex = i + 1;
                }
            }
            for (int i = prevORIndex; i < rule.getFactList().size(); i++) {
                ANDresult = ANDresult && rule.getFactList().get(i).isTrueFact(trueFacts);
            }
            return result || ANDresult;
        }
    }
}
