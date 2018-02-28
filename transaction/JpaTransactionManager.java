package org.gra4j.gazelle.transaction;

import javax.persistence.EntityTransaction;

/**
 * gazelle
 * org.gra4j.gazelle.transaction
 *
 * @author tom.long
 */
public class JpaTransactionManager implements TransactionManager {

    private EntityTransaction tx;

    public JpaTransactionManager (EntityTransaction tx) {
        this.tx = tx;
    }

    @Override
    public void begin() {
        tx.begin();
    }

    @Override
    public void commit() {
        tx.commit();
    }

    @Override
    public void rollback() {
        if ( tx != null && tx.isActive()) {
            tx.rollback();
        }
    }
}
