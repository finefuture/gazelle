package org.gra4j.gazelle.JPAQuery.core;

import javax.persistence.criteria.Root;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery.core
 *
 * @author tom.long
 */
public class Join {

    private Jpa jpa;

    private Root root;

    public Join (Jpa jpa) {
        this.jpa = jpa;
        this.root = jpa.root();
    }

}
