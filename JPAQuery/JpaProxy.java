package org.gra4j.gazelle.JPAQuery;

import org.gra4j.gazelle.repository.JpaContext;
import org.gra4j.gazelle.transaction.ProxyTransactional;
import org.gra4j.gazelle.transaction.Transactional;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery
 *
 * @author tom.long
 */
public class JpaProxy {

    public static <T> T createProxy (Class<T> proxyClass, Class[] paramType, Object[] param) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(proxyClass);
        enhancer.setCallback((MethodInterceptor) (o, method, args, methodProxy) -> {
            if (method.isAnnotationPresent(Transactional.class)) {
                return new ProxyTransactional(JpaContext.getTransactionManager()).doTransaction(methodProxy, o, args);
            }
            return methodProxy.invokeSuper(o, args);
        });
        return (T) enhancer.create(paramType, param);
    }

}
