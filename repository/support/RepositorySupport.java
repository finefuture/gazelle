package org.gra4j.gazelle.repository.support;

import java.lang.reflect.InvocationTargetException;

/**
 * gazelle
 * org.gra4j.gazelle.repository.support
 *
 * @author tom.long
 */
public interface RepositorySupport {

    Object execute(Object[] args) throws InvocationTargetException, IllegalAccessException;

}
