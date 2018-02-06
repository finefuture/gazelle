package org.gra4j.gazelle.repository.model;

import org.gra4j.gazelle.repository.GazelleRepository;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.repository.model
 *
 * @author tom.long
 */
public class RepositoryMetadata implements Serializable {

    private Class repositoryInterface;

    private Class[] superInterfaces;

    private List<Method> dataAccessMethods;

    private Class achievement;

    private Class entityType;

    public RepositoryMetadata(Class repositoryInterface, Class[] superInterfaces, List<Method> dataAccessMethods, Class achievement) {
        this.repositoryInterface = repositoryInterface;
        this.superInterfaces = superInterfaces;
        this.dataAccessMethods = dataAccessMethods;
        this.achievement = achievement;
        findEntityType();
    }

    private void findEntityType () {
        Type[] genericInterfaces = repositoryInterface.getGenericInterfaces();
        for (Type type:genericInterfaces) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            if (parameterizedType.getRawType().equals(GazelleRepository.class)) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                entityType = (Class) actualTypeArguments[0];
                break;
            }
        }
    }

    public Class getRepositoryInterface() {
        return repositoryInterface;
    }

    public void setRepositoryInterface(Class repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }

    public List<Method> getDataAccessMethods() {
        return dataAccessMethods;
    }

    public void setDataAccessMethods(List<Method> dataAccessMethods) {
        this.dataAccessMethods = dataAccessMethods;
    }

    public Class[] getSuperInterfaces() {
        return superInterfaces;
    }

    public void setSuperInterfaces(Class[] superInterfaces) {
        this.superInterfaces = superInterfaces;
    }

    public Class getAchievement() {
        return achievement;
    }

    public void setAchievement(Class achievement) {
        this.achievement = achievement;
    }

    public Class getEntityType() {
        return entityType;
    }

    public void setEntityType(Class entityType) {
        this.entityType = entityType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepositoryMetadata)) return false;

        RepositoryMetadata that = (RepositoryMetadata) o;

        if (getRepositoryInterface() != null ? !getRepositoryInterface().equals(that.getRepositoryInterface()) : that.getRepositoryInterface() != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(getSuperInterfaces(), that.getSuperInterfaces())) return false;
        if (getDataAccessMethods() != null ? !getDataAccessMethods().equals(that.getDataAccessMethods()) : that.getDataAccessMethods() != null)
            return false;
        if (getAchievement() != null ? !getAchievement().equals(that.getAchievement()) : that.getAchievement() != null)
            return false;
        return getEntityType() != null ? getEntityType().equals(that.getEntityType()) : that.getEntityType() == null;
    }

    @Override
    public int hashCode() {
        int result = getRepositoryInterface() != null ? getRepositoryInterface().hashCode() : 0;
        result = 31 * result + Arrays.hashCode(getSuperInterfaces());
        result = 31 * result + (getDataAccessMethods() != null ? getDataAccessMethods().hashCode() : 0);
        result = 31 * result + (getAchievement() != null ? getAchievement().hashCode() : 0);
        result = 31 * result + (getEntityType() != null ? getEntityType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RepositoryMetadata{" +
                "repositoryInterface=" + repositoryInterface +
                ", superInterfaces=" + Arrays.toString(superInterfaces) +
                ", dataAccessMethods=" + dataAccessMethods +
                ", achievement=" + achievement +
                ", entityType=" + entityType +
                '}';
    }
}
