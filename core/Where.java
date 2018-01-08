package org.gra4j.gazelle.core;

import org.gra4j.gazelle.chain.CheckChain;
import org.gra4j.gazelle.expression.AbstractExpressionParser;
import org.gra4j.gazelle.structure.WhereStructure;

import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * gazelle
 * org.gra4j.gazelle.core
 *
 * @author tom.long
 */
public class Where extends AbstractExpressionParser {

    private Jpa jpa;

    private ThreadLocal<WhereStructure> whereStructure = new ThreadLocal<>();

    private ThreadLocal<CheckChain> check = new ThreadLocal<>();

    public Where(Jpa jpa) {
        super(jpa);
        this.jpa = jpa;
    }

    public Where init () {
        whereStructure.set(new WhereStructure(jpa));
        check.set(new CheckChain());
        return this;
    }

    public Predicate getPredicate () {
        toPredicate();
        return whereStructure.get().getPredicate();
    }

    public List<Order> order () {
        return whereStructure.get().getOrder();
    }

    public Set<Expression> groupBy () {
        return whereStructure.get().getGroupBy();
    }

    public Predicate having_ () {
        Predicate predicate = whereStructure.get().getPredicate_h();
        return predicate.getExpressions().size()>0?predicate:null;
    }

    public int[] page () {
        return whereStructure.get().getPage();
    }

    protected void clear () {
        whereStructure.remove();
        check.remove();
    }

    public Where and () {
        WhereStructure whereStructure = this.whereStructure.get();
        if (whereStructure.getPredicate().getExpressions().size()>0) {
            toPredicate();
            whereStructure.setOffset(Boolean.TRUE);
        }
        return this;
    }

    public Where or () {
        toPredicate();
        whereStructure.get().setOffset(Boolean.TRUE);
        return this;
    }

    private void toPredicate () {
        CriteriaBuilder cb = jpa.cb();
        WhereStructure whereStructure = this.whereStructure.get();
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
        WhereStructure whereStructure = this.whereStructure.get();
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
        WhereStructure whereStructure = this.whereStructure.get();
        CriteriaBuilder cb = jpa.cb();
        for (Expression exp:exps) {
            Order asc = cb.asc(exp);
            whereStructure.addOrder(asc);
        }
    }

    private void toDesc (List<String> field) {
        Root root = jpa.root();
        WhereStructure whereStructure = this.whereStructure.get();
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
        WhereStructure whereStructure = this.whereStructure.get();
        CriteriaBuilder cb = jpa.cb();
        for (Expression exp:exps) {
            Order desc = cb.desc(exp);
            whereStructure.addOrder(desc);
        }
    }
    
    public Where having (Predicate... predicates) {
        CriteriaBuilder cb = jpa.cb();
        WhereStructure whereStructure = this.whereStructure.get();
        Predicate having = whereStructure.getPredicate_h();
        whereStructure.setPredicate_h(cb.and(having, cb.and(predicates)));
        return this;
    }

    public Where orHaving (Predicate... predicates) {
        CriteriaBuilder cb = jpa.cb();
        WhereStructure whereStructure = this.whereStructure.get();
        Predicate having = whereStructure.getPredicate_h();
        whereStructure.setPredicate_h(cb.and(having, cb.or(predicates)));
        return this;
    }

    public Where groupBy (String... field) {
        return groupBy(Arrays.asList(field));
    }

    public Where groupBy (List<String> field) {
        WhereStructure whereStructure = this.whereStructure.get();
        Root root = jpa.root();
        for (String colmun:field)
            whereStructure.addGroupBy(root.get(colmun));

        return this;
    }

    public Where first (int i) {
        whereStructure.get().first(i);
        return this;
    }

    public Where max (int i) {
        whereStructure.get().max(i);
        return this;
    }

    public Where addChecker (String methodName, Class targetClass) {
        check.get().addChecker(methodName, targetClass);
        return this;
    }

    public Where checkNullValue () {
        check.get().setCheckNull();
        return this;
    }

    public Where checkEmptyValue () {
        check.get().setCheckEmpty();
        return this;
    }

    private boolean check (Object value) {
        CheckChain checkChain = check.get();
        return checkChain.check(checkChain, value);
    }

    public Where eq (String field, Object val) {
        return eq(jpa.root().get(field), val);
    }

    public Where equal (String field1, String field2) {
        Root root = jpa.root();
        return eq(root.get(field1), root.get(field2));
    }

    public Where eq (Expression exp, Object val) {
        if (check(val))
            whereStructure.get().addRestriction(eq_(exp, val));
        return this;
    }

    public Where eq (Expression exp1, Expression exp2) {
        whereStructure.get().addRestriction(eq_(exp1, exp2));
        return this;
    }

    public Where ne (String field, Object val) {
        return ne(jpa.root().get(field), val);
    }

