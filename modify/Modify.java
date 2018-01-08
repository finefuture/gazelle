package org.gra4j.gazelle.modify;

import org.gra4j.gazelle.core.Where;

/**
 * gazelle
 * org.gra4j.gazelle.modify
 *
 * @author tom.long
 */
public interface Modify {

    int delete(Where where);

    int deleteAll();

    int deleteAll(Class targetClass);

    int update();

    int update(Where where);

}
