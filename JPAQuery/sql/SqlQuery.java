package org.gra4j.gazelle.JPAQuery.sql;

import java.util.List;
import java.util.Map;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery.query
 *
 * @author tom.long
 */
public interface SqlQuery {

    List query(String jpql);

    List query(String jpql, Class resultClass);

    List query(String jpql, Map<String, Object> param);

    List query(String jpql, List param);

    List query(String jpql, Class resultClass, Map<String, Object> param);

    List query(String jpql, Class resultClass, List param);

    List namedQuery(String name);

    List namedQuery(String name, Class resultClass);

    List namedQuery(String name, Map<String, Object> param);

    List namedQuery(String name, List param);

    List namedQuery(String name, Class resultClass, Map<String, Object> param);

    List namedQuery(String name, Class resultClass, List param);

    List nativeQuery(String sql);

    List nativeQuery(String sql, Class resultClass);

    List nativeQuery(String sql, String resultSetMapping);

    List nativeQuery(String sql, Map<String, Object> param);

    List nativeQuery(String sql, List param);

    List nativeQuery(String sql, Class resultClass, Map<String, Object> param);

    List nativeQuery(String sql, Class resultClass, List param);

    List nativeQuery(String sql, String resultSetMapping, Map<String, Object> param);

    List nativeQuery(String sql, String resultSetMapping, List param);

}
