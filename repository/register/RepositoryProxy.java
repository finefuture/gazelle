package org.gra4j.gazelle.repository.register;

import org.gra4j.gazelle.repository.JpaContext;
import org.gra4j.gazelle.repository.model.RepositoryMetadata;
import org.gra4j.gazelle.transaction.ProxyTransactional;
import org.gra4j.gazelle.transaction.Transactional;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.gra4j.gazelle.structure.EntityPool.merge;

/**
 * gazelle
 * org.gra4j.gazelle.repository.register
 *
 * @author tom.long
 */
public class RepositoryProxy {

    private static Map<Method,DataAccessMethod> methodCache = new HashMap<>();

    public static <T> T createProxy (Class<T> repository) {
        RepositoryMetadata repositoryMetadata = JpaContext.getRepositoryMetadata(repository);
        List<Method> dataAccessMethods = repositoryMetadata.getDataAccessMethods();
        Class[] superInterfaces = repositoryMetadata.getSuperInterfaces();
        Class entityType = repositoryMetadata.getEntityType();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(repositoryMetadata.getAchievement());
        enhancer.setInterfaces(merge(superInterfaces, new Class[]{repository}));
        enhancer.setCallback((MethodInterceptor) (o, method, args, methodProxy) -> {
            if (dataAccessMethods.contains(method)) {
                DataAccessMethod dataAccessMethod = cachedDataAccessMethod(method, entityType);
                return dataAccessMethod.execute(args);
            }
            if (method.isAnnotationPresent(Transactional.class)) {
                return new ProxyTransactional(JpaContext.getTransactionManager()).doTransaction(methodProxy, o, args);
            }
            return methodProxy.invokeSuper(o, args);
        });
        return (T) enhancer.create(new Class[]{Class.class}, new Class[]{entityType});
    }

    private static DataAccessMethod cachedDataAccessMethod (Method method, Class entityType) {
        DataAccessMethod dataAccessMethod = methodCache.get(method);
        if (dataAccessMethod == null) {
            dataAccessMethod = new DataAccessMethod(method, entityType);
            methodCache.put(method, dataAccessMethod);
        }
        return dataAccessMethod;
    }

}
