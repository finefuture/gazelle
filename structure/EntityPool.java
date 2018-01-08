package org.gra4j.gazelle.structure;

import org.gra4j.gazelle.chain.CheckChain;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * gazelle
 * org.gra4j.gazelle.structure
 *
 * @author tom.long
 */
public abstract class EntityPool implements Serializable {

    private final static ThreadLocal<CheckChain> check = ThreadLocal.withInitial(() -> new CheckChain());

    final static Map<Class, Field[]> entityFields = new ConcurrentHashMap<>();

    public static Field[] getIfExist (Class targetClass) {
        Field[] fields = entityFields.get(targetClass);
        if (null==fields) {
            fields = targetClass.getDeclaredFields();
            Class superclass = targetClass.getSuperclass();
            if (null!=superclass) {
                Field[] superFields = superclass.getDeclaredFields();
                fields = merge(fields, superFields);
            }
            CheckChain checker = check.get();
            checker.check(checker, fields);
            entityFields.put(targetClass, fields);
        }
        return fields;
    }

    public static <T> T[] merge (T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static void addChecker (String methodName, Class targetClass) {
        check.get().addChecker(methodName, targetClass);
    }

    public static Map<Class, Field[]> getEntityFields() {
        return entityFields;
    }
}
