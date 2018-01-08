package org.gra4j.gazelle.modify;

import org.gra4j.gazelle.core.Jpa;
import org.gra4j.gazelle.expression.AbstractExpressionParser;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

/**
 * gazelle
 * org.gra4j.gazelle.modify
 *
 * @author tom.long
 */
public class Setter extends AbstractExpressionParser {

    private Jpa jpa;

    public Setter (Jpa jpa) {
        super(jpa);
        this.jpa = jpa;
    }

    public <Y, X extends Y> Setter set (String field, X value) {
        Path<Y> path = jpa.root().get(field);
        jpa.update().set(path, value);
        return this;
    }

    public <Y> Setter set (String field, Expression<? extends Y> expression) {
        Path<Y> path = jpa.root().get(field);
        jpa.update().<Y>set(path, expression);
        return this;
    }

}
