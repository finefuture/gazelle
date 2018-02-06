package org.gra4j.gazelle.expression;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * gazelle
 * org.gra4j.gazelle.expression
 *
 * @author tom.long
 */
public abstract class AbstractExpressionParser extends CommonCriteriaExpression {

    private CriteriaBuilder cb;

    private Root root;

    public AbstractExpressionParser(CriteriaBuilder cb, Root root) {
        super(cb);
        this.cb = cb;
        this.root = root;
    }

    public Expression prod (String field, Number num) {
        return cb.prod(root.get(field), num);
    }

    public Expression prod (Number num, String field) {
        return cb.prod(num, root.get(field));
    }

    public Expression prod (String field1, String field2) {
        return prod(root.get(field1), root.get(field2));
    }

    public Expression mod (String field, Integer num) {
        return cb.mod(root.get(field), num);
    }

    public Expression mod (Integer num, String field) {
        return cb.mod(num, root.get(field));
    }

    public Expression mod (String field1, String field2) {
        return mod(root.get(field1), root.get(field2));
    }

    public Expression quot (String field, Number num) {
        return cb.quot(root.get(field), num);
    }

    public Expression quot (Number num, String field) {
        return cb.quot(num, root.get(field));
    }

    public Expression quot (String field1, String field2) {
        return quot(root.get(field1), root.get(field2));
    }

    public Expression diff (String field, Number num) {
        return cb.diff(root.get(field), num);
    }

    public Expression diff (Number num, String field) {
        return cb.diff(num, root.get(field));
    }

    public Expression diff (String field1, String field2) {
        return diff(root.get(field1), root.get(field2));
    }

    public Expression count (String field) {
        return count(root.get(field));
    }

    public Expression sum (String field) {
        return sum(root.get(field));
    }

    public Expression avg (String field) {
        return avg(root.get(field));
    }

    public Expression max (String field) {
        return max(root.get(field));
    }

    public Expression min (String field) {
        return min(root.get(field));
    }

}
