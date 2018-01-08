package org.gra4j.gazelle.expression;

import org.gra4j.gazelle.core.Jpa;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * gazelle
 * org.gra4j.gazelle.expression
 *
 * @author tom.long
 */
public abstract class AbstractExpressionParser extends CommonCriteriaExpression {

    private Jpa jpa;

    public AbstractExpressionParser(Jpa jpa) {
        super(jpa);
        this.jpa = jpa;
    }

    public Expression prod (String field, Number num) {
        return jpa.cb().prod(jpa.root().get(field), num);
    }

    public Expression prod (Number num, String field) {
        return jpa.cb().prod(num, jpa.root().get(field));
    }

    public Expression prod (String field1, String field2) {
        Root root = jpa.root();
        return prod(root.get(field1), root.get(field2));
    }

    public Expression mod (String field, Integer num) {
        return jpa.cb().mod(jpa.root().get(field), num);
    }

    public Expression mod (Integer num, String field) {
        return jpa.cb().mod(num, jpa.root().get(field));
    }

    public Expression mod (String field1, String field2) {
        Root root = jpa.root();
        return mod(root.get(field1), root.get(field2));
    }

    public Expression quot (String field, Number num) {
        return jpa.cb().quot(jpa.root().get(field), num);
    }

    public Expression quot (Number num, String field) {
        return jpa.cb().quot(num, jpa.root().get(field));
    }

    public Expression quot (String field1, String field2) {
        Root root = jpa.root();
        return quot(root.get(field1), root.get(field2));
    }

    public Expression diff (String field, Number num) {
        return jpa.cb().diff(jpa.root().get(field), num);
    }

    public Expression diff (Number num, String field) {
        return jpa.cb().diff(num, jpa.root().get(field));
    }

    public Expression diff (String field1, String field2) {
        Root root = jpa.root();
        return diff(root.get(field1), root.get(field2));
    }

    public Expression count (String field) {
        return count(jpa.root().get(field));
    }

    public Expression sum (String field) {
        return sum(jpa.root().get(field));
    }

    public Expression avg (String field) {
        return avg(jpa.root().get(field));
    }

    public Expression max (String field) {
        return max(jpa.root().get(field));
    }

    public Expression min (String field) {
        return min(jpa.root().get(field));
    }

    public Predicate between_ (String field, Comparable val1, Comparable val2) {
        return between_(jpa.root().get(field), val1, val2);
    }

}
