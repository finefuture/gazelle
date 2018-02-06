package org.gra4j.gazelle.JPAQuery;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

import static org.gra4j.gazelle.util.Checker.notNull;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery
 *
 * @author tom.long
 */
public class BasicOperation<T, ID extends Serializable> {

    private final static String ENTITY_NOTNULL = "The entity must not be null!";

    private final static String ID_NOTNULL = "The given id must not be null!";

    private EntityManager em;

    private Class<T> clazz;

    public BasicOperation (EntityManager em, Class<T> clazz) {
        this.em = em;
        this.clazz = clazz;
    }

    public T findOne(ID identity) {
        notNull(identity, ID_NOTNULL);
        return em.find(clazz, identity);
    }

    public T getOne (ID identity) {
        notNull(identity, ID_NOTNULL);
        return em.getReference(clazz, identity);
    }

    public List<T> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(clazz);
        Root root = cq.from(clazz);
        cq.select(root);
        javax.persistence.Query query = em.createQuery(cq);

        return query.getResultList();
    }

    @Transactional
    public T save (T entity) {
        notNull(entity, ENTITY_NOTNULL);
        em.persist(entity);
        return entity;
    }

    @Transactional
    public T update (T entity) {
        notNull(entity, ENTITY_NOTNULL);
        em.merge(entity);
        return entity;
    }

    @Transactional
    public T saveAndFlush (T entity) {
        notNull(entity, ENTITY_NOTNULL);
        em.persist(entity);
        em.flush();
        return entity;
    }

    public T updateAndFlush (T entity) {
        notNull(entity, ENTITY_NOTNULL);
        em.merge(entity);
        em.flush();
        return entity;
    }

    @Transactional
    public void delete (ID identity) {
        notNull(identity, ID_NOTNULL);
        T entity = findOne(identity);
        notNull(entity, String.format("No %s entity with id %s exists!", clazz, identity));
        delete(entity);
    }

    @Transactional
    public void delete (T entity) {
        notNull(entity, ENTITY_NOTNULL);
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    public int deleteAll () {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete delete = cb.createCriteriaDelete(clazz);
        delete.from(clazz);

        Query query = em.createQuery(delete);
        return query.executeUpdate();
    }

    @Transactional
    @Deprecated//Not recommended for use
    public void delete (Iterable<T> entities) {
        notNull(entities, "The given Iterable of entities not be null!");

        for (T entity : entities) {
            delete(entity);
        }
    }

    @Transactional
    @Deprecated//Not recommended for use
    public void deleteAll$ () {
        delete(findAll());
    }

}
