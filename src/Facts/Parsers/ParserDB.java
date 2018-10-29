package Facts.Parsers;

import Facts.MyBatis.ExprDB;
import Facts.Expression.AndExpression;
import Facts.Expression.Expression;
import Facts.Expression.FactExpression;
import Facts.Expression.OrExpression;
import Facts.Model;
import Facts.MyBatis.FactDao;
import Facts.MyBatis.RuleDB;
import Facts.Rule;

import java.util.*;

public class ParserDB implements IParser {
    private FactDao factDao;

    @Override
    public Model parse(String source[]) throws Exception {
        factDao = new FactDao(source);
        List<RuleDB> rulesDB = factDao.selectRules();
        List<Rule> rules = new ArrayList<>();
        for (RuleDB ruleDB : rulesDB) {
            rules.add(parseRule(ruleDB));
        }
        Set<String> facts = new HashSet<>(factDao.selectFacts());
        return new Model(rules, facts);
    }

    private Rule parseRule(RuleDB ruleDB) throws Exception {
        ExprDB exprDB = factDao.selectExpr(ruleDB.getId());
        return new Rule(parseExpression(exprDB), ruleDB.getResultFact());
    }

    private Expression parseExpression(ExprDB exprDB) throws Exception {
        if (!exprDB.getType().equals("or"))
            return parseAndExpression(exprDB);

        List<Expression> andExprList = new ArrayList<>();
        for (ExprDB andExprDB : factDao.selectExprChildren(exprDB.getId())) {
            andExprList.add(parseAndExpression(andExprDB));
        }
        return new OrExpression(andExprList);
    }

    private Expression parseAndExpression(ExprDB exprDB) throws Exception {
        if (!exprDB.getType().equals("and"))
            return parseFactExpression(exprDB);

        List<Expression> factExprList = new ArrayList<>();
        for (ExprDB factExprDB : factDao.selectExprChildren(exprDB.getId())) {
            factExprList.add(parseFactExpression(factExprDB));
        }
        return new AndExpression(factExprList);
    }

    private Expression parseFactExpression(ExprDB exprDB) throws Exception {
        if (!exprDB.getType().equals("fact"))
            throw new Exception("Not supported expression.");

        return new FactExpression(exprDB.getFact());
    }
}
