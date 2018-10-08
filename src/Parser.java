import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final String FACT_NAME = "((\\_+[A-Za-z]+\\w*)|([A-Za-z]+\\w*))";

    static Rule parseRule(String line) {
        Matcher m2 = Pattern.compile(FACT_NAME)
                .matcher(line);
        if (m2.matches()){
            Rule rule = new Rule();
            rule.setSolved(true);
            return rule;
        }
        Matcher m1 = Pattern.compile("\\s*(" + FACT_NAME + "\\s*((\\|\\|)|(&&)))*\\s*" + FACT_NAME + "\\s*->\\s*" + FACT_NAME + "\\s*")
                .matcher(line);
        if (!m1.matches()) return null;

        List<Fact> facts = getRules(line);
        Fact undefinedFact = facts.get(facts.size() - 1);
        facts.remove(facts.size() - 1);
        Rule rule = new Rule(facts, getOperators(line));
        rule.setUndefinedFact(undefinedFact);
        return rule;
    }

    static Set<Fact> parseFacts(String line){
        Matcher m = Pattern.compile("\\s*(" + FACT_NAME + "\\s*,\\s*)*(\\s*" + FACT_NAME + "\\s*)?")
                .matcher(line);
        if (!m.matches()) return null;
        Set<Fact> factSet = new HashSet<>();
        for(String fact: line.split("\\s*,\\s*")){
            factSet.add(new Fact(fact.trim()));
        }
        return factSet;
    }

    private static List<Fact> getRules(String s) {
        List<Fact> facts = new ArrayList<>();
        for (String fact : s.split("\\s*\\|\\|\\s*|\\s*&&\\s*|\\s*->\\s*")) {
            facts.add(new Fact(fact));
        }
        return facts;
    }

    private static List<Operator> getOperators(String s) {
        List<Operator> operators = new ArrayList<>();
        Matcher m = Pattern.compile("\\|\\||&&")
                .matcher(s);
        while (m.find()) {
            operators.add(m.group().equals("&&") ? Operator.AND : Operator.OR);
        }
        return operators;
    }
}
