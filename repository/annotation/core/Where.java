package org.gra4j.gazelle.repository.annotation.core;

import org.gra4j.gazelle.repository.Enum.CheckOps;
import org.gra4j.gazelle.repository.annotation.comprise.And;
import org.gra4j.gazelle.repository.annotation.comprise.Having;
import org.gra4j.gazelle.repository.annotation.comprise.Or;
import org.gra4j.gazelle.repository.annotation.comprise.Order;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * gazelle
 * org.gra4j.gazelle.repository.annotation.core
 *
 * @author tom.long
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Where {

    And[] and() default {};

    Or[] or() default {};

    String[] groupBy() default {};

    Having[] having() default {};

    Order[] order() default {};

    CheckOps[] check() default CheckOps.none;
}
