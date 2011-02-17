/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.persist;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * JpaDaoService with Generic Support,
 * No need to write specified DAO Service for each Entity.
 * @author x-spirit
 */
public interface IJpaDaoService {

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    boolean containsEntity(Object entity);

    <T extends Object> void create(T entity);

    <T extends Object> void destroy(Class<T> t, Integer id);

    <T extends Object> void destroy(Class<T> t, String id);

    <T extends Object> T edit(T entity);

    int executeUpdate(final String jpql, final Map<String, ? extends Object> params);

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    List findByNamedQueryAndNamedParams(String queryName, Map<String, ? extends Object> params);

    @Deprecated
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    List findByNamedQueryAndNamedParams(final String queryName, final Map<String, ? extends Object> params, final boolean all, final int firstResult, final int maxResults);

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    List findEntities(final String jpql, final Map<String, ? extends Object> params, final boolean all, final int firstResult, final int maxResults);

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    <T extends Object> T findOneEntityById(Class<T> t, Integer id);

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    <T extends Object> T findOneEntityById(Class<T> t, String id);

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    BigDecimal getEntityCount(final String jpql, final Map<String, ? extends Object> params);

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    Object getSingleResult(final String jpql, final Map<String, ? extends Object> params);

    @Deprecated
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    BigDecimal getNamedQueryEntityCount(final String queryName, final Map<String, ? extends Object> params);

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    <T extends Object> List executeNativeQuery(final Class<T> t,final String sql,final Map<Integer,? extends Object> params,final boolean all, final int firstResult, final int maxResults);

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    BigDecimal getCountByNativeQuery(final String sql,final Map<Integer,? extends Object> params);

    int executeNativeUpdate(final String sql,final Map<Integer,? extends Object> params);

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    Object getNativeSingleResult(final String sql,final Map<Integer,? extends Object> params);
}
