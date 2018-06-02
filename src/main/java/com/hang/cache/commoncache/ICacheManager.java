package com.hang.cache.commoncache;

import java.util.Map;
import java.util.Set;

/**
 * @author ZhangHang
 * @create 2018-05-31 18:03
 **/
public interface ICacheManager {
    /**
     * 存入缓存
     *
     * @param key
     * @param cache
     */
    void putCache(String key, EntityCache cache);

    /**
     * 存入缓存
     *
     * @param key
     * @param object
     * @param timeOut
     */
    void putCache(String key, Object object, long timeOut);

    /**
     * 获取缓存
     * @param key
     * @return
     */
    EntityCache getCacheByKey(String key);

    Object getCacheByDataKeys(String key);

    /**
     * 获取所有缓存
     * @return
     */
    Map<String,EntityCache> getCacheAll();

    /**
     * 判断是否在缓存中
     * @param key
     * @return
     */
    boolean isContains(String key);

    /**
     * 清楚所有缓存
     */
    void clearAll();

    /**
     * 清楚指定缓存
     * @param key
     */
    void clearByKey(String key);

    /**
     * 缓存是否超时失效
     * @param key
     * @return
     */
    boolean isTimeOut(String key);

    /**
     * 获取所有key
     * @return
     */
    Set<String> getAllKeys();
}