package org.gra4j.gazelle.repository.register;

import org.gra4j.gazelle.repository.JpaContext;
import org.gra4j.gazelle.repository.SimpleCrud;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

/**
 * gazelle
 * org.gra4j.gazelle.repository.register
 *
 * @author tom.long
 */
public class GazelleRepositoryRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(
                annotationMetadata.getAnnotationAttributes(EnableGazelleRepository.class.getName()));

        String[] basePackages = annoAttrs.getStringArray("basePackages");
        Class<? extends SimpleCrud> achievement = annoAttrs.getClass("achievement");
        Class<? extends RepositoryFactoryBean> factoryBean = annoAttrs.getClass("factoryBean");

        JpaContext jpaContext = new JpaContext(basePackages, achievement);
        Set<Class> repositoryClasses = jpaContext.getRepositoryClasses();
        for (Class repository:repositoryClasses) {
            GenericBeanDefinition definition = new GenericBeanDefinition();
            definition.setBeanClass(factoryBean);
            definition.getPropertyValues().add("mapperInterface", repository);
            registry.registerBeanDefinition(repository.getSimpleName(), definition);
        }

//        ComponentRegistrar componentRegistrar = new ComponentRegistrar(registry);
//        componentRegistrar.doRegister();
//        RepositoryScanner scanner = new RepositoryScanner(beanDefinitionRegistry);
//        scanner.setRepositoryFactoryBean(factoryBean);
//        scanner.registerFilters();
//        scanner.doScan(basePackages);
    }
}
