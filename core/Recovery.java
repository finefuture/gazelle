package org.gra4j.gazelle.core;

/**
 * gazelle
 * org.gra4j.gazelle.core
 *
 * @author tom.long
 */
public class Recovery {

    private Jpa jpa;

    private Special special;

    private Where where;

    public Recovery (Jpa jpa, Special special, Where where) {
        this.jpa = jpa;
        this.special = special;
        this.where = where;
    }

    public void recover () {
        jpa.clear();
        special.clear();
        where.clear();
    }
}
