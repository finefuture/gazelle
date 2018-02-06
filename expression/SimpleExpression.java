package org.gra4j.gazelle.expression;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.expression
 *
 * @author tom.long
 */
public class SimpleExpression extends AbstractExpressionParser {

    private CriteriaBuilder cb;

    private Root root;

    public SimpleExpression(CriteriaBuilder cb, Root root) {
        super(cb, root);
        this.cb = cb;
        this.root = root;
    }

    public Predicate eq (String field, Object val) {
        return eq(root.get(field), val);
    }

    public Predicate eq (String field1, String field2) {
        return eq(root.get(field1), root.get(field2));
    }

    public Predicate ne (String field, Object val) {
        return ne(root.get(field), val);
    }

    public Predicate ne (String field1, String field2) {
        return ne(root.get(field1), root.get(field2));
    }

    public Predicate gt (String field, Comparable val) {
        return gt(root.get(field), val);
    }

    public Predicate gt (String field1, String field2) {
        return gt(root.get(field1), root.get(field2));
    }

    public Predicate lt (String field, Comparable val) {
        return lt(root.get(field), val);
    }

    public Predicate lt (String field1, String field2) {
        return lt(root.get(field1), root.get(field2));
    }

    public Predicate ge (String field, Comparable val) {
        return ge(root.get(field), val);
    }

    public Predicate ge (String field1, String field2) {
        return ge(root.get(field1), root.get(field2));
    }

    public Predicate le (String field, Comparable val) {
        return le(root.get(field), val);
    }

    public Predicate le (String field1, String field2) {
        return le(root.get(field1), root.get(field2));
    }

    public Predicate like (String field, String val) {
        return like(root.get(field), val);
    }

    public Predicate notLike (String field, String val) {
        return notLike(root.get(field), val);
    }

    public Predicate in (String field, Object... vals) {
        return in(field, Arrays.asList(vals));
    }

    public Predicate in (String field, List vals) {
        return in(root.get(field), vals);
    }

    public Predicate isNull (String field) {
        return isNull(root.get(field));
    }

    public Predicate isNotNull (String field) {
        return isNotNull(root.get(field));
    }

    public Predicate between (String field, Comparable val1, Comparable val2) {
        return between(root.get(field), val1, val2);
    }

    public Predicate isEmpty (String field) {
        return isEmpty(root.get(field));
    }

    public Predicate isNotEmpty (String field) {
        return isNotEmpty(root.get(field));
    }

}
