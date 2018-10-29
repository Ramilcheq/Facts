package Facts.Expression;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Set;

public class OrExpression implements Expression {
    @XmlElements({
            @XmlElement(type = FactExpression.class, name = "Fact"),
            @XmlElement(type = AndExpression.class, name = "And")
    })
    private List<Expression> expressions;

    public OrExpression(){}

    public OrExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }

    // Вычисление "ИЛИ"-выражения
    @Override
    public boolean evaluate(Set<String> trueFacts) {
        for (Expression expr: expressions){
            if (expr.evaluate(trueFacts))
                return true;
        }
        return false;
    }
}
