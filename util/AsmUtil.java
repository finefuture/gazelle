package org.gra4j.gazelle.util;

/**
 * gazelle
 * org.gra4j.gazelle.util
 *
 * @author tom.long
 */
public class AsmUtil {

    public static Object fastInvoke (Object obj, String methodName, Class<?>[] parameterTypes, Object... args) {
        AsmMethodAccess accessor = AsmMethodAccess.get(obj.getClass());
        return accessor.invoke(obj, methodName, parameterTypes, args);
    }

}
