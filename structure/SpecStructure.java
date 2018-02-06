package org.gra4j.gazelle.structure;

import javax.persistence.criteria.Expression;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.structure
 *
 * @author tom.long
 */
public class SpecStructure implements Serializable {

    private List<Expression> expressions;

    private List<Expression> groupBy;

    private List<String> alias;

    public SpecStructure () {
        expressions = new ArrayList<>();
        groupBy = new ArrayList<>();
        alias = new ArrayList<>();
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public void addExpression (Expression expression) {
        this.expressions.add(expression);
    }

    public List<Expression> getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(List<Expression> groupBy) {
        this.groupBy = groupBy;
    }

    public void addGroupBy (Expression expression) {
        this.groupBy.add(expression);
    }

    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }

    public void addAlias (String alias) {
        this.alias.add(alias);
    }

    public void addExpAndAlias (Expression expression, String alias) {
        this.expressions.add(expression);
        this.alias.add(alias);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecStructure)) return false;

        SpecStructure that = (SpecStructure) o;

        if (getExpressions() != null ? !getExpressions().equals(that.getExpressions()) : that.getExpressions() != null)
            return false;
        if (getGroupBy() != null ? !getGroupBy().equals(that.getGroupBy()) : that.getGroupBy() != null) return false;
        return getAlias() != null ? getAlias().equals(that.getAlias()) : that.getAlias() == null;
    }

    @Override
    public int hashCode() {
        int result = getExpressions() != null ? getExpressions().hashCode() : 0;
        result = 31 * result + (getGroupBy() != null ? getGroupBy().hashCode() : 0);
        result = 31 * result + (getAlias() != null ? getAlias().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SpecStructure{" +
                "expressions=" + expressions +
                ", groupBy=" + groupBy +
                ", alias=" + alias +
                '}';
    }
}
