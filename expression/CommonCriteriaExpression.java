package org.gra4j.gazelle.expression;

import javax.persistence.criteria.CriteriaBuilder;
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

    private CriteriaBuilder cb;

    public CommonCriteriaExpression (CriteriaBuilder cb) {
        this.cb = cb;
    }

    public Expression prod (Expression exp1, Expression exp2) {
        return cb.prod(exp1, exp2);
    }

    public Expression mod (Expression exp1, Expression exp2) {
        return cb.mod(exp1, exp2);
    }

    public Expression quot (Expression exp1, Expression exp2) {
        return cb.quot(exp1, exp2);
    }

    public Expression diff (Expression exp1, Expression exp2) {
        return cb.diff(exp1, exp2);
    }

    public Expression count (Expression exp) {
        return cb.count(exp);
    }

    public Expression sum (Expression exp) {
        return cb.sum(exp);
    }

    public Expression avg (Expression exp) {
        return cb.avg(exp);
    }

    public Expression max (Expression exp) {
        return cb.max(exp);
    }

    public Expression min (Expression exp) {
        return cb.min(exp);
    }

    public Predicate between (Expression exp, Comparable val1, Comparable val2) {
        return cb.between(exp, val1, val2);
    }

    public Predicate between (Expression exp1, Expression exp2, Expression exp3) {
        return cb.between(exp1, exp2, exp3);
    }

    public Predicate isNotNull (Expression exp) {
        return cb.isNotNull(exp);
    }

    public Predicate isNull (Expression exp) {
        return cb.isNull(exp);
    }

    public Predicate notLike (Expression exp, String val) {
        return cb.notLike(exp, val);
    }

    public Predicate like (Expression exp, String val) {
        return cb.like(exp, val);
    }

    public Predicate le (Expression exp, Comparable val) {
        return cb.lessThanOrEqualTo(exp, val);
    }

    public Predicate le (Expression exp1, Expression exp2) {
        return cb.lessThanOrEqualTo(exp1, exp2);
    }

    public Predicate ge (Expression exp, Comparable val) {
        return cb.greaterThanOrEqualTo(exp, val);
    }

    public Predicate ge (Expression exp1, Expression exp2) {
        return cb.greaterThanOrEqualTo(exp1, exp2);
    }

    public Predicate lt (Expression exp, Comparable val) {
        return cb.lessThan(exp, val);
    }

    public Predicate lt (Expression exp1, Expression exp2) {
        return cb.lessThan(exp1, exp2);
    }

    public Predicate gt (Expression exp, Comparable val) {
        return cb.greaterThan(exp, val);
    }

    public Predicate gt (Expression exp1, Expression exp2) {
        return cb.greaterThan(exp1, exp2);
    }

    public Predicate ne (Expression exp, Object val) {
        return cb.notEqual(exp, val);
    }

    public Predicate ne (Expression exp1, Expression exp2) {
        return cb.notEqual(exp1, exp2);
    }

    public Predicate eq (Expression exp, Object val) {
        return cb.equal(exp, val);
    }

    public Predicate eq (Expression exp1, Expression exp2) {
        return cb.equal(exp1, exp2);
    }

    public Predicate isEmpty (Expression exp) {
        return cb.isEmpty(exp);
    }

    public Predicate isNotEmpty (Expression exp) {
        return cb.isNotEmpty(exp);
    }

    public Predicate in (Expression exp, List vals) {
        return exp.in(vals);
    }
}
