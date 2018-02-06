package org.gra4j.gazelle.structure;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * gazelle
 * org.gra4j.gazelle.structure
 *
 * @author tom.long
 */
public abstract class CheckerPool implements Serializable {

    final static Map<Class,Object> pool = new ConcurrentHashMap<>();

    public static Object getIfExist (Class targetClass) throws IllegalAccessException, InstantiationException {
        Object checker = pool.get(targetClass);
        if (null==checker) {
            checker = targetClass.newInstance();
            pool.putIfAbsent(targetClass, checker);
        }
        return checker;
    }

    public static Map<Class, Object> getPool() {
        return pool;
    }

}
