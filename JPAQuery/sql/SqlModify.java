package org.gra4j.gazelle.JPAQuery.sql;

import java.util.List;
import java.util.Map;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery.modify
 *
 * @author tom.long
 */
public interface SqlModify {

    int modify(String jpql);

    int modify(String jpql, Map<String, Object> param);

    int modify(String jpql, List param);

    int namedModify(String name);

    int namedModify(String name, Map<String, Object> param);

    int namedModify(String name, List param);

    int nativeModify(String sql);

    int nativeModify(String sql, Map<String, Object> param);

    int nativeModify(String sql, List param);

}
