package org.gra4j.gazelle.JPAQuery.core;

import org.gra4j.gazelle.JPAQuery.builder.JPAQuery;
import org.gra4j.gazelle.chain.CheckChain;
import org.gra4j.gazelle.expression.AbstractExpressionParser;
import org.gra4j.gazelle.structure.WhereStructure;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery.core
 *
 * @author tom.long
 */
public class Where extends AbstractExpressionParser {

    private Jpa jpa;

    private WhereStructure whereStructure;

    private CheckChain check;

    private JPAQuery jpaQuery;

    public Where(Jpa jpa) {
        this(jpa, null);
    }

    public Where(Jpa jpa, JPAQuery jpaQuery) {
        super(jpa.cb(), jpa.root());
        this.jpa = jpa;
        this.whereStructure = new WhereStructure(jpa);
        this.check = new CheckChain();
        this.jpaQuery = jpaQuery;
    }

    public JPAQuery build () {
        return this.jpaQuery;
    }

    public Predicate getPredicate () {
        toPredicate();
        return whereStructure.getPredicate();
    }

    public List<Order> order () {
        return whereStructure.getOrder();
    }

    public Set<Expression> groupBy () {
        return whereStructure.getGroupBy();
    }

    public Predicate having_ () {
        Predicate predicate = whereStructure.getPredicate_h();
        return predicate.getExpressions().size()>0?predicate:null;
    }

    public int[] page () {
        return whereStructure.getPage();
    }

    public Where and () {
        WhereStructure whereStructure = this.whereStructure;
        if (whereStructure.getPredicate().getExpressions().size()>0) {
            toPredicate();
            whereStructure.setOffset(Boolean.FALSE);
        }
        return this;
    }

    public Where or () {
        toPredicate();
        whereStructure.setOffset(Boolean.TRUE);
        return this;
    }

    private void toPredicate () {
        CriteriaBuilder cb = jpa.cb();
        WhereStructure whereStructure = this.whereStructure;
        List<Predicate> restrictions = whereStructure.getRestrictions();
        Predicate predicate = whereStructure.getPredicate();
        if (whereStructure.getOffset())
            whereStructure.setPredicate(cb.and(predicate, cb.and(restrictions.toArray(new Predicate[restrictions.size()]))));
        else
            whereStructure.setPredicate(cb.and(predicate, cb.or(restrictions.toArray(new Predicate[restrictions.size()]))));
        restrictions.clear();
    }

    public Where asc (String... field) {
        toAsc(Arrays.asList(field));
        return this;
    }

    public Where asc (List<String> field) {
        toAsc(field);
        return this;
    }

    public Where asc (Expression... exps){
        toAsc(exps);
        return this;
    }

    public Where asc (ArrayList<Expression> exps){
        toAsc(exps.toArray(new Expression[exps.size()]));
        return this;
    }

    public Where desc (String... field) {
        toDesc(Arrays.asList(field));
        return this;
    }

    public Where desc (List<String> field) {
        toDesc(field);
        return this;
    }

    public Where desc (Expression... exps){
        toDesc(exps);
        return this;
    }

    public Where desc (ArrayList<Expression> exps){
        toDesc(exps.toArray(new Expression[exps.size()]));
        return this;
    }

    private void toAsc (List<String> field) {
        Root root = jpa.root();
        WhereStructure whereStructure = this.whereStructure;
        CriteriaBuilder cb = jpa.cb();
        Boolean aggregated = jpa.isAggregated();
        for (String colmun:field) {
            Path path = root.get(colmun);
            Order asc = cb.asc(path);
            whereStructure.addOrder(asc);
            if (aggregated)
                whereStructure.addGroupBy(path);
        }
    }

    private void toAsc (Expression... exps) {
        WhereStructure whereStructure = this.whereStructure;
        CriteriaBuilder cb = jpa.cb();
        for (Expression exp:exps) {
            Order asc = cb.asc(exp);
            whereStructure.addOrder(asc);
        }
    }

