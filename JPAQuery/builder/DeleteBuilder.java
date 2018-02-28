package org.gra4j.gazelle.JPAQuery.builder;

import org.gra4j.gazelle.transaction.Transactional;
import org.gra4j.gazelle.JPAQuery.core.Operation;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaDelete;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery.builder
 *
 * @author tom.long
 */
public class DeleteBuilder extends JPAQueryBuilder {

    public DeleteBuilder (EntityManager em, Class clazz) {
        super(em, clazz, Operation.delete);
    }

    @Transactional
    public int execute () {
        CriteriaDelete delete = jpa.delete();

        delete.where(where.getPredicate());
        Query query = jpa.em().createQuery(delete);
        return query.executeUpdate();
    }

}
