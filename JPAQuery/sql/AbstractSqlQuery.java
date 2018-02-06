package org.gra4j.gazelle.JPAQuery.sql;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * gazelle
 * org.gra4j.gazelle.JPAQuery.query
 *
 * @author tom.long
 */
public class AbstractSqlQuery  implements SqlQuery {

    private EntityManager em;

    public AbstractSqlQuery (EntityManager em) {
        this.em = em;
    }

    @Override
    public List query (String jpql) {
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    @Override
    public List query (String jpql, Class resultClass) {
        TypedQuery query = em.createQuery(jpql, resultClass);
        return query.getResultList();
    }

    @Override
    public List query (String jpql, Map<String,Object> param) {
        Query query = em.createQuery(jpql);
        setParameter(query, param);
        return query.getResultList();
    }

    @Override
    public List query (String jpql, List param) {
        Query query = em.createQuery(jpql);
        setParameter(query, param);
        return query.getResultList();
    }

    @Override
    public List query (String jpql, Class resultClass, Map<String,Object> param) {
        TypedQuery query = em.createQuery(jpql, resultClass);
        setParameter(query, param);
        return query.getResultList();
    }

    @Override
    public List query (String jpql, Class resultClass, List param) {
        TypedQuery query = em.createQuery(jpql, resultClass);
        setParameter(query, param);
        return query.getResultList();
    }

    @Override
    public List namedQuery (String name) {
        Query query = em.createNamedQuery(name);
        return query.getResultList();
    }

    @Override
    public List namedQuery (String name, Class resultClass) {
        Query query = em.createNamedQuery(name, resultClass);
        return query.getResultList();
    }

    @Override
    public List namedQuery (String name, Map<String,Object> param) {
        Query query = em.createNamedQuery(name);
        setParameter(query, param);
        return query.getResultList();
    }

    @Override
    public List namedQuery (String name, List param) {
        Query query = em.createNamedQuery(name);
        setParameter(query, param);
        return query.getResultList();
    }

    @Override
    public List namedQuery (String name, Class resultClass, Map<String,Object> param) {
        Query query = em.createNamedQuery(name, resultClass);
        setParameter(query, param);
        return query.getResultList();
    }

    @Override
    public List namedQuery (String name, Class resultClass, List param) {
        Query query = em.createNamedQuery(name, resultClass);
        setParameter(query, param);
        return query.getResultList();
    }

    @Override
    public List nativeQuery (String sql) {
        Query query = em.createNativeQuery(sql);
        return query.getResultList();
    }

    @Override
    public List nativeQuery (String sql, Class resultClass) {
        Query query = em.createNativeQuery(sql, resultClass);
        return query.getResultList();
    }

    @Override
    public List nativeQuery (String sql, String resultSetMapping) {
        Query query = em.createNativeQuery(sql, resultSetMapping);
        return query.getResultList();
    }

    @Override
    public List nativeQuery (String sql, Map<String,Object> param) {
        Query query = em.createNativeQuery(sql);
        setParameter(query, param);
        return query.getResultList();
    }

    @Override
    public List nativeQuery (String sql, List param) {
        Query query = em.createNativeQuery(sql);
        setParameter(query, param);
        return query.getResultList();
    }

    @Override
    public List nativeQuery (String sql, Class resultClass, Map<String,Object> param) {
        Query query = em.createNativeQuery(sql, resultClass);
        setParameter(query, param);
        return query.getResultList();
    }

    @Override
    public List nativeQuery (String sql, Class resultClass, List param) {
        Query query = em.createNativeQuery(sql, resultClass);
        setParameter(query, param);
        return query.getResultList();
    }

    @Override
    public List nativeQuery (String sql, String resultSetMapping, Map<String,Object> param) {
        Query query = em.createNativeQuery(sql, resultSetMapping);
        setParameter(query, param);
        return query.getResultList();
    }

    @Override
    public List nativeQuery (String sql, String resultSetMapping, List param) {
        Query query = em.createNativeQuery(sql, resultSetMapping);
        setParameter(query, param);
        return query.getResultList();
    }

    private void setParameter (Query query, Map param) {
        Set<Map.Entry<String, Object>> entries = param.entrySet();
        for (Map.Entry<String, Object> entry:entries)
            query.setParameter(entry.getKey(), entry.getValue());
    }

    private void setParameter (Query query, List param) {
        int size = param.size();
        for (int i=1;i<size;i++)
            query.setParameter(i, param.get(i-1));
    }

}
