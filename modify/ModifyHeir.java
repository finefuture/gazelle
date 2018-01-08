package org.gra4j.gazelle.modify;

import org.gra4j.gazelle.core.Jpa;
import org.gra4j.gazelle.core.Where;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;

/**
 * gazelle
 * org.gra4j.gazelle.modify
 *
 * @author tom.long
 */
public abstract class ModifyHeir implements Modify{

    private Jpa jpa;

    private EntityManager em;

    public ModifyHeir(Jpa jpa) {
        this.jpa = jpa;
        this.em = jpa.em();
    }

    @Override
    @Transactional
    public int delete (Where where) {
        CriteriaDelete delete = jpa.delete();

        delete.where(where.getPredicate());
        Query query = em.createQuery(delete);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int deleteAll () {
        CriteriaDelete delete = jpa.delete();

        Query query = em.createQuery(delete);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int deleteAll (Class targetClass) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete delete = cb.createCriteriaDelete(targetClass);

        Query query = em.createQuery(delete);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int update () {
        CriteriaUpdate update = jpa.update();

        Query query = em.createQuery(update);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int update (Where where) {
        CriteriaUpdate update = jpa.update();

        update.where(where.getPredicate());
        Query query = em.createQuery(update);
        return query.executeUpdate();
    }

}
