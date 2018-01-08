package org.gra4j.gazelle.core;

import org.gra4j.gazelle.expression.CommonCriteriaExpression;
import org.gra4j.gazelle.structure.EntityPool;
import org.gra4j.gazelle.structure.SpecStructure;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.core
 *
 * @author tom.long
 */
public class Special extends CommonCriteriaExpression {

    private Jpa jpa;

    private ThreadLocal<SpecStructure> specStructure = new ThreadLocal<>();

    public Special(Jpa jpa) {
        super(jpa);
        this.jpa = jpa;
    }

    public Special init () {
        specStructure.set(new SpecStructure());
        return this;
    }

    public List<Expression> expressions () {
        return specStructure.get().getExpressions();
    }

    public List<Expression> groupBy () {
        return specStructure.get().getGroupBy();
    }

    public List<String> alias () {
        return specStructure.get().getAlias();
    }

    protected void clear () {
        specStructure.remove();
    }

    public Special all () {
        Root root = jpa.root();
        Class javaType = root.getModel().getBindableJavaType();
        Field[] fields = EntityPool.getIfExist(javaType);
        Boolean aggregated = jpa.isAggregated();
        SpecStructure specStructure = this.specStructure.get();
        for (Field field:fields) {
            String fieldName = field.getName();
            Path path = (Path) root.get(fieldName).alias(fieldName);
            specStructure.addExpAndAlias(path, fieldName);
            if (aggregated)
                specStructure.addGroupBy(path);
        }
        return this;
    }

    public Special some (String... field) {
        toSome(Arrays.asList(field));
        return this;
    }

    public Special some (List<String> field) {
        toSome(field);
        return this;
    }

    private void toSome (List<String> field) {
        Root root = jpa.root();
        Boolean aggregated = jpa.isAggregated();
        SpecStructure specStructure = this.specStructure.get();
        for (String column:field) {
            Path path = (Path) root.get(column).alias(column);
            specStructure.addExpAndAlias(path, column);
            if (aggregated)
                specStructure.addGroupBy(path);
        }
    }

    public Special distinct () {
        jpa.query().distinct(true);
        return this;
    }

    public Special count (String field) {
        return count(field, "count".concat(field));
    }

    public Special count (String field, String alias) {
        Expression<Long> count = (Expression<Long>) jpa.cb().count(jpa.root().get(field)).alias(alias);
        specStructure.get().addExpAndAlias(count, alias);
        return this;
    }

    public Special sum (String field) {
        return sum(field, "sum".concat(field));
    }

    public Special sum (String field, String alias) {
        Expression<Number> sum = (Expression<Number>) jpa.cb().sum(jpa.root().get(field)).alias(alias);
        specStructure.get().addExpAndAlias(sum, alias);
        return this;
    }

    public Special nullif (String field, Object val) {
        return nullif(field, val, "nullif".concat(field));
    }

    public Special nullif (String field, Object val, String alias) {
        Expression<Object> nullif = (Expression<Object>) jpa.cb().nullif(jpa.root().get(field), val).alias(alias);
        specStructure.get().addExpAndAlias(nullif, alias);
        return this;
    }
//    public Special nullif (String field1, String field2) {
//        jpa.cb().nullif(jpa.root().get(field1), jpa.root().get(field2));
//        return this;
//    }

    public Special avg (String field) {
        return avg(field, "avg".concat(field));
    }

    public Special avg (String field, String alias) {
        Expression<Double> avg = (Expression<Double>) jpa.cb().avg(jpa.root().get(field)).alias(alias);
        specStructure.get().addExpAndAlias(avg, alias);
        return this;
    }

    public Special max (String field) {
        return max(field, "max".concat(field));
    }

    public Special max (String field, String alias) {
        Expression<Number> max = (Expression<Number>) jpa.cb().max(jpa.root().get(field)).alias(alias);
        specStructure.get().addExpAndAlias(max, alias);
        return this;
    }

    public Special trim (String field) {
        return trim(field, "trim".concat(field));
    }

    public Special trim (String field, String alias) {
        Expression trim = (Expression) jpa.cb().trim(jpa.root().get(field)).alias(alias);
        specStructure.get().addExpAndAlias(trim, alias);
        return this;
    }

    public Special min (String field) {
        return min(field, "min".concat(field));
    }

    public Special min (String field, String alias) {
        Expression<Number> min = (Expression<Number>) jpa.cb().min(jpa.root().get(field)).alias(alias);
        specStructure.get().addExpAndAlias(min, alias);
        return this;
    }

    public Special upper (String field) {
        return upper(field, "upper".concat(field));
    }

    public Special upper (String field, String alias) {
        Expression upper = (Expression) jpa.cb().upper(jpa.root().get(field)).alias(alias);
        specStructure.get().addExpAndAlias(upper, alias);
        return this;
    }

    public Special lower (String field) {
        return lower(field, "lower".concat(field));
    }

    public Special lower (String field, String alias) {
        Expression lower = (Expression) jpa.cb().lower(jpa.root().get(field)).alias(alias);
        specStructure.get().addExpAndAlias(lower, alias);
        return this;
    }

    public Special subString (String field, int start) {
        return subString(field, start, "subString".concat(field));
    }

    public Special subString (String field, int start, String alias) {
        Expression substring = (Expression) jpa.cb().substring(jpa.root().get(field), start).alias(alias);
        specStructure.get().addExpAndAlias(substring, alias);
        return this;
    }

    public Special subString (String field, int start, int end) {
        return subString(field, start, end, "subString_e".concat(field));
    }

    public Special subString (String field, int start, int end, String alias) {
        Expression substring = (Expression) jpa.cb().substring(jpa.root().get(field), start, end).alias(alias);
        specStructure.get().addExpAndAlias(substring, alias);
        return this;
    }

    public Special concat (String field1, String field2, String alias) {
        Root root = jpa.root();
        Expression concat = (Expression) jpa.cb().concat(root.get(field1), root.get(field2)).alias(alias);
        specStructure.get().addExpAndAlias(concat, alias);
        return this;
    }

    public Special concat_s (String field, String val) {
        return concat_s(field, val, "concat_s".concat(field));
    }

    public Special concat_s (String field, String val, String alias) {
        Expression concat = (Expression) jpa.cb().concat(jpa.root().get(field), val).alias(alias);
        specStructure.get().addExpAndAlias(concat, alias);
        return this;
    }

    public Special s_concat (String val, String field) {
        return s_concat( val, field, "s_concat".concat(field));
    }

    public Special s_concat (String val, String field, String alias) {
        Expression concat = (Expression) jpa.cb().concat(val, jpa.root().get(field)).alias(alias);
        specStructure.get().addExpAndAlias(concat, alias);
        return this;
    }

}
