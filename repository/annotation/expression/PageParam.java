package org.gra4j.gazelle.repository.annotation.expression;

import org.gra4j.gazelle.repository.Enum.PageType;

import java.lang.annotation.*;

/**
 * gazelle
 * org.gra4j.gazelle.repository.annotation.expression
 *
 * @author tom.long
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageParam {

    PageType value() default PageType.max;

}
