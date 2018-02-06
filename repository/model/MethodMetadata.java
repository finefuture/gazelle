package org.gra4j.gazelle.repository.model;

import org.gra4j.gazelle.repository.Enum.PageType;
import org.gra4j.gazelle.repository.annotation.core.*;
import org.gra4j.gazelle.repository.annotation.expression.ExpParam;
import org.gra4j.gazelle.repository.annotation.expression.ModifyParam;
import org.gra4j.gazelle.repository.annotation.expression.PageParam;
import org.gra4j.gazelle.repository.annotation.expression.SpecParam;
import org.gra4j.gazelle.repository.support.*;
import org.gra4j.gazelle.util.Checker;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.repository.model
 *
 * @author tom.long
 */
public class MethodMetadata implements Serializable {

    private Method dataAccessMethod;

    private List<ParamInfo> paramInfoList;

    private List<ParamInfo> specInfoList;

    private List<ParamInfo> modifyInfoList;

    private int[] pageInfo;

    private RepositorySupport support;

    private Class entityType;

    public MethodMetadata (Method dataAccessMethod, Class entityType) {
        Checker.notNull(dataAccessMethod, "repository dataAccessMethod must be not null");
        this.dataAccessMethod = dataAccessMethod;
        this.paramInfoList = new ArrayList<>();
        this.specInfoList = new ArrayList<>();
        this.modifyInfoList = new ArrayList<>();
        this.pageInfo = new int[]{-1,-1};
        this.entityType = entityType;
        paserParameters();
        paserMethodAnnotation();
    }

    private void paserParameters () {
        Parameter[] methodParam = dataAccessMethod.getParameters();
        int length = methodParam.length;
        for (int i = 0;i<length;i++) {
            Parameter parameter = methodParam[i];
            Class<?> type = parameter.getType();
            if (parameter.isAnnotationPresent(ExpParam.class)) {
                ExpParam expParam = parameter.getAnnotation(ExpParam.class);
                paramInfoList.add(new ParamInfo((byte) i, type, expParam.value()));
            } else if (parameter.isAnnotationPresent(PageParam.class)) {
                PageParam pageParam = parameter.getAnnotation(PageParam.class);
                if (pageParam.value()==PageType.first)
                    pageInfo[0] = i;
                else
                    pageInfo[1] = i;
            } else if (parameter.isAnnotationPresent(SpecParam.class)) {
                specInfoList.add(new ParamInfo((byte) i, type, parameter.getName()));
            } else if (parameter.isAnnotationPresent(ModifyParam.class)) {
                modifyInfoList.add(new ParamInfo((byte) i, type, parameter.getName()));
            }
        }
    }

    private void paserMethodAnnotation () {
        if (dataAccessMethod.isAnnotationPresent(Query.class))
            support = new QuerySupport(dataAccessMethod.getDeclaredAnnotation(Query.class), paramInfoList, pageInfo, entityType);
        else if (dataAccessMethod.isAnnotationPresent(SqlQuery.class))
            support = new SqlQuerySupport(dataAccessMethod.getDeclaredAnnotation(SqlQuery.class), paramInfoList);
        else if (dataAccessMethod.isAnnotationPresent(TupleQuery.class))
            support = new TupleQuerySupport(dataAccessMethod.getDeclaredAnnotation(TupleQuery.class), paramInfoList, specInfoList, pageInfo, entityType);
        else if (dataAccessMethod.isAnnotationPresent(Update.class))
            support = new UpdateSupport(dataAccessMethod.getDeclaredAnnotation(Update.class), paramInfoList, modifyInfoList, entityType);
        else if (dataAccessMethod.isAnnotationPresent(Delete.class))
            support = new DeleteSupport(dataAccessMethod.getDeclaredAnnotation(Delete.class), paramInfoList, entityType);
    }

    public Method getDataAccessMethod() {
        return dataAccessMethod;
    }

    public void setDataAccessMethod(Method dataAccessMethod) {
        this.dataAccessMethod = dataAccessMethod;
    }

    public List<ParamInfo> getParamInfoList() {
        return paramInfoList;
    }

    public void setParamInfoList(List<ParamInfo> paramInfoList) {
        this.paramInfoList = paramInfoList;
    }

    public List<ParamInfo> getSpecInfoList() {
        return specInfoList;
    }

    public void setSpecInfoList(List<ParamInfo> specInfoList) {
        this.specInfoList = specInfoList;
    }

    public int[] getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(int[] pageInfo) {
        this.pageInfo = pageInfo;
    }

    public RepositorySupport getSupport() {
        return support;
    }

    public void setSupport(RepositorySupport support) {
        this.support = support;
    }

    public Class getEntityType() {
        return entityType;
    }

    public void setEntityType(Class entityType) {
        this.entityType = entityType;
    }

    public List<ParamInfo> getModifyInfoList() {
        return modifyInfoList;
    }

    public void setModifyInfoList(List<ParamInfo> modifyInfoList) {
        this.modifyInfoList = modifyInfoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodMetadata)) return false;

        MethodMetadata that = (MethodMetadata) o;

        if (getDataAccessMethod() != null ? !getDataAccessMethod().equals(that.getDataAccessMethod()) : that.getDataAccessMethod() != null)
            return false;
        if (getParamInfoList() != null ? !getParamInfoList().equals(that.getParamInfoList()) : that.getParamInfoList() != null)
            return false;
        if (getSpecInfoList() != null ? !getSpecInfoList().equals(that.getSpecInfoList()) : that.getSpecInfoList() != null)
            return false;
        if (getModifyInfoList() != null ? !getModifyInfoList().equals(that.getModifyInfoList()) : that.getModifyInfoList() != null)
            return false;
        if (!Arrays.equals(getPageInfo(), that.getPageInfo())) return false;
        if (getSupport() != null ? !getSupport().equals(that.getSupport()) : that.getSupport() != null) return false;
        return getEntityType() != null ? getEntityType().equals(that.getEntityType()) : that.getEntityType() == null;
    }

    @Override
    public int hashCode() {
        int result = getDataAccessMethod() != null ? getDataAccessMethod().hashCode() : 0;
        result = 31 * result + (getParamInfoList() != null ? getParamInfoList().hashCode() : 0);
        result = 31 * result + (getSpecInfoList() != null ? getSpecInfoList().hashCode() : 0);
        result = 31 * result + (getModifyInfoList() != null ? getModifyInfoList().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(getPageInfo());
        result = 31 * result + (getSupport() != null ? getSupport().hashCode() : 0);
        result = 31 * result + (getEntityType() != null ? getEntityType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MethodMetadata{" +
                "dataAccessMethod=" + dataAccessMethod +
                ", paramInfoList=" + paramInfoList +
                ", specInfoList=" + specInfoList +
                ", modifyInfoList=" + modifyInfoList +
                ", pageInfo=" + Arrays.toString(pageInfo) +
                ", support=" + support +
                ", entityType=" + entityType +
                '}';
    }
}
