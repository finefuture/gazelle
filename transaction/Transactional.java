package org.gra4j.gazelle.transaction;

import java.lang.annotation.*;

/**
 * gazelle
 * org.gra4j.gazelle.transaction
 *
 * @author tom.long
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Transactional {
}
