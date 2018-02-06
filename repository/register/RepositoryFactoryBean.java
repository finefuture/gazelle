package org.gra4j.gazelle.repository.register;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * gazelle
 * org.gra4j.gazelle.repository.register
 *
 * @author tom.long
 */
public class RepositoryFactoryBean<T> implements FactoryBean<T>, InitializingBean, DisposableBean {

    private Class<T> mapperInterface;

    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    public void setMapperInterface(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public T getObject() throws Exception {
        return RepositoryProxy.createProxy(mapperInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
