package org.gra4j.gazelle.JPAQuery.core;

import org.gra4j.gazelle.JPAQuery.builder.UpdateBuilder;
import org.gra4j.gazelle.expression.AbstractExpressionParser;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery.modify
 *
 * @author tom.long
 */
public class Setter extends AbstractExpressionParser {

    private Jpa jpa;

    private UpdateBuilder updateBuilder;

    public Setter (Jpa jpa, UpdateBuilder updateBuilder) {
        super(jpa.cb(), jpa.root());
        this.jpa = jpa;
        this.updateBuilder = updateBuilder;
    }

    public UpdateBuilder build () {
        return this.updateBuilder;
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
