package org.gra4j.gazelle.query;

import org.gra4j.gazelle.core.Jpa;
import org.gra4j.gazelle.core.Special;
import org.gra4j.gazelle.core.Where;
import org.gra4j.gazelle.modify.AbstractModify;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * gazelle
 * org.gra4j.gazelle.query
 *
 * @author tom.long
 */
public abstract class StandardizedQueryHeir extends AbstractModify implements StandardizedQuery {

    private Jpa jpa;

    public StandardizedQueryHeir (Jpa jpa) {
        super(jpa);
        this.jpa = jpa;
    }

    @Override
    public <T> T findOne(Class<T> targetClass, Object identity) {
        return jpa.em().find(targetClass, identity);
    }

    @Override
    public List findAll(Class targetClass) {
        CriteriaBuilder cb = jpa.em().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(targetClass);
        Root root = cq.from(targetClass);
        cq.select(root);
        javax.persistence.Query query = jpa.em().createQuery(cq);

        return query.getResultList();
    }

    @Override
    public <T> List<T> findAll(Where where) {
        CriteriaQuery cq = jpa.query();
        Root root = jpa.root();
        cq.select(root);

        cq.where(where.getPredicate());
        cq.orderBy(where.order());
        cq.groupBy(Arrays.asList(where.groupBy().toArray()));
        Predicate having = where.having_();
        if (null!=having)
            cq.having(having);
        javax.persistence.Query query = jpa.em().createQuery(cq);

        int[] page = where.page();
        return query.setFirstResult(page[0])
                .setMaxResults(page[1])
                .getResultList();
    }

    @Override
    public List<Tuple> findSpec (Special special, Where where) {
        CriteriaQuery cq = jpa.query();
        cq.multiselect(special.expressions());

        cq.where(where.getPredicate());
        cq.orderBy(where.order());
        Set<Expression> groupBy = where.groupBy();
        groupBy.addAll(special.groupBy());
        cq.groupBy(Arrays.asList(groupBy.toArray(new Expression[groupBy.size()])));
        Predicate having = where.having_();
        if (null!=having)
            cq.having(having);
        TypedQuery<Tuple> query = jpa.em().createQuery(cq);

        int[] page = where.page();
        return query.setFirstResult(page[0])
                .setMaxResults(page[1])
                .getResultList();
    }

    public List<Map<String,Object>> findSpecToList (Special special, Where where) {
        List<Tuple> tuples = findSpec(special, where);
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
