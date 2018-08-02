package hang.cache.commoncache;

import com.hang.tools.time.TimeUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZhangHang
 * @create 2018-05-31 18:13
 **/
public class CacheManagerImpl implements ICacheManager {
    private static Map<String, EntityCache> caches = new ConcurrentHashMap<>();

    @Override
    public void putCache(String key, EntityCache cache) {
        caches.put(key, cache);
    }

    @Override
    public void putCache(String key, Object datas, long timeOut) {
        timeOut = timeOut > 0 ? timeOut : 0L;
        putCache(key, new EntityCache(datas, timeOut, System.currentTimeMillis()));
    }

    @Override
    public EntityCache getCacheByKey(String key) {
        if (this.isContains(key)) {
            return caches.get(key);
        }
        return null;
    }

    @Override
    public Object getCacheByDataKeys(String key) {
        if (this.isContains(key)) {
            return caches.get(key).getDatas();
        }
        return null;
    }

    @Override
    public Map<String, EntityCache> getCacheAll() {
        return caches;
    }

    @Override
    public boolean isContains(String key) {
        return caches.containsKey(key);
    }

    @Override
    public void clearAll() {
        caches.clear();
    }

    @Override
    public void clearByKey(String key) {
        if (this.isContains(key)) {
            caches.remove(key);
        }
    }

    @Override
    public boolean isTimeOut(String key) {
        if (!this.isContains(key)) {
            return true;
        }
        EntityCache cache = caches.get(key);

        long timeOut = cache.getTimeOut();
        long lastRefreshTime = cache.getLastRefreshTime();

        return timeOut == 0 || TimeUtils.getNow() - lastRefreshTime >= timeOut;
    }

    @Override
    public Set<String> getAllKeys() {
        return caches.keySet();
    }
}
