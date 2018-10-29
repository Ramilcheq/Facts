package Facts.Expression;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Set;

public class FactExpression implements Expression {
    @XmlAttribute
    private String fact;

    public FactExpression() {
    }

    public FactExpression(String fact) {
        this.fact = fact;
    }

    // Вычисление выражения-факта
    @Override
    public boolean evaluate(Set<String> trueFacts) {
        return trueFacts.contains(fact);
    }
}
