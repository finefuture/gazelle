package org.gra4j.gazelle.transaction;

import org.springframework.cglib.proxy.MethodProxy;

/**
 * gazelle
 * org.gra4j.gazelle.transaction
 *
 * @author tom.long
 */
public class ProxyTransactional {

    TransactionManager tx;

    public ProxyTransactional (TransactionManager tx) {
        this.tx = tx;
    }

    public Object doTransaction (MethodProxy methodProxy, Object o, Object[] args) throws Throwable {
        Object result;
        tx.begin();
        try {
            result = methodProxy.invokeSuper(o, args);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        return result;
    }

}
