package org.gra4j.gazelle.JPAQuery.builder;

import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery.builder
 *
 * @author tom.long
 */
public interface JPAQuery {

    int execute ();

    List tuple ();

    List list ();

}
