package org.gra4j.gazelle.JPAQuery.builder;

import org.gra4j.gazelle.JPAQuery.core.Operation;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery.builder
 *
 * @author tom.long
 */
public class QueryBuilder<T> extends JPAQueryBuilder {

    public QueryBuilder (EntityManager em, Class<T> clazz) {
        super(em, clazz, Operation.select);
    }

    public List<T> list () {
        CriteriaQuery cq = jpa.query();
        Root root = jpa.root();
        cq.select(root);

        cq.where(where.getPredicate());
        cq.orderBy(where.order());
        cq.groupBy(Arrays.asList(where.groupBy().toArray()));
        cq.having(where.having_());
        javax.persistence.Query query = jpa.em().createQuery(cq);

        int[] page = where.page();
        return query.setFirstResult(page[0])
                    .setMaxResults(page[1])
                    .getResultList();
    }

}
