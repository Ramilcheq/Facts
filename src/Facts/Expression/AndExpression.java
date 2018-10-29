package Facts.Expression;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;
import java.util.Set;

public class AndExpression implements Expression {
    @XmlElement(type = FactExpression.class, name = "Fact")
    private List<Expression> expressions;

    public AndExpression(){}
    public AndExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }

    // Вычисление "И"-выражения
    @Override
    public boolean evaluate(Set<String> trueFacts) {
        for (Expression expr: expressions){
            if(!expr.evaluate(trueFacts))
                return false;
        }
        return true;
    }
}
