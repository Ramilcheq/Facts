import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Rule {
    private List<Fact> factList;
    private List<Operator> operatorList;
    private Fact undefinedFact;
    private boolean isSolved = false;

    Rule() {
        factList = new ArrayList<>();
        operatorList = new ArrayList<>();
        undefinedFact = new Fact("");
        isSolved = false;
    }

    List<Fact> getFactList() {
        return factList;
    }

    List<Operator> getOperatorList() {
        return operatorList;
    }

    boolean isSolved() {
        return isSolved;
    }

    void setSolved(boolean solved) {
        isSolved = solved;
    }

    void setUndefinedFact(Fact undefinedFact) {
        this.undefinedFact = undefinedFact;
    }

    Fact getUndefinedFact() {
        return undefinedFact;
    }

    Rule(List<Fact> factList, List<Operator> operatorList) {
        this.factList = factList;
        this.operatorList = operatorList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(factList, rule.factList) &&
                Objects.equals(operatorList, rule.operatorList) &&
                Objects.equals(undefinedFact, rule.undefinedFact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(factList, operatorList, undefinedFact);
    }
}
