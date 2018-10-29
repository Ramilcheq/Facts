package Facts.MyBatis;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

public class MySqlSessionFactory {
    public static SqlSessionFactory getSqlSessionFactory(String dbDriver, String dbUrl, String user, String password) {
        DataSource dataSource = new PooledDataSource(dbDriver, dbUrl, user, password);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration config = new Configuration(environment);
        config.addMapper(FactsMapper.class);
        config.getTypeAliasRegistry().registerAlias("ExprDB", ExprDB.class);
        return new SqlSessionFactoryBuilder().build(config);
    }
}
