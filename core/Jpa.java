package org.gra4j.gazelle.core;

import org.gra4j.gazelle.structure.JpaStructure;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

/**
 * gazelle
 * org.gra4j.gazelle.core
 *
 * @author tom.long
 */
public class Jpa {

    private EntityManager entityManager;

    private ThreadLocal<JpaStructure> jpaStructure = new ThreadLocal<>();

    public Jpa (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Jpa register (Class clazz, Operation operation) {
        jpaStructure.set(new JpaStructure(entityManager, operation, clazz));
        return this;
    }

    public EntityManager em () {
        return this.entityManager;
    }

    public CriteriaBuilder cb () {
        return this.jpaStructure.get().getCb();
    }

    public CriteriaQuery query () {
        return this.jpaStructure.get().getQuery();
    }

    public CriteriaUpdate update () {
        return this.jpaStructure.get().getUpdate();
    }

    public CriteriaDelete delete () {
        return this.jpaStructure.get().getDelete();
    }

    public Root root () {
        return this.jpaStructure.get().getRoot();
    }

    public void aggregate () {
        this.jpaStructure.get().setIsAggregated(Boolean.TRUE);
    }

    public void nonAggregate () {
        this.jpaStructure.get().setIsAggregated(Boolean.FALSE);
    }

    public Boolean isAggregated () {
        return this.jpaStructure.get().getIsAggregated();
    }

    protected void clear () {
        jpaStructure.remove();
    }

}
