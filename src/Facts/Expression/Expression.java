package Facts.Expression;

import java.util.Set;

public interface Expression {
    boolean evaluate(Set<String> facts);
}
