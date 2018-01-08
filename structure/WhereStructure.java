package org.gra4j.gazelle.structure;

import org.gra4j.gazelle.core.Jpa;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.*;

/**
 * gazelle
 * org.gra4j.gazelle.structure
 *
 * @author tom.long
 */
public class WhereStructure implements Serializable {

    private List<Predicate> restrictions;

    private List<Order> order;

    private Set<Expression> groupBy;

    private Predicate predicate;

    private Predicate predicate_h;

    private Boolean offset;

    private int[] page;

    public WhereStructure (Jpa jpa) {
        restrictions = new ArrayList<>();
        order = new ArrayList<>();
        groupBy = new HashSet<>();
        predicate = jpa.cb().conjunction();
        predicate_h = jpa.cb().conjunction();
        offset = Boolean.TRUE;
        page = new int[2];
        page[1] = -1;
    }

    public List<Predicate> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<Predicate> restrictions) {
        this.restrictions = restrictions;
    }

    public void addRestriction (Predicate restriction) {
        restrictions.add(restriction);
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public void addOrder (Order order) {
        this.order.add(order);
    }

    public Set<Expression> getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(Set<Expression> groupBy) {
        this.groupBy = groupBy;
    }

    public void addGroupBy (Expression groupBy) {
        this.groupBy.add(groupBy);
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    public Predicate getPredicate_h() {
        return predicate_h;
    }

    public void setPredicate_h(Predicate predicate_h) {
        this.predicate_h = predicate_h;
    }

    public Boolean getOffset() {
        return offset;
    }

    public void setOffset(Boolean offset) {
        this.offset = offset;
    }

    public int[] getPage() {
        return page;
    }

    public void setPage(int[] page) {
        this.page = page;
    }

    public void first (int first) {
        page[0] = first;
    }

    public void max (int max) {
        page[1] = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WhereStructure)) return false;

        WhereStructure that = (WhereStructure) o;

        if (!getRestrictions().equals(that.getRestrictions())) return false;
        if (!getOrder().equals(that.getOrder())) return false;
        if (!getGroupBy().equals(that.getGroupBy())) return false;
        if (!getPredicate().equals(that.getPredicate())) return false;
        if (!getPredicate_h().equals(that.getPredicate_h())) return false;
        if (!getOffset().equals(that.getOffset())) return false;
        return Arrays.equals(getPage(), that.getPage());
    }

    @Override
    public int hashCode() {
        int result = getRestrictions().hashCode();
        result = 31 * result + getOrder().hashCode();
        result = 31 * result + getGroupBy().hashCode();
        result = 31 * result + getPredicate().hashCode();
        result = 31 * result + getPredicate_h().hashCode();
        result = 31 * result + getOffset().hashCode();
        result = 31 * result + Arrays.hashCode(getPage());
        return result;
    }

    @Override
    public String toString() {
        return "WhereStructure{" +
                "restrictions=" + restrictions +
                ", order=" + order +
                ", groupBy=" + groupBy +
                ", predicate=" + predicate +
                ", predicate_h=" + predicate_h +
                ", offset=" + offset +
                ", page=" + Arrays.toString(page) +
                '}';
    }
}
