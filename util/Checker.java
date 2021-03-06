package org.gra4j.gazelle.util;

/**
 * gazelle
 * org.gra4j.gazelle.util
 *
 * @author tom.long
 */
public class Checker {

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static boolean isBlank (Object object) {
        if (object == null || "".equals(object)) {
            return true;
        }
        return false;
    }

}
