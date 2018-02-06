package org.gra4j.gazelle.repository.support;

import org.gra4j.gazelle.JPAQuery.core.Operation;
import org.gra4j.gazelle.repository.Enum.KeyType;
import org.gra4j.gazelle.repository.annotation.core.Query;
import org.gra4j.gazelle.repository.model.ParamInfo;

import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.repository.support
 *
 * @author tom.long
 */
public class QuerySupport extends JpaQuerySupport {

    public QuerySupport (Query query, List<ParamInfo> paramInfoList, int[] pageInfo, Class entityType) {
        super(query.value(), pageInfo, paramInfoList, Operation.select, entityType);
    }

    @Override
    public Object execute(Object[] args) throws InvocationTargetException, IllegalAccessException {
        CriteriaQuery cq = jpaStructure.getQuery();
        cq.select(root());

        cq.where(toPredicate(args, KeyType.and, KeyType.or));
        cq.orderBy(toOrder());
        cq.groupBy(toGroupBy());
        cq.having(toHaving(args));
        javax.persistence.Query query = em.createQuery(cq);

        toPage(args);
        return query.setFirstResult(pageInfo[0])
                    .setMaxResults(pageInfo[1])
                    .getResultList();
    }

}