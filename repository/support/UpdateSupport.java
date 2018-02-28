package org.gra4j.gazelle.repository.support;

import org.gra4j.gazelle.JPAQuery.core.Operation;
import org.gra4j.gazelle.repository.Enum.KeyType;
import org.gra4j.gazelle.repository.JpaContext;
import org.gra4j.gazelle.repository.annotation.core.Update;
import org.gra4j.gazelle.repository.model.ParamInfo;
import org.gra4j.gazelle.transaction.TransactionManager;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaUpdate;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.repository.support
 *
 * @author tom.long
 */
public class UpdateSupport extends JpaRepositorySupport {

    private List<ParamInfo> modifyInfoList;

    private String[] set;

    public UpdateSupport(Update modify, List<ParamInfo> paramInfoList,
                         List<ParamInfo> modifyInfoList, Class entityType) {
        super(modify.where(), paramInfoList, Operation.update, entityType);
        this.modifyInfoList = modifyInfoList;
        this.set = modify.set();
    }

    @Override
    public Object execute(Object[] args) throws InvocationTargetException, IllegalAccessException {
        TransactionManager tx = JpaContext.getTransactionManager();
        int change;
        tx.begin();
        try {
            CriteriaUpdate update = jpaStructure.getUpdate();

            toSet(update, args);
            update.where(toPredicate(args, KeyType.and, KeyType.or));
            Query query = em.createQuery(update);
            change = query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        return change;
    }

    private void toSet (CriteriaUpdate update, Object[] args) {
        int length = set.length;
        for (int i=0;i<length;i++)
            update.set(set[i], args[modifyInfoList.get(i).getPosition()]);
    }
}
