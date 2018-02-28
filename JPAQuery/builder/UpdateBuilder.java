package org.gra4j.gazelle.JPAQuery.builder;

import org.gra4j.gazelle.transaction.Transactional;
import org.gra4j.gazelle.JPAQuery.core.Operation;
import org.gra4j.gazelle.JPAQuery.core.Setter;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaUpdate;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery.builder
 *
 * @author tom.long
 */
public class UpdateBuilder extends JPAQueryBuilder {

    private Setter setter;

    public UpdateBuilder (EntityManager em, Class clazz) {
        super(em, clazz, Operation.update);
    }

    public Setter setter () {
        this.setter = new Setter(jpa, this);
        return this.setter;
    }

    @Transactional
    public int execute () {
        CriteriaUpdate update = jpa.update();

        update.where(where.getPredicate());
        Query query = jpa.em().createQuery(update);
        return query.executeUpdate();
    }

}
