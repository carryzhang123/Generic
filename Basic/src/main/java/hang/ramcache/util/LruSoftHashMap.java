package hang.ramcache.util;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZhangHang
 * @create 2018-01-26 21:41
 **/
public class LruSoftHashMap<K, V> {
    private Map<K, Reference<V>> cacheMap = new ConcurrentHashMap<K, Reference<V>>();

    public LruSoftHashMap(Map<K, V> map) {

    }

    public int getCacheMapSize() {
        return this.cacheMap.size();
    }

    public V get(K key) {
        Reference<V> ref = this.cacheMap.get(key);
        V result = null;
        if (ref != null) {
            result = ref.get();
            if (result == null) {
                this.cacheMap.remove(key);
            }
        }
        return result;
    }

    public void put(K key, V value) {
        Reference<V> entry = new LruSoftHashMap.Entry(key, value);
        this.cacheMap.put(key, entry);
    }

    public V remove(K key) {
        V result = null;
        if (result == null) {
            Reference<V> ref = this.cacheMap.remove(key);
            if (ref != null) {
                result = ref.get();
            }
        }
        return result;
    }

    public Collection<V> values() {
        Collection<V> result = new ArrayList<V>();
        Iterator i$ = this.cacheMap.entrySet().iterator();

        while (i$.hasNext()) {
            Map.Entry<K, Reference<V>> entry = (Map.Entry<K, Reference<V>>) i$.next();
            V temp = (V) ((Reference) entry.getValue()).get();
            if (result != null) {
                result.add(temp);
            } else {
                this.cacheMap.remove(entry.getKey());
            }
        }
        return result;
    }

    private class Entry extends SoftReference<V> {
        private K key;

        Entry(K key, V referent) {
            super(referent);
            this.key = (K) key;
        }

        K getKey() {
            return this.key;
        }
    }
}
