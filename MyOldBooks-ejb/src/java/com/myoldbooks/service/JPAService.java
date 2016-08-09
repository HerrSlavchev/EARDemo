/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author SRVR1
 */
public class JPAService<T> {

    private final Class<T> entityClass;

    @PersistenceContext(unitName = "MyOldBooks-ejbPU")
    private EntityManager em;

    public JPAService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    
    public T find(String namedQuery) {
        return find(namedQuery, new String[]{}, new Object[]{});
    }
    
    public T find(String namedQuery, String paramName, Object paramValue) {
        return find(namedQuery, new String[]{paramName}, new Object[]{paramValue});
    }
    
    public T find(String namedQuery, String[] paramNames, Object[] paramValues) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        for(int i = 0; i < paramNames.length; i++){
            q.setParameter(paramNames[i], paramValues[i]);
        }
        List res = q.getResultList();
        return res.isEmpty() ? null : (T) res.get(0);
    }
    
    public List<T> findAll(String namedQuery) {
        return findAll(namedQuery, new String[]{}, new Object[]{});
    }
    
    public List<T> findAll(String namedQuery, String paramName, Object paramValue) {
        return findAll(namedQuery, new String[]{paramName}, new Object[]{paramValue});
    }
    
     public List<T> findAll(String namedQuery, String[] paramNames, Object[] paramValues) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        for(int i = 0; i < paramNames.length; i++){
            q.setParameter(paramNames[i], paramValues[i]);
        }
        return (List<T>) q.getResultList();
    }

    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    
}