    private void toDesc (List<String> field) {
        Root root = jpa.root();
        WhereStructure whereStructure = this.whereStructure;
        CriteriaBuilder cb = jpa.cb();
        Boolean aggregated = jpa.isAggregated();
        for (String colmun:field) {
            Path path = root.get(colmun);
            Order desc = cb.desc(path);
            whereStructure.addOrder(desc);
            if (aggregated)
                whereStructure.addGroupBy(path);
        }
    }

    private void toDesc (Expression... exps) {
        WhereStructure whereStructure = this.whereStructure;
        CriteriaBuilder cb = jpa.cb();
        for (Expression exp:exps) {
            Order desc = cb.desc(exp);
            whereStructure.addOrder(desc);
        }
    }

    public Where having (Predicate... predicates) {
        CriteriaBuilder cb = jpa.cb();
        WhereStructure whereStructure = this.whereStructure;
        Predicate having = whereStructure.getPredicate_h();
        whereStructure.setPredicate_h(cb.and(having, cb.and(predicates)));
        return this;
    }

    public Where orHaving (Predicate... predicates) {
        CriteriaBuilder cb = jpa.cb();
        WhereStructure whereStructure = this.whereStructure;
        Predicate having = whereStructure.getPredicate_h();
        whereStructure.setPredicate_h(cb.and(having, cb.or(predicates)));
        return this;
    }

    public Where groupBy (String... field) {
        return groupBy(Arrays.asList(field));
    }

    public Where groupBy (List<String> field) {
        WhereStructure whereStructure = this.whereStructure;
        Root root = jpa.root();
        for (String colmun:field)
            whereStructure.addGroupBy(root.get(colmun));

        return this;
    }

    public Where first (int i) {
        whereStructure.first(i);
        return this;
    }

    public Where max (int i) {
        whereStructure.max(i);
        return this;
    }

    public Where addChecker (String methodName, Class targetClass) {
        check.addChecker(methodName, targetClass);
        return this;
    }

    public Where checkNullValue () {
        check.setCheckNull();
        return this;
    }

    public Where checkEmptyValue () {
        check.setCheckEmpty();
        return this;
    }

    private boolean check (Object value) {
        CheckChain checkChain = check;
        return checkChain.check(checkChain, value);
    }

    public Where eq (String field, Object val) {
        return eq_(jpa.root().get(field), val);
    }

    public Where equal (String field1, String field2) {
        Root root = jpa.root();
        return eq_(root.get(field1), root.get(field2));
    }

    public Where eq_ (Expression exp, Object val) {
        if (check(val))
            whereStructure.addRestriction(eq(exp, val));
        return this;
    }

    public Where eq_ (Expression exp1, Expression exp2) {
        whereStructure.addRestriction(eq(exp1, exp2));
        return this;
    }

    public Where ne (String field, Object val) {
        return ne_(jpa.root().get(field), val);
    }

    public Where notEqual (String field1, String field2) {
        Root root = jpa.root();
        return ne_(root.get(field1), root.get(field2));
    }

    public Where ne_ (Expression exp, Object val) {
        if (check(val))
            whereStructure.addRestriction(ne(exp, val));
        return this;
    }

    public Where ne_ (Expression exp1, Expression exp2) {
        whereStructure.addRestriction(ne(exp1, exp2));
        return this;
    }

    public Where gt (String field, Comparable val) {
        return gt_(jpa.root().get(field), val);
    }

    public Where greaterThan (String field1, String field2) {
        Root root = jpa.root();
        return gt_(root.get(field1), root.get(field2));
    }

    public Where gt_ (Expression exp, Comparable val) {
        if (check(val))
            whereStructure.addRestriction(gt(exp, val));
        return this;
    }

    public Where gt_ (Expression exp1, Expression exp2) {
        whereStructure.addRestriction(gt(exp1, exp2));
        return this;
    }

