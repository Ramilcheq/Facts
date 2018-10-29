package Facts.MyBatis;

public class ExprDB {
    private int id;
    private String type;
    private String fact;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getFact() {
        return fact;
    }

    public ExprDB(int id, Object type, String fact) {
        this.id = id;
        this.type = (String) type;
        this.fact = fact;
    }
}
