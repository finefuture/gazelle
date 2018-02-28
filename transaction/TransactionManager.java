package org.gra4j.gazelle.transaction;

/**
 * gazelle
 * org.gra4j.gazelle.transaction
 *
 * @author tom.long
 */
public interface TransactionManager {

    void begin ();

    void commit ();

    void rollback ();
}
