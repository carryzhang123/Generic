package com.hang.spring.sanqilaod.reflect;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author ZhangHang
 * @create 2018-06-04 18:37
 **/
public class Storge<K, V> {
    private HashMap<K, V> hashMap = new HashMap<>();

    public V getById(K k) {
        return hashMap.get(k);
    }

    public Collection<V> getAll() {
        return hashMap.values();
    }

    public void put(K k, V v) {
        hashMap.put(k, v);
    }
}
