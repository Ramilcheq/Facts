package Facts.MyBatis;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface FactsMapper {
    @Select("SELECT fact FROM facts")
    List<String> selectFacts();

    @Select("SELECT expression_id, result_fact FROM rules")
    List<RuleDB> selectRules();

    @Select("SELECT expression_id, type, fact FROM expressions WHERE expression_id =#{id}")
    ExprDB selectExpr(int id);

    @Select("SELECT expressions.expression_id, expressions.type, expressions.fact " +
            "FROM siblings JOIN expressions ON siblings.child = expressions.expression_id " +
            "WHERE siblings.parent = #{id}")
    List<ExprDB> selectExprChildren(int id);
}
