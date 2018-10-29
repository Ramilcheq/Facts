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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserDB implements IParser {
    private static final String FACT_PATTERN = "(\\_*[A-Za-z]+\\w*)";
    private FactDao factDao;

    @Override
    public Model parse(String... source) throws Exception {
        factDao = new FactDao(source);
        List<RuleDB> rulesDB = factDao.selectRules();
        List<Rule> rules = new ArrayList<>();
        for (RuleDB ruleDB : rulesDB) {
            rules.add(parseRule(ruleDB));
        }
        Set<String> facts = new HashSet<>();
        for (String fact : factDao.selectFacts()) {
            facts.add(parseFact(fact));
        }
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

        return new FactExpression(parseFact(exprDB.getFact()));
    }

    private String parseFact(String expr) throws Exception {
        Matcher m = Pattern.compile(FACT_PATTERN)
                .matcher(expr.trim());
        if (!m.matches())
            throw new Exception("Wrong fact format.");
        return expr;
    }

//    private Expression getExpression(ExprDB exprDB, FactDao factDao) throws Exception {
//        switch (exprDB.getType()) {
//            case "fact":
//                return new FactExpression(exprDB.getFact());
//            case "and":
//                List<Expression> factExpressions = new ArrayList<>();
//                for (ExprDB factExprDB : factDao.selectExprChildren(exprDB.getId())) {
//                    factExpressions.add(getExpression(factExprDB, factDao));
//                }
//                return new AndExpression(factExpressions);
//            case "or":
//                List<Expression> AndExpressions = new ArrayList<>();
//                for (ExprDB andExprDB : factDao.selectExprChildren(exprDB.getId())) {
//                    AndExpressions.add(getExpression(andExprDB, factDao));
//                }
//                return new OrExpression(AndExpressions);
//            default:
//                throw new Exception("Not supported expression.");
//        }
}
