package org.gra4j.gazelle.repository.support;

import org.gra4j.gazelle.JPAQuery.core.Operation;
import org.gra4j.gazelle.repository.Enum.KeyType;
import org.gra4j.gazelle.repository.annotation.core.Special;
import org.gra4j.gazelle.repository.annotation.core.TupleQuery;
import org.gra4j.gazelle.repository.model.ExpressionModel;
import org.gra4j.gazelle.repository.model.ParamInfo;
import org.gra4j.gazelle.repository.model.SpecialModel;
import org.gra4j.gazelle.structure.EntityPool;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.repository.support
 *
 * @author tom.long
 */
public class TupleQuerySupport extends JpaQuerySupport {

    private SpecialModel specialModel;

    private Special special;

    private boolean aggregated;

    private boolean distinct;

    private List<Expression> groupBy;

    public TupleQuerySupport (TupleQuery tupleQuery, List<ParamInfo> paramInfoList,
                              List<ParamInfo> sepcInfoList, int[] pageInfo, Class entityType) {
        super(tupleQuery.where(), pageInfo, paramInfoList, Operation.tupleQuery, entityType);
        this.special = tupleQuery.spec();
        this.specialModel = new SpecialModel(special, sepcInfoList);
        this.aggregated = tupleQuery.aggregated();
        this.distinct = tupleQuery.distinct();
        this.groupBy = toGroupBy();
    }

    @Override
    public Object execute(Object[] args) throws InvocationTargetException, IllegalAccessException {
        CriteriaQuery cq = jpaStructure.getQuery();
        cq.distinct(distinct);
        cq.multiselect(toExpression(args));

        cq.where(toPredicate(args, KeyType.and, KeyType.or));
        cq.orderBy(toOrder());
        cq.groupBy(groupBy);
        cq.having(toHaving(args));
        TypedQuery<Tuple> query = em.createQuery(cq);

        int[] page = toPage(args);
        return query.setFirstResult(page[0])
                    .setMaxResults(page[1])
                    .getResultList();
    }

    private List<Expression> toExpression (Object[] args) throws InvocationTargetException, IllegalAccessException {
        List<ExpressionModel> expressionModelList = specialModel.getExpressionModelList();
        List<Expression> expressions = paserSomeOrAll();
        for (ExpressionModel expressionModel:expressionModelList)
            expressions.add(paserExpressionModel(args, expressionModel));
        return expressions;
    }

    private List<Expression> paserSomeOrAll () {
        List<Expression> expressions = new ArrayList<>();
        String[] some = special.some();
        Root root = root();
        for (String field:some) {
            Path path = root.get(field);
            expressions.add(path);
            if (aggregated)
                groupBy.add(path);
        }
        boolean selectAll = special.all();
        if (selectAll) {
            Class javaType = root.getModel().getBindableJavaType();
            Field[] fields = EntityPool.getIfExist(javaType);
            for (Field field:fields) {
                Path path = root.get(field.getName());
                expressions.add(path);
                if (aggregated)
                    groupBy.add(path);
            }
        }
        return expressions;
    }

}
