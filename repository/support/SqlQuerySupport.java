package org.gra4j.gazelle.repository.support;

import org.gra4j.gazelle.repository.JpaContext;
import org.gra4j.gazelle.repository.annotation.core.SqlQuery;
import org.gra4j.gazelle.repository.model.ParamInfo;
import org.gra4j.gazelle.transaction.TransactionManager;

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
        return modify ? modify(args):query(args);
    }

    private Object query (Object[] args) {
        Query query = toMappingQuery();

        setParameter(query, args);
        return query.getResultList();
    }

    private Object modify (Object[] args) {
        TransactionManager tx = JpaContext.getTransactionManager();
        int change;
        tx.begin();
        try {
            Query query = toMappingQuery();

            setParameter(query, args);
            change = query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        return change;
    }

    private Query toMappingQuery () {
        return isNamed?em.createNamedQuery(value, resultClass):(isNative?em.createNativeQuery(value, resultClass):em.createQuery(value, resultClass));
    }

    private void setParameter (Query query, Object[] args) {
        for (ParamInfo paramInfo:paramInfoList) {
            query.setParameter(paramInfo.getName(), args[paramInfo.getPosition()]);
        }
    }

}
