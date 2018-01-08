package org.gra4j.gazelle.query;

import org.gra4j.gazelle.core.Special;
import org.gra4j.gazelle.core.Where;

import javax.persistence.Tuple;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.query
 *
 * @author tom.long
 */
public interface StandardizedQuery extends Query {

    <T> T findOne(Class<T> clazz, Object identity);

    List findAll(Class clazz);

    <T> List<T> findAll(Where where);

    List<Tuple> findSpec(Special special, Where where);

}
