package org.gra4j.gazelle.repository.annotation.comprise;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * gazelle
 * org.gra4j.gazelle.repository.annotation.comprise
 *
 * @author tom.long
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Having {

    And[] and() default {};

    Or[] or() default {};

}
