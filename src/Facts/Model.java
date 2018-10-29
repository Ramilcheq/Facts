package Facts;

import java.util.*;

public class Model {

    private List<Rule> rules;

    public List<Rule> getRules() {
        return rules;
    }

    public Set<String> getTrueFacts() {
        return trueFacts;
    }

    private Set<String> trueFacts;

    public Model(List<Rule> rules, Set<String> facts) {
        this.rules = rules;
        this.trueFacts = facts;
    }

    // Вычисление всех правил в соответствии с фактами
    public Set<String> solve() {
        boolean wereChanges;
        do {
            wereChanges = false;
            Iterator<Rule> ruleIterator = rules.iterator();
            while (ruleIterator.hasNext()) {
                Rule rule = ruleIterator.next();
                if (rule.solveRule(trueFacts)) {
                    trueFacts.add(rule.getResultFact());
                    ruleIterator.remove();
                    wereChanges = true;
                }
            }
        } while (wereChanges);

        return trueFacts;
    }
}
