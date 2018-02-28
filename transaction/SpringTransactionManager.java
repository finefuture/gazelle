package org.gra4j.gazelle.transaction;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * gazelle
 * org.gra4j.gazelle.transaction
 *
 * @author tom.long
 */
public class SpringTransactionManager implements TransactionManager {

    private PlatformTransactionManager tx;

    private TransactionStatus txStatus;

    public SpringTransactionManager (PlatformTransactionManager tx) {
        this.tx = tx;
    }

    @Override
    public void begin() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        def.setTimeout(30);
        txStatus = tx.getTransaction(def);
    }

    @Override
    public void commit() {
        tx.commit(txStatus);
    }

    @Override
    public void rollback() {
        if(!txStatus.isCompleted()){
            tx.rollback(txStatus);
        }
    }
}
