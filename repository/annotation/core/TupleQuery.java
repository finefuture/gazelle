package org.gra4j.gazelle.repository.annotation.core;

import java.lang.annotation.*;

/**
 * gazelle
 * org.gra4j.gazelle.repository.annotation.core
 *
 * @author tom.long
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TupleQuery {

    Special spec();

    Where where();

    boolean aggregated() default false;

    boolean distinct() default false;

}
