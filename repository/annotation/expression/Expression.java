package org.gra4j.gazelle.repository.annotation.expression;

import org.gra4j.gazelle.repository.Enum.ExpressionOps;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * gazelle
 * org.gra4j.gazelle.repository.annotation.expression
 *
 * @author tom.long
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Expression {

    ExpressionOps ops();

    String key() default "";

    String value() default "";

    String alias() default "";
}
