package org.gra4j.gazelle.JPAQuery.builder;

import org.gra4j.gazelle.JPAQuery.core.Operation;
import org.gra4j.gazelle.JPAQuery.core.Special;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import java.util.*;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery.builder
 *
 * @author tom.long
 */
public class TupleBuilder extends JPAQueryBuilder {

    private Special special;

    public TupleBuilder (EntityManager em, Class clazz) {
        super(em, clazz, Operation.tupleQuery);
    }

    public Special spec () {
        this.special = new Special(jpa, this);
        return this.special;
    }

    public List<Tuple> tuple () {
        CriteriaQuery cq = jpa.query();
        cq.multiselect(special.expressions());

        cq.where(where.getPredicate());
        cq.orderBy(where.order());
        Set<Expression> groupBy = where.groupBy();
        groupBy.addAll(special.groupBy());
        cq.groupBy(Arrays.asList(groupBy.toArray(new Expression[groupBy.size()])));
        cq.having(where.having_());
        TypedQuery<Tuple> query = jpa.em().createQuery(cq);

        int[] page = where.page();
        return query.setFirstResult(page[0])
                    .setMaxResults(page[1])
                    .getResultList();
    }

    public List<Map<String,Object>> list () {
        List<Tuple> tuples = tuple();
        List<String> alias_ = special.alias();
        List<Map<String,Object>> tupleList = new ArrayList<>();
        for (Tuple tuple:tuples) {
            Map<String,Object> tupleMap = new HashMap<>();
            for (String alias:alias_)
                tupleMap.put(alias,tuple.get(alias));
            tupleList.add(tupleMap);
        }
        return tupleList;
    }

}
