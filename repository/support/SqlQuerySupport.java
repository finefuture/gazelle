package org.gra4j.gazelle.repository.support;

import org.gra4j.gazelle.repository.JpaContext;
import org.gra4j.gazelle.repository.annotation.core.SqlQuery;
import org.gra4j.gazelle.repository.model.ParamInfo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.repository.support
 *
 * @author tom.long
 */
public class SqlQuerySupport implements RepositorySupport {

    private String value;

    private List<ParamInfo> paramInfoList;

    private boolean isNamed;

    private boolean isNative;

    private boolean modify;

    private Class resultClass;

    final static EntityManager em = JpaContext.em();

    public SqlQuerySupport (SqlQuery sqlQuery, List<ParamInfo> paramInfoList) {
        this.value = sqlQuery.value();
        this.isNamed = sqlQuery.isNamed();
        this.isNative = sqlQuery.isNative();
        this.modify = sqlQuery.modify();
        this.resultClass = sqlQuery.result();
        this.paramInfoList = paramInfoList;
    }

    @Override
    public Object execute(Object[] args) {
        Query query = toMappingQuery();

        setParameter(query, args);
        if (modify)
            return query.executeUpdate();
        return query.getResultList();
    }

    private Query toMappingQuery () {
        if (isNamed)
            return em.createNamedQuery(value, resultClass);
        else if (isNative)
            return em.createNativeQuery(value, resultClass);
        return em.createQuery(value, resultClass);
    }

    private void setParameter (Query query, Object[] args) {
        for (ParamInfo paramInfo:paramInfoList) {
            query.setParameter(paramInfo.getName(), args[paramInfo.getPosition()]);
        }
    }

}
