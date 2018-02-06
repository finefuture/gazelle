package org.gra4j.gazelle.JPAQuery;

import org.gra4j.gazelle.JPAQuery.builder.DeleteBuilder;
import org.gra4j.gazelle.JPAQuery.builder.QueryBuilder;
import org.gra4j.gazelle.JPAQuery.builder.TupleBuilder;
import org.gra4j.gazelle.JPAQuery.builder.UpdateBuilder;
import org.gra4j.gazelle.JPAQuery.sql.AbstractModify;
import org.gra4j.gazelle.JPAQuery.sql.AbstractSqlQuery;
import org.gra4j.gazelle.repository.JpaContext;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery
 *
 * @author tom.long
 */
public class GazelleQuery {

    private final static EntityManager em = JpaContext.em();

    public static QueryBuilder select (Class clazz) {
        return new QueryBuilder(em, clazz);
    }

    public static TupleBuilder tuple (Class clazz) {
        return new TupleBuilder(em, clazz);
    }

    public static UpdateBuilder update (Class clazz) {
        return new UpdateBuilder(em, clazz);
    }

    public static DeleteBuilder delete (Class clazz) {
        return new DeleteBuilder(em, clazz);
    }

    public static <T, ID extends Serializable> BasicOperation basic (Class<T> clazz) {
        return new BasicOperation<T, ID>(em, clazz);
    }

    public static AbstractSqlQuery query () {
        return new AbstractSqlQuery(em);
    }

    public static AbstractModify modify () {
        return new AbstractModify(em);
    }

}
