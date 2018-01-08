package org.gra4j.gazelle.structure;

import org.gra4j.gazelle.core.Operation;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.io.Serializable;

/**
 * gazelle
 * org.gra4j.gazelle.structure
 *
 * @author tom.long
 */
public class JpaStructure implements Serializable {

    private CriteriaBuilder cb;

    private Root root;

    private CriteriaQuery query;

    private CriteriaUpdate update;

    private CriteriaDelete delete;

    private Boolean isAggregated;

    public JpaStructure (EntityManager entityManager, Operation operation, Class clazz) {
        cb = entityManager.getCriteriaBuilder();
        isAggregated = Boolean.FALSE;
        switch (operation) {
            case tupleQuery:
                query = cb.createTupleQuery();
                root = query.from(clazz);
                break;
            case select:
                query = cb.createQuery(clazz);
                root = query.from(clazz);
                break;
            case update:
                update = cb.createCriteriaUpdate(clazz);
                root = update.from(clazz);
                break;
            case create:
                break;
            case delete:
                delete = cb.createCriteriaDelete(clazz);
                root = delete.from(clazz);
                break;
        }
    }

    public void setCb (CriteriaBuilder cb) {
        this.cb = cb;
    }

    public CriteriaBuilder getCb () {
        return this.cb;
    }

    public void setRoot (Root root) {
        this.root = root;
    }

    public Root getRoot () {
        return this.root;
    }

    public void setQuery (CriteriaQuery query) {
        this.query = query;
    }

    public CriteriaQuery getQuery () {
        return this.query;
    }

    public void setUpdate (CriteriaUpdate update) {
        this.update = update;
    }

    public CriteriaUpdate getUpdate () {
        return this.update;
    }

    public void setDelete (CriteriaDelete delete) {
        this.delete = delete;
    }

    public CriteriaDelete getDelete () {
        return this.delete;
    }

    public void setIsAggregated (Boolean aggregated) {
        this.isAggregated = aggregated;
    }

    public Boolean getIsAggregated () {
        return this.isAggregated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaStructure)) return false;

        JpaStructure that = (JpaStructure) o;

        if (!getCb().equals(that.getCb())) return false;
        if (!getRoot().equals(that.getRoot())) return false;
        if (!getQuery().equals(that.getQuery())) return false;
        if (!getUpdate().equals(that.getUpdate())) return false;
        if (!getDelete().equals(that.getDelete())) return false;
        return getIsAggregated().equals(that.getIsAggregated());
    }

    @Override
    public int hashCode() {
        int result = getCb().hashCode();
        result = 31 * result + getRoot().hashCode();
        result = 31 * result + getQuery().hashCode();
        result = 31 * result + getUpdate().hashCode();
        result = 31 * result + getDelete().hashCode();
        result = 31 * result + getIsAggregated().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "JpaStructure{" +
                "cb=" + cb +
                ", root=" + root +
                ", query=" + query +
                ", update=" + update +
                ", delete=" + delete +
                ", isAggregated=" + isAggregated +
                '}';
    }
}
