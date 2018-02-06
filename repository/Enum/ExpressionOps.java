package org.gra4j.gazelle.repository.Enum;

/**
 * gazelle
 * org.gra4j.gazelle.repository.Enum
 *
 * @author tom.long
 */
public enum ExpressionOps {

    prod(false),

    mod(false),

    quot(false),

    diff(false),

    count(false),

    sum(false),

    avg(false),

    max(false),

    min(false),

    between(true),

    isNotNull(true),

    isNull(true),

    notLike(true),

    like(true),

    le(true),

    ge(true),

    lt(true),

    gt(true),

    ne(true),

    eq(true),

    isEmpty(true),

    isNotEmpty(true),

    in(true);

    public boolean isPredicate;

    ExpressionOps (boolean isPredicate) {
        this.isPredicate = isPredicate;
    }

}
