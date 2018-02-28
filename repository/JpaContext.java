package org.gra4j.gazelle.repository;

import org.gra4j.gazelle.repository.model.RepositoryMetadata;
import org.gra4j.gazelle.transaction.JpaTransactionManager;
import org.gra4j.gazelle.transaction.SpringTransactionManager;
import org.gra4j.gazelle.transaction.TransactionManager;
import org.gra4j.gazelle.transaction.TransactionalType;
import org.gra4j.gazelle.util.ClassScanner;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * gazelle
 * org.gra4j.gazelle.repository
 *
 * @author tom.long
 */
public class JpaContext {

    private String[] basePackages;

    private static EntityManager em;

    private static PlatformTransactionManager tx;

    private static TransactionalType txType;

    final static Map<Class,RepositoryMetadata> repositoryMetadata = new HashMap<>();

    Set<Class> repositoryClasses = new HashSet<>();

    public JpaContext(String[] basePackages, Class<? extends SimpleCrud> achievement) {
        this.basePackages = basePackages;
        parsePackages(achievement);
    }

    private void parsePackages (Class achievement) {
        repositoryClasses = ClassScanner.scan(basePackages);
        for (Class reposiotry:repositoryClasses) {
            repositoryMetadata.put(reposiotry, new RepositoryMetadata(reposiotry, reposiotry.getInterfaces(),
                                    Arrays.asList(reposiotry.getDeclaredMethods()), achievement));
        }
    }

    public static RepositoryMetadata getRepositoryMetadata(Class repository) {
        return repositoryMetadata.get(repository);
    }

    public Set<Class> getRepositoryClasses() {
        return repositoryClasses;
    }

    public void setRepositoryClasses(Set<Class> repositoryClasses) {
        this.repositoryClasses = repositoryClasses;
    }

    public void setAchievement (Class repository, Class achievement) {
        repositoryMetadata.get(repository).setAchievement(achievement);
    }

    public static EntityManager em () {
        return em;
    }

    public static void setEntityManager (EntityManager primaryEntityManager) {
        em = primaryEntityManager;
    }

    public static PlatformTransactionManager tx () {
        return tx;
    }

    public static void setSpringTransactionManager (PlatformTransactionManager springTransactionManager) {
        tx = springTransactionManager;
    }

    public static void setTransactionType (TransactionalType type) {
        txType = type;
    }

    public static TransactionManager getTransactionManager () {
        TransactionManager transactionManager;
        switch (txType) {
            case spring:
                transactionManager = new SpringTransactionManager(tx);
                break;
            default:
                transactionManager = new JpaTransactionManager(em.getTransaction());
                break;
        }
        return transactionManager;
    }

    public String[] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }

}