    public Where lt (String field, Comparable val) {
        return lt_(jpa.root().get(field), val);
    }

    public Where lessThan (String field1, String field2) {
        Root root = jpa.root();
        return lt_(root.get(field1), root.get(field2));
    }

    public Where lt_ (Expression exp, Comparable val) {
        if (check(val))
            whereStructure.addRestriction(lt(exp, val));
        return this;
    }

    public Where lt_ (Expression exp1, Expression exp2) {
        whereStructure.addRestriction(lt(exp1, exp2));
        return this;
    }

    public Where ge (String field, Comparable val) {
        return ge_(jpa.root().get(field), val);
    }

    public Where greaterThanOrEqualTo (String field1, String field2) {
        Root root = jpa.root();
        return ge_(root.get(field1), root.get(field2));
    }

    public Where ge_ (Expression exp, Comparable val) {
        if (check(val))
            whereStructure.addRestriction(ge(exp, val));
        return this;
    }

    public Where ge_ (Expression exp1, Expression exp2) {
        whereStructure.addRestriction(ge(exp1, exp2));
        return this;
    }

    public Where le (String field, Comparable val) {
        return le_(jpa.root().get(field), val);
    }

    public Where lessThanOrEqualTo (String field1, String field2) {
        Root root = jpa.root();
        return le_(root.get(field1), root.get(field2));
    }

    public Where le_ (Expression exp, Comparable val) {
        if (check(val))
            whereStructure.addRestriction(le(exp, val));
        return this;
    }

    public Where le_ (Expression exp1, Expression exp2) {
        whereStructure.addRestriction(le(exp1, exp2));
        return this;
    }

    public Where like (String field, String val) {
        return like_(jpa.root().get(field), val);
    }

    public Where like_ (Expression exp, String val) {
        if (check(val))
            whereStructure.addRestriction(like(exp, val));
        return this;
    }

    public Where notLike (String field, String val) {
        return notLike_(jpa.root().get(field), val);
    }

    public Where notLike_ (Expression exp, String val) {
        if (check(val))
            whereStructure.addRestriction(notLike(exp, val));
        return this;
    }

    public Where in (String field, Object... vals) {
        return in(field, Arrays.asList(vals));
    }

    public Where in (String field, List vals) {
        return in_(jpa.root().get(field), vals);
    }

    public Where in_ (Expression exp, List vals) {
        if (check(vals))
            whereStructure.addRestriction(in(exp, vals));
        return this;
    }

    public Where isNull (String field) {
        return isNull_(jpa.root().get(field));
    }

    public Where isNull_ (Expression exp) {
        whereStructure.addRestriction(isNull(exp));
        return this;
    }

    public Where isNotNull (String field) {
        return isNotNull_(jpa.root().get(field));
    }

    public Where isNotNull_ (Expression exp) {
        whereStructure.addRestriction(isNotNull(exp));
        return this;
    }

    public Where between (String field, Comparable val1, Comparable val2) {
        return between_(jpa.root().get(field), val1, val2);
    }

    public Where between_ (Expression exp, Comparable val1, Comparable val2) {
        if (check(val1)&&check(val2))
            whereStructure.addRestriction(between(exp, val1, val2));
        return this;
    }

    public Where between_ (Expression exp1, Expression exp2, Expression exp3) {
        whereStructure.addRestriction(between(exp1, exp2, exp3));
        return this;
    }

    public Where isEmpty (String field) {
        return isEmpty_(jpa.root().get(field));
    }

    public Where isEmpty_ (Expression exp) {
        whereStructure.addRestriction(isEmpty(exp));
        return this;
    }

    public Where isNotEmpty (String field) {
        return isNotEmpty_(jpa.root().get(field));
    }

    public Where isNotEmpty_ (Expression exp) {
        whereStructure.addRestriction(isNotEmpty(exp));
        return this;
    }

}
