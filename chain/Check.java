package org.gra4j.gazelle.chain;

/**
 * gazelle
 * org.gra4j.gazelle.chain
 *
 * @author tom.long
 */
public interface Check {

    boolean check(Check checkChain, Object value);

}
