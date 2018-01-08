package org.gra4j.gazelle.core;

import org.gra4j.gazelle.modify.AbstractModify;
import org.gra4j.gazelle.query.AbstractSqlQuery;
import org.gra4j.gazelle.util.Checker;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * gazelle
 * org.gra4j.gazelle.core
 *
 * @author tom.long
 */
public class Criterion extends AbstractSqlQuery {

    private Jpa jpa;

    private EntityManager em;

    public Criterion(Jpa jpa) {
        super(jpa);
        this.jpa = jpa;
        this.em = jpa.em();
    }

    @Transactional
    public <T> T save (T entity) {
        Checker.notNull(entity, "The entity must not be null!");
        em.persist(entity);
        return entity;
    }

    @Transactional
    public <T> T update (T entity) {
        Checker.notNull(entity, "The entity must not be null!");
        em.merge(entity);
        return entity;
    }

    @Transactional
    public <T> T saveAndFlush (T entity) {
        Checker.notNull(entity, "The entity must not be null!");
        em.persist(entity);
        em.flush();
        return entity;
    }

    @Transactional
    public void delete (Class targetClass, Object identity) {
        Object entity = findOne(targetClass, identity);
        Checker.notNull(entity, String.format("No %s entity with id %s exists!", targetClass, identity));
        delete(entity);
    }

    @Transactional
    public void delete (Object entity) {
        Checker.notNull(entity, "The entity must not be null!");
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    /**
     * {@link AbstractModify}
     * @param entities
     */
    @Transactional
    @Deprecated//Not recommended for use
    public void delete (Iterable entities) {
        Checker.notNull(entities, "The given Iterable of entities not be null!");

        for (Object entity : entities) {
            delete(entity);
        }
    }

    /**
     * {@link AbstractModify}
     * @param targetClass
     */
    @Transactional
    @Deprecated//Not recommended for use
    public void deleteAll$ (Class targetClass) {
        delete(findAll(targetClass));
    }

}
