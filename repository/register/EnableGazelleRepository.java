package org.gra4j.gazelle.repository.register;

import org.gra4j.gazelle.repository.SimpleCrud;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * gazelle
 * org.gra4j.gazelle.repository.register
 *
 * @author tom.long
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(GazelleRepositoryRegistrar.class)
public @interface EnableGazelleRepository {

    String[] basePackages() default {};

    Class<? extends SimpleCrud> achievement() default SimpleCrud.class;

    Class<? extends RepositoryFactoryBean> factoryBean() default RepositoryFactoryBean.class;

}
