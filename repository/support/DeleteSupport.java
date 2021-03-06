package org.gra4j.gazelle.repository.support;

import org.gra4j.gazelle.JPAQuery.core.Operation;
import org.gra4j.gazelle.repository.Enum.KeyType;
import org.gra4j.gazelle.repository.JpaContext;
import org.gra4j.gazelle.repository.annotation.core.Delete;
import org.gra4j.gazelle.repository.model.ParamInfo;
import org.gra4j.gazelle.transaction.TransactionManager;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaDelete;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.repository.support
 *
 * @author tom.long
 */
public class DeleteSupport extends JpaRepositorySupport {

    public DeleteSupport(Delete delete, List<ParamInfo> paramInfoList, Class entityType) {
        super(delete.value(), paramInfoList, Operation.delete, entityType);
    }

    @Override
    public Object execute(Object[] args) throws InvocationTargetException, IllegalAccessException {
        TransactionManager tx = JpaContext.getTransactionManager();
        int change;
        tx.begin();
        try {
            CriteriaDelete delete = jpaStructure.getDelete();

            delete.where(toPredicate(args, KeyType.and, KeyType.or));
            Query query = em.createQuery(delete);
            change = query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        return change;
    }
}
