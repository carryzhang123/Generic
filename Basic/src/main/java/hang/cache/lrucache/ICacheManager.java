package hang.cache.lrucache;

import java.util.Map;

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
     */
    void putCache(String key, Object object);

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
     * 清楚所有缓存
     */
    void clearAll();

}