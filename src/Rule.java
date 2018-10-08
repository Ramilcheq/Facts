import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Rule {
    private List<Fact> factList;
    private List<Operator> operatorList;
    private Fact undefinedFact;
    private boolean isSolved = false;

    public Rule() {
        factList = new ArrayList<>();
        operatorList = new ArrayList<>();
        undefinedFact = new Fact("");
        isSolved = false;
    }

    public List<Fact> getFactList() {
        return factList;
    }

    public List<Operator> getOperatorList() {
        return operatorList;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public void setUndefinedFact(Fact undefinedFact) {
        this.undefinedFact = undefinedFact;
    }

    public Fact getUndefinedFact() {
        return undefinedFact;
    }

//    public Rule() {
//        factList = new ArrayList<>();
//        operatorList = new ArrayList<>();
//    }
//
//    public Rule(List<Fact> factList) {
//        this.factList = factList;
//        this.operatorList = new ArrayList<>();
//    }

    public Rule(List<Fact> factList, List<Operator> operatorList) {
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
