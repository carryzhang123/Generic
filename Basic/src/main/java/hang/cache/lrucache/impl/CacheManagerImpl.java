package hang.cache.lrucache.impl;

import com.hang.cache.lrucache.EntityCache;
import com.hang.cache.lrucache.ICacheManager;
import com.hang.cache.lrucache.LRUMap;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ZhangHang
 * @create 2018-05-31 18:13
 **/
public class CacheManagerImpl implements ICacheManager {
    private static LRUMap<String, EntityCache> caches;
    private static ReadWriteLock lock = new ReentrantReadWriteLock(false);
    private static Lock readLock = lock.readLock();
    private static Lock writeLock = lock.writeLock();

    public CacheManagerImpl(int initialCapacity) {
        caches = new LRUMap<>(initialCapacity, 0.75f);
    }

    @Override
    public void putCache(String key, EntityCache cache) {
        try {
            writeLock.lock();
            caches.put(key, cache);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void putCache(String key, Object datas) {
        try {
            writeLock.lock();
            putCache(key, new EntityCache(datas));
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public EntityCache getCacheByKey(String key) {
        try {
            readLock.lock();
            if (caches.containsKey(key)) {
                return caches.get(key);
            }
            return null;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Object getCacheByDataKeys(String key) {
        try {
            readLock.lock();
            if (caches.containsKey(key)) {
                return caches.get(key).getDatas();
            }
            return null;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Map<String, EntityCache> getCacheAll() {
        try {
            readLock.lock();
            return caches;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void clearAll() {
        try {
            caches.clear();
            writeLock.lock();
        } finally {
            writeLock.unlock();
        }
    }
}
