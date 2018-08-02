package com.hang.cache.lrucache;

import com.hang.cache.lrucache.impl.CacheManagerImpl;

import java.util.Map;
import java.util.logging.Logger;

/**
 * @author ZhangHang
 * @create 2018-05-31 18:40
 **/
public class TestCache {
    static Logger logger = Logger.getLogger("cachelog");

    public static void main(String[] args) {
        ICacheManager cacheManager = new CacheManagerImpl(6);
        cacheManager.putCache("1",1);
        cacheManager.putCache("2",2);
        cacheManager.putCache("3",3);
        cacheManager.putCache("4",4);
        cacheManager.putCache("5",5);
        cacheManager.putCache("6",6);
        cacheManager.getCacheByDataKeys("3");
        cacheManager.putCache("7",7);
        cacheManager.putCache("8",8);
        cacheManager.putCache("9",9);
        cacheManager.putCache("10",10);


     for(Map.Entry<String,EntityCache> entry:cacheManager.getCacheAll().entrySet()){
         System.out.print(entry.getValue().getDatas()+",");
     }
    }
}
