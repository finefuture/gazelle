package org.gra4j.gazelle.JPAQuery.builder;

import org.gra4j.gazelle.JPAQuery.core.Jpa;
import org.gra4j.gazelle.JPAQuery.core.Operation;
import org.gra4j.gazelle.JPAQuery.core.Where;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery.builder
 *
 * @author tom.long
 */
public abstract class JPAQueryBuilder implements JPAQuery {

    protected Jpa jpa;

    protected Where where;

    public JPAQueryBuilder (EntityManager em, Class clazz, Operation operation) {
        this.jpa = new Jpa(em).register(clazz, operation);
        this.where = new Where(jpa, this);
    }

    public Where where () {
        return this.where;
    }

    public int execute () {
        return 0;
    }

    public List tuple () {
        return null;
    }

    public List list () {
        return null;
    }

}
