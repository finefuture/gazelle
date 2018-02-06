package org.gra4j.gazelle.repository;

import org.gra4j.gazelle.repository.model.RepositoryMetadata;
import org.gra4j.gazelle.util.ClassScanner;

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

    public String[] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }

}
