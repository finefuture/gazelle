package org.gra4j.gazelle.modify;

import org.gra4j.gazelle.core.Jpa;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * * gazelle
 * org.gra4j.gazelle.modify
 *
 * @author tom.long
 */
public abstract class AbstractModify extends ModifyHeir implements SqlModify {

    private Jpa jpa;

    private EntityManager em;

    public AbstractModify(Jpa jpa) {
        super(jpa);
        this.jpa = jpa;
        this.em = jpa.em();
    }

    @Override
    @Transactional
    public int modify(String jpql) {
        Query query = em.createQuery(jpql);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int modify (String jpql, Map<String,Object> param) {
        Query query = em.createQuery(jpql);
        setParameter(query, param);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int modify (String jpql, List param) {
        Query query = em.createQuery(jpql);
        setParameter(query, param);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int namedModify(String name) {
        Query query = em.createNamedQuery(name);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int namedModify(String name, Map<String, Object> param) {
        Query query = em.createNamedQuery(name);
        setParameter(query, param);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int namedModify(String name, List param) {
        Query query = em.createNamedQuery(name);
        setParameter(query, param);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int nativeModify(String sql) {
        Query query = em.createNativeQuery(sql);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int nativeModify(String sql, Map<String, Object> param) {
        Query query = em.createNativeQuery(sql);
        setParameter(query, param);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int nativeModify(String sql, List param) {
        Query query = em.createNativeQuery(sql);
        setParameter(query, param);
        return query.executeUpdate();
    }

    private void setParameter (Query query, Map param) {
        Set<Map.Entry<String, Object>> entries = param.entrySet();
        for (Map.Entry<String, Object> entry:entries)
            query.setParameter(entry.getKey(), entry.getValue());
    }

    private void setParameter (Query query, List param) {
        int size = param.size();
        for (int i=0;i<size;i++)
            query.setParameter(i, param.get(i));
    }
}
