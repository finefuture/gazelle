package org.gra4j.gazelle.JPAQuery.core;

import org.gra4j.gazelle.structure.JpaStructure;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery.core
 *
 * @author tom.long
 */
public class Jpa {

    private EntityManager entityManager;

    private JpaStructure jpaStructure;

    public Jpa (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Jpa register (Class clazz, Operation operation) {
        this.jpaStructure = new JpaStructure(entityManager, operation, clazz);
        return this;
    }

    public EntityManager em () {
        return this.entityManager;
    }

    public CriteriaBuilder cb () {
        return this.jpaStructure.getCb();
    }

    public CriteriaQuery query () {
        return this.jpaStructure.getQuery();
    }

    public CriteriaUpdate update () {
        return this.jpaStructure.getUpdate();
    }

    public CriteriaDelete delete () {
        return this.jpaStructure.getDelete();
    }

    public Root root () {
        return this.jpaStructure.getRoot();
    }

    public void aggregate () {
        this.jpaStructure.setIsAggregated(Boolean.TRUE);
    }

    public void nonAggregate () {
        this.jpaStructure.setIsAggregated(Boolean.FALSE);
    }

    public Boolean isAggregated () {
        return this.jpaStructure.getIsAggregated();
    }

}