    public Where notEqual (String field1, String field2) {
        Root root = jpa.root();
        return ne(root.get(field1), root.get(field2));
    }

    public Where ne (Expression exp, Object val) {
        if (check(val))
            whereStructure.get().addRestriction(ne_(exp, val));
        return this;
    }

    public Where ne (Expression exp1, Expression exp2) {
        whereStructure.get().addRestriction(ne_(exp1, exp2));
        return this;
    }

    public Where gt (String field, Comparable val) {
        return gt(jpa.root().get(field), val);
    }

    public Where greaterThan (String field1, String field2) {
        Root root = jpa.root();
        return gt(root.get(field1), root.get(field2));
    }

    public Where gt (Expression exp, Comparable val) {
        if (check(val))
            whereStructure.get().addRestriction(gt_(exp, val));
        return this;
    }

    public Where gt (Expression exp1, Expression exp2) {
        whereStructure.get().addRestriction(gt_(exp1, exp2));
        return this;
    }

    public Where lt (String field, Comparable val) {
        return lt(jpa.root().get(field), val);
    }

    public Where lessThan (String field1, String field2) {
        Root root = jpa.root();
        return lt(root.get(field1), root.get(field2));
    }

    public Where lt (Expression exp, Comparable val) {
        if (check(val))
            whereStructure.get().addRestriction(lt_(exp, val));
        return this;
    }

    public Where lt (Expression exp1, Expression exp2) {
        whereStructure.get().addRestriction(lt_(exp1, exp2));
        return this;
    }

    public Where ge (String field, Comparable val) {
        return ge(jpa.root().get(field), val);
    }

    public Where greaterThanOrEqualTo (String field1, String field2) {
        Root root = jpa.root();
        return ge(root.get(field1), root.get(field2));
    }

    public Where ge (Expression exp, Comparable val) {
        if (check(val))
            whereStructure.get().addRestriction(ge_(exp, val));
        return this;
    }

    public Where ge (Expression exp1, Expression exp2) {
        whereStructure.get().addRestriction(ge_(exp1, exp2));
        return this;
    }

    public Where le (String field, Comparable val) {
        return le(jpa.root().get(field), val);
    }

    public Where lessThanOrEqualTo (String field1, String field2) {
        Root root = jpa.root();
        return le(root.get(field1), root.get(field2));
    }

    public Where le (Expression exp, Comparable val) {
        if (check(val))
            whereStructure.get().addRestriction(le_(exp, val));
        return this;
    }

    public Where le (Expression exp1, Expression exp2) {
        whereStructure.get().addRestriction(le_(exp1, exp2));
        return this;
    }

    public Where like (String field, String val) {
        return like(jpa.root().get(field), val);
    }

    public Where like (Expression exp, String val) {
        if (check(val))
            whereStructure.get().addRestriction(like_(exp, val));
        return this;
    }

    public Where notLike (String field, String val) {
        return notLike(jpa.root().get(field), val);
    }

    public Where notLike (Expression exp, String val) {
        if (check(val))
            whereStructure.get().addRestriction(notLike_(exp, val));
        return this;
    }

    public Where in (String field, Object... vals) {
        return in(field, Arrays.asList(vals));
    }

    public Where in (String field, List vals) {
        return in(jpa.root().get(field), vals);
    }

    public Where in (Expression exp, List vals) {
        if (check(vals))
            whereStructure.get().addRestriction(in_(exp, vals));
        return this;
    }

    public Where isNull (String field) {
        return isNull(jpa.root().get(field));
    }

    public Where isNull (Expression exp) {
        whereStructure.get().addRestriction(isNull_(exp));
        return this;
    }

    public Where isNotNull (String field) {
        return isNotNull(jpa.root().get(field));
    }

    public Where isNotNull (Expression exp) {
        whereStructure.get().addRestriction(isNotNull_(exp));
        return this;
    }

    public Where between (String field, Comparable val1, Comparable val2) {
        return between(jpa.root().get(field), val1, val2);
    }

    public Where between (Expression exp, Comparable val1, Comparable val2) {
        if (check(val1)&&check(val2))
            whereStructure.get().addRestriction(between_(exp, val1, val2));
        return this;
    }

    public Where between (Expression exp1, Expression exp2, Expression exp3) {
        whereStructure.get().addRestriction(between_(exp1, exp2, exp3));
        return this;
    }

    public Where isEmpty (String field) {
        return isEmpty(jpa.root().get(field));
    }

    public Where isEmpty (Expression exp) {
        whereStructure.get().addRestriction(isEmpty_(exp));
        return this;
    }

    public Where isNotEmpty (String field) {
        return isNotEmpty(jpa.root().get(field));
    }

    public Where isNotEmpty (Expression exp) {
        whereStructure.get().addRestriction(isNotEmpty_(exp));
        return this;
    }

}
