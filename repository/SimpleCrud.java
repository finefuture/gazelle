package org.gra4j.gazelle.repository;

import org.gra4j.gazelle.util.Checker;

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
 * org.gra4j.gazelle.repository
 *
 * @author tom.long
 */
public class SimpleCrud<T, ID extends Serializable> implements GazelleRepository<T, ID> {

    private final static String ENTITY_NOTNULL = "The entity must not be null!";

    private final static String ID_NOTNULL = "The given id must not be null!";

    private EntityManager em = JpaContext.em();

    private Class<T> entityType;

    public SimpleCrud (Class<T> entityType) {
        this.entityType = entityType;
    }

    @Override
    public T findOne (ID id) {
        notNull(id, ID_NOTNULL);
        return em.find(entityType, id);
    }

    @Override
    public T getOne (ID id) {
        notNull(id, ID_NOTNULL);
        return em.getReference(entityType, id);
    }

    @Override
    public List<T> findAll () {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(entityType);
        Root root = cq.from(entityType);
        cq.select(root);
        Query query = em.createQuery(cq);

        return query.getResultList();
    }

    @Override
    public T save (T entity) {
        notNull(entity, ENTITY_NOTNULL);
        em.persist(entity);
        return entity;
    }

    @Override
    public T update (T entity) {
        notNull(entity, ENTITY_NOTNULL);
        em.merge(entity);
        return entity;
    }

    @Override
    public T saveAndFlush (T entity) {
        save(entity);
        em.flush();
        return null;
    }

    @Override
    public T updateAndFlush (T entity) {
        update(entity);
        em.flush();
        return null;
    }

    @Override
    public void delete (T entity) {
        Checker.notNull(entity, "The entity must not be null!");
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    public void delete (ID id) {
        notNull(id, ID_NOTNULL);
        T entity = findOne(id);
        notNull(entity, String.format("No %s entity with id %s exists!", entityType, id));
        delete(entity);
    }

    @Override
    public int deleteAll () {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete delete = cb.createCriteriaDelete(entityType);
        delete.from(entityType);

        Query query = em.createQuery(delete);
        return query.executeUpdate();
    }

    @Override
    public void delete (Iterable<T> entities) {
        notNull(entities, "The given Iterable of entities not be null!");

        for (T entity : entities) {
            delete(entity);
        }
    }

}
