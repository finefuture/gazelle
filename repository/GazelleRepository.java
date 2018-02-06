package org.gra4j.gazelle.repository;

import java.io.Serializable;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.repository
 *
 * @author tom.long
 */
public interface GazelleRepository<T,ID extends Serializable> {

    T findOne (ID identity);

    T getOne (ID identity);

    List<T> findAll ();

    T save (T entity);

    T update (T entity);

    T saveAndFlush (T entity);

    T updateAndFlush (T entity);

    void delete (T entity);

    void delete (ID identity);

    int deleteAll ();

    void delete (Iterable<T> entities);

}
