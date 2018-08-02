package com.hang.cache.lrucache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ZhangHang
 * @create 2018-06-01 11:22
 **/
public class LRUMap<K, V> extends LinkedHashMap<K, V> {
    private static int DEFAULT_MAX_CAPACITY;

    //false:按照插入顺序排列  true；按照访问的顺序排列
    public LRUMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor, true);
        DEFAULT_MAX_CAPACITY = initialCapacity;
    }

    /**
     * 判断是否超出限制，超出则移除链表头，即最久没有访问的数据
     * @param eldest
     * @return
     */ v
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > DEFAULT_MAX_CAPACITY;
    }
}
