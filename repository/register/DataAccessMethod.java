package org.gra4j.gazelle.repository.register;

import org.gra4j.gazelle.repository.model.MethodMetadata;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * gazelle
 * org.gra4j.gazelle.repository.register
 *
 * @author tom.long
 */
public class DataAccessMethod {

    private Method method;

    private MethodMetadata methodMetadata;

    public DataAccessMethod(Method method, Class entityType) {
        this.method = method;
        this.methodMetadata = new MethodMetadata(method, entityType);
    }

    public Object execute(Object[] args) throws InvocationTargetException, IllegalAccessException {
        return methodMetadata.getSupport().execute(args);
    }

}
