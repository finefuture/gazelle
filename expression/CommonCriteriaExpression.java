package org.gra4j.gazelle.expression;

import org.gra4j.gazelle.core.Jpa;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.expression
 *
 * @author tom.long
 */
public abstract class CommonCriteriaExpression {

    private Jpa jpa;

    public CommonCriteriaExpression (Jpa jpa) {
        this.jpa = jpa;
    }

    public Expression prod (Expression exp1, Expression exp2) {
        return jpa.cb().prod(exp1, exp2);
    }

    public Expression mod (Expression exp1, Expression exp2) {
        return jpa.cb().mod(exp1, exp2);
    }

    public Expression quot (Expression exp1, Expression exp2) {
        return jpa.cb().quot(exp1, exp2);
    }

    public Expression diff (Expression exp1, Expression exp2) {
        return jpa.cb().diff(exp1, exp2);
    }

    public Expression count (Expression exp) {
        return jpa.cb().count(exp);
    }

    public Expression sum (Expression exp) {
        return jpa.cb().sum(exp);
    }

    public Expression avg (Expression exp) {
        return jpa.cb().avg(exp);
    }

    public Expression max (Expression exp) {
        return jpa.cb().max(exp);
    }

    public Expression min (Expression exp) {
        return jpa.cb().min(exp);
    }

    public Predicate between_ (Expression exp, Comparable val1, Comparable val2) {
        return jpa.cb().between(exp, val1, val2);
    }

    public Predicate between_ (Expression exp1, Expression exp2, Expression exp3) {
        return jpa.cb().between(exp1, exp2, exp3);
    }

    public Predicate isNotNull_ (Expression exp) {
        return jpa.cb().isNotNull(exp);
    }

    public Predicate isNull_ (Expression exp) {
        return jpa.cb().isNull(exp);
    }

    public Predicate notLike_ (Expression exp, String val) {
        return jpa.cb().notLike(exp, val);
    }

    public Predicate like_ (Expression exp, String val) {
        return jpa.cb().like(exp, val);
    }

    public Predicate le_ (Expression exp, Comparable val) {
        return jpa.cb().lessThanOrEqualTo(exp, val);
    }

    public Predicate le_ (Expression exp1, Expression exp2) {
        return jpa.cb().lessThanOrEqualTo(exp1, exp2);
    }

    public Predicate ge_ (Expression exp, Comparable val) {
        return jpa.cb().greaterThanOrEqualTo(exp, val);
    }

    public Predicate ge_ (Expression exp1, Expression exp2) {
        return jpa.cb().greaterThanOrEqualTo(exp1, exp2);
    }

    public Predicate lt_ (Expression exp, Comparable val) {
        return jpa.cb().lessThan(exp, val);
    }

    public Predicate lt_ (Expression exp1, Expression exp2) {
        return jpa.cb().lessThan(exp1, exp2);
    }

    public Predicate gt_ (Expression exp, Comparable val) {
        return jpa.cb().greaterThan(exp, val);
    }

    public Predicate gt_ (Expression exp1, Expression exp2) {
        return jpa.cb().greaterThan(exp1, exp2);
    }

    public Predicate ne_ (Expression exp, Object val) {
        return jpa.cb().notEqual(exp, val);
    }

    public Predicate ne_ (Expression exp1, Expression exp2) {
        return jpa.cb().notEqual(exp1, exp2);
    }

    public Predicate eq_ (Expression exp, Object val) {
        return jpa.cb().equal(exp, val);
    }

    public Predicate eq_ (Expression exp1, Expression exp2) {
        return jpa.cb().equal(exp1, exp2);
    }

    public Predicate isEmpty_ (Expression exp) {
        return jpa.cb().isEmpty(exp);
    }

    public Predicate isNotEmpty_ (Expression exp) {
        return jpa.cb().isNotEmpty(exp);
    }

    public Predicate in_ (Expression exp, List vals) {
        return exp.in(vals);
    }
}
