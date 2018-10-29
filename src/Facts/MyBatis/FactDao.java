package Facts.MyBatis;

import Facts.RuleResultHandler;
import org.apache.ibatis.session.SqlSession;

import java.util.List;


public class FactDao {
    private String dbDriver;
    private String dbUrl;
    private String user;
    private String password;

    public FactDao(String source[]) throws Exception {
        this.dbDriver = getDbDriver(source[1]);
        this.dbUrl = source[2];
        this.user = source[3];
        this.password = source[4];
    }

    private String getDbDriver(String dbName) throws Exception {
        switch (dbName) {
            case "postgres":
                return "org.postgresql.Driver";
            case "mysql":
                return "com.mysql.jdbc.Driver";
            case "oracle":
                return "oracle.jdbc.OracleDriver";
            default:
                throw new Exception("Not supported database.");
        }
    }

    public List<String> selectFacts() {
        SqlSession session = MySqlSessionFactory.getSqlSessionFactory(dbDriver, dbUrl, user, password).openSession();
        FactsMapper mapper = session.getMapper(FactsMapper.class);
        List<String> result = mapper.selectFacts();
        session.commit();
        session.close();
        return result;
    }

    public List<RuleDB> selectRules() {
        SqlSession session = MySqlSessionFactory.getSqlSessionFactory(dbDriver, dbUrl, user, password).openSession();
        FactsMapper mapper = session.getMapper(FactsMapper.class);
        List<RuleDB> result = mapper.selectRules();
        session.commit();
        session.close();
        return result;
    }

    public ExprDB selectExpr(Integer id) {
        SqlSession session = MySqlSessionFactory.getSqlSessionFactory(dbDriver, dbUrl, user, password).openSession();
        FactsMapper mapper = session.getMapper(FactsMapper.class);
        ExprDB result = mapper.selectExpr(id);
        session.commit();
        session.close();
        return result;
    }

    public List<ExprDB> selectExprChildren(Integer id) {
        SqlSession session = MySqlSessionFactory.getSqlSessionFactory(dbDriver, dbUrl, user, password).openSession();
        FactsMapper mapper = session.getMapper(FactsMapper.class);
        List<ExprDB> result = mapper.selectExprChildren(id);
        session.commit();
        session.close();
        return result;
    }
}
