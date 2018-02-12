package com.hang.ramcache.service;

import com.hang.ramcache.IEntity;
import com.hang.ramcache.anno.CachedEntityConfig;

import java.io.Serializable;
import java.util.Collection;

/**
 * 区域缓存服务接口
 *
 * @param <PK>
 * @param <T>
 */
public interface RegionCacheService<PK extends Comparable<PK> & Serializable, T extends IEntity<PK>> {
    /**
     * 加载指定区域的实体集合
     *
     * @param idx 索引值
     * @return 不可修改的列表
     */
    Collection<T> load(IndexValue idx);

    /**
     * 获取某一组件的实体
     *
     * @param idx
     * @param id
     * @return
     */
    T get(IndexValue idx, PK id);

    /**
     * 创建新的实体
     *
     * @param entity
     * @return
     */
    T create(T entity);

    /**
     * 移除指定实体（异步）
     *
     * @param entity
     */
    void remove(T entity);

    /**
     * 清理指定的区域缓存
     *
     * @param idx
     */
    void clear(IndexValue idx);

    /**
     * 将缓存中的指定实体回写到储存层（异步）
     *
     * @param id
     * @param entity
     */
    void writeBack(PK id, T entity);

    /**
     * 获取实体缓存配置信息
     *
     * @return
     */
    CachedEntityConfig getEntityConfig();

    /**
     * 获取对应的持久化处理器
     *
     * @return
     */
    com.hang.ramcache.persist.Persister getPersister();
}
