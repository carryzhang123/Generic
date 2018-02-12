package com.hang.ramcache.strategy;

import com.hang.tools.time.TimeUtils;

import java.util.*;

/**
 * @author ZhangHang
 * @create 2018-01-26 21:58
 **/
public class CacheManager {
    public static final int MAX_CACHE_NUM = 3;//最大3个缓存
    public static TreeMap<String, Cache> cacheMap = new TreeMap<>();
    public static List<Map.Entry<String, Cache>> list;

    /**
     * 从缓存中得到数据，如果没有数据，则从数据库中读取，并更新缓存的时间
     */
    public static Cache getCache(String id) {
        //若缓存中没有数据，则从数据库中查找，并加入缓存
        if (cacheMap.get(id) == null) {
            cacheMap.put(id, new Cache(id, new String("value" + id)));
        }

        Cache res = cacheMap.get(id);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        res.setTagDate(new Date());

        rankValue();

        return cacheMap.get(id);
    }

    /**
     * 传入Cache值，并更加缓存时间进行排序，若超过数量，则移除最后一个
     *
     * @param cache
     */
    public static void putCache(Cache cache) {
        try {
            Thread.sleep(1000);
            cache.setTagDate(new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cacheMap.put(cache.id, cache);
        rankValue();
        if (cacheMap.size() >= MAX_CACHE_NUM) {
            cacheMap.remove(cacheMap.firstKey());
        }
    }

    /**
     * 打印结果
     */
    public static void printValue() {
        for (Map.Entry<String, Cache> mapping : list) {
            System.out.println(mapping.getKey() + "---" + TimeUtils.formatTim(mapping.getValue().getTagDate()));
        }
    }

    /**
     * 根据缓存日期进行排序
     */
    public static void rankValue() {
        list = new ArrayList<>(cacheMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Cache>>() {
            @Override
            public int compare(Map.Entry<String, Cache> o1, Map.Entry<String, Cache> o2) {
                return (int) (o2.getValue().getTagDate().getTime() - o1.getValue().getTagDate().getTime());
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        Cache cache1 = new Cache("1", "value1");
        Cache cache2 = new Cache("2", "value2");
        Cache cache3 = new Cache("3", "value3");
        Cache cache4 = new Cache("4", "value4");
        Cache cache5 = new Cache("5", "value5");
        CacheManager.putCache(cache1);
        CacheManager.putCache(cache2);
        CacheManager.putCache(cache3);
        CacheManager.putCache(cache4);
        CacheManager.putCache(cache5);
        printValue();
//        com.hang.ramcache.strategy.CacheManager.getCache("5");
//        com.hang.ramcache.strategy.CacheManager.getCache("6");
//        com.hang.ramcache.strategy.CacheManager.getCache("6");
    }

    public static class Cache {
        String id;//相当于主键
        Object val;
        private Date tagDate;//标记日期，标定什么时候使用的


        public Cache(String id, Object val) {
            this.id = id;
            this.val = val;
            this.setTagDate(new Date());
        }

        public void setValue(Object val) {
            this.val = val;
            this.setTagDate(new Date());
        }

        public void showInfo() {
            System.out.println("Cache的ID是:   " + id + "   Cache的值是:   " + val);
        }

        public void setTagDate(Date dt) {
            this.tagDate = dt;
        }

        public Date getTagDate() {
            return tagDate;
        }
    }

}


