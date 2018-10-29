package Facts.MyBatis;

public class RuleDB {
    private int id;
    private String resultFact;

    public RuleDB(int id, String resultFact) {
        this.id = id;
        this.resultFact = resultFact;
    }

    public int getId() {
        return id;
    }

    public String getResultFact() {
        return resultFact;
    }
}
