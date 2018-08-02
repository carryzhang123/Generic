package com.hang.ramcache.service;

import com.hang.ramcache.IEntity;

import java.io.Serializable;

/**
 * @author ZhangHang
 * @create 2018-01-30 16:09
 **/
public interface RegionEnhanceService<PK extends Comparable<PK> & Serializable, T extends IEntity<PK>> extends EnhanceService<PK, T> {
    /**
     * 修改索引值的方法
     *
     * @param name   索引属性名
     * @param entity 修改的实体
     * @param prev   之前的值
     */
    void changeIndexValue(String name, T entity, Object prev);
}
