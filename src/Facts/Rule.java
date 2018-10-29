package Facts;

import Facts.Expression.AndExpression;
import Facts.Expression.Expression;
import Facts.Expression.FactExpression;
import Facts.Expression.OrExpression;

import javax.xml.bind.annotation.*;
import java.util.*;

@XmlRootElement
public class Rule {
    @XmlElements({
            @XmlElement(type = FactExpression.class, name = "Fact"),
            @XmlElement(type = AndExpression.class, name = "And"),
            @XmlElement(type = OrExpression.class, name = "Or")
    })
    private Expression expression;
    @XmlElement(name = "Result")
    private String resultFact;

    public Rule() {
    }

    String getResultFact() {
        return resultFact;
    }

    public Rule(Expression expression, String resultFact) {
        this.expression = expression;
        this.resultFact = resultFact;
    }

    // Вычисление правила
    boolean solveRule(Set<String> trueFacts) {
        return expression.evaluate(trueFacts);
    }
}
