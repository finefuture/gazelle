package org.gra4j.gazelle.repository.support;

import org.gra4j.gazelle.JPAQuery.core.Operation;
import org.gra4j.gazelle.chain.CheckChain;
import org.gra4j.gazelle.expression.SimpleExpression;
import org.gra4j.gazelle.repository.Enum.ExpressionOps;
import org.gra4j.gazelle.repository.Enum.KeyType;
import org.gra4j.gazelle.repository.JpaContext;
import org.gra4j.gazelle.repository.annotation.core.Where;
import org.gra4j.gazelle.repository.model.ExpressionModel;
import org.gra4j.gazelle.repository.model.ParamInfo;
import org.gra4j.gazelle.repository.model.WhereModel;
import org.gra4j.gazelle.structure.JpaStructure;
import org.gra4j.gazelle.util.AsmMethodAccess;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * gazelle
 * org.gra4j.gazelle.repository.support
 *
 * @author tom.long
 */
public abstract class JpaRepositorySupport implements RepositorySupport {

    private WhereModel whereModel;

    protected final static EntityManager em = JpaContext.em();

    private static AsmMethodAccess access = AsmMethodAccess.get(SimpleExpression.class);

    private SimpleExpression simpleExpression;

    protected JpaStructure jpaStructure;

    private CheckChain check;

    public JpaRepositorySupport(Where where, List<ParamInfo> paramInfoList, Operation operation, Class entityType) {
        this.jpaStructure = new JpaStructure(em, operation, entityType);
        this.simpleExpression = new SimpleExpression(cb(), root());
        this.whereModel = new WhereModel(where, paramInfoList);
        this.check = whereModel.getCheck();
    }

    public abstract Object execute (Object[] args) throws InvocationTargetException, IllegalAccessException;

    protected CriteriaBuilder cb () {
        return this.jpaStructure.getCb();
    }

    protected Root root () {
        return this.jpaStructure.getRoot();
    }

    protected Predicate toPredicate (Object[] args, KeyType and, KeyType or) throws InvocationTargetException, IllegalAccessException {
        Predicate predicate = cb().conjunction();
        Map<KeyType, List<ExpressionModel>> emodelMap = whereModel.getEmodelMap();
        List<ExpressionModel> expressionModels = emodelMap.get(and);
        for (ExpressionModel expressionModel:expressionModels) {
            predicate = cb().and(predicate, paserExpressionModel(args, expressionModel));
        }
        Map<KeyType, List<List<ExpressionModel>>> emodelArrayMap = whereModel.getEmodelArrayMap();
        List<List<ExpressionModel>> emodelArray = emodelArrayMap.get(or);
        for (List<ExpressionModel> emodelList:emodelArray) {
            List<Predicate> predicates = new ArrayList();
            for (ExpressionModel emodel:emodelList)
                predicates.add((Predicate) paserExpressionModel(args, emodel));
            predicate = cb().and(predicate, cb().or(predicates.toArray(new Predicate[predicates.size()])));
        }
        return predicate;
    }

    protected List<Order> toOrder () {
        List<Order> orderList = new ArrayList<>();
        Map<KeyType, String[]> stringMap = whereModel.getStringMap();
        String[] ascs = stringMap.get(KeyType.asc);
        for (String field:ascs) {
            Path path = root().get(field);
            Order asc = cb().asc(path);
            orderList.add(asc);
        }
        String[] descs = stringMap.get(KeyType.desc);
        for (String field:descs) {
            Path path = root().get(field);
            Order desc = cb().desc(path);
            orderList.add(desc);
        }
//        Map<KeyType, List<ExpressionModel>> emodelMap = whereModel.getEmodelMap();
//        List<ExpressionModel> eascs = emodelMap.get(KeyType.easc);
//        for (ExpressionModel expressionModel:eascs) {
//            Order asc = cb.asc(exp);
//            whereStructure.addOrder(asc);
//        }
        return orderList;
    }

    protected List<Expression> toGroupBy () {
        List<Expression> groupBy = new ArrayList<>();
        Map<KeyType, String[]> stringMap = whereModel.getStringMap();
        String[] fields = stringMap.get(KeyType.groupBy);
        for (String field:fields)
            groupBy.add(root().get(field));
        return groupBy;
    }

    protected Predicate toHaving (Object[] args) throws InvocationTargetException, IllegalAccessException {
        return toPredicate(args, KeyType.ha, KeyType.ho);
    }

    protected Expression paserExpressionModel (Object[] args, ExpressionModel expressionModel)
            throws InvocationTargetException, IllegalAccessException {
        ExpressionModel.BlankType blankType = expressionModel.getBlankType();
        ExpressionOps ops = expressionModel.getOps();
        List parameter = new ArrayList();
        switch (blankType) {
            case kvBlank:
                parameter.add(args[expressionModel.getKeyPosition()]);
                parameter.add(args[expressionModel.getValuePosition()]);
                break;
            case valueBlank:
                parameter.add(expressionModel.getKey());
                parameter.add(args[expressionModel.getValuePosition()]);
                break;
            case keyBlank:
                parameter.add(args[expressionModel.getKeyPosition()]);
                parameter.add(expressionModel.getValue());
                break;
            case noBlank:
                parameter.add(expressionModel.getKey());
                parameter.add(expressionModel.getValue());
                break;
        }
        if (!check.check(check, parameter.get(1)))
            return null;
        if (ops == ExpressionOps.between) {
            Object arg = args[expressionModel.getSpecPosition()];
            if (!check.check(check, arg))
                return null;
            parameter.add(arg);
        }
        if (ops.isPredicate)
            return (Predicate) access.invoke(simpleExpression, ops.name(), expressionModel.paramType(), parameter.toArray());
        else
            return (Expression) ((Expression) access.invoke(simpleExpression, ops.name(), expressionModel.paramType(), parameter.toArray()))
                                                                 .alias(expressionModel.getAlias());
    }

}
