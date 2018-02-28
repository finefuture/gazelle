package org.gra4j.gazelle.repository.support;

import org.gra4j.gazelle.JPAQuery.core.Operation;
import org.gra4j.gazelle.repository.annotation.core.Where;
import org.gra4j.gazelle.repository.model.ParamInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/2/6.
 */
public abstract class JpaQuerySupport extends JpaRepositorySupport {

    private int[] pageInfo;

    public JpaQuerySupport(Where where, int[] pageInfo, List<ParamInfo> paramInfoList, Operation operation, Class entityType) {
        super(where, paramInfoList, operation, entityType);
        this.pageInfo = pageInfo;
    }

    protected int[] toPage (Object[] args) {
        int first = pageInfo[0];
        int max = pageInfo[1];
        return new int[]{first==-1 ? 0 : (int) args[first], max==-1 ? Integer.MAX_VALUE : (int) args[max]};
    }

}
