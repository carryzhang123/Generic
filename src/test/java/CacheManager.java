import com.hang.tools.time.TimeUtils;

import java.util.*;

/**
 * @author ZhangHang
 * @create 2018-01-26 21:58
 **/
public class CacheManager {
    public static final int MAX_CACHE_NUM = 5;//最大五个缓存
    public static TreeMap<String, Cache> cacheMap = new TreeMap<String, Cache>();

    public static void sortMap() {
        List<Map.Entry<String, Cache>> list = new ArrayList<>();
        list.addAll(cacheMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Cache>>() {
            @Override
            public int compare(Map.Entry<String, Cache> o1, Map.Entry<String, Cache> o2) {
                return (int) (o1.getValue().getTagDate().getTime() - o2.getValue().getTagDate().getTime());
            }
        });
    }

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
        sortMap();
        return cacheMap.get(id);
    }


    public static void putCache(Cache cache) {
        try {
            Thread.sleep(1000);
            cache.setTagDate(new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cacheMap.put(cache.id, cache);
        sortMap();
        if (cacheMap.size() > MAX_CACHE_NUM) {
            cacheMap.remove(cacheMap.lastKey());
        }
    }

//    public static void refreshCaches() {
//        System.out.println("---刷新缓存---");
//
//        List<Map.Entry<String, Cache>> list = new ArrayList();
//        list.addAll(cacheMap.entrySet());
//        Collections.sort(list, new Comparator<Map.Entry<String, Cache>>() {
//            @Override
//            public int compare(Map.Entry<String, Cache> o1, Map.Entry<String, Cache> o2) {
//                return (int) (o1.getValue().getTagDate().getTime() - o2.getValue().getTagDate().getTime());
//            }
//        });
//
//        for (Iterator<Map.Entry<String, Cache>> itea = list.iterator(); itea.hasNext(); ) {
//            System.out.println(itea.next().getValue().id);
//        }
//
//        for (int i = MAX_CACHE_NUM; i < list.size(); i++) {
//            String id = (String) list.get(i).getKey();
//            Object val = getFromDB(id);
//            cacheMap.put(id, new Cache(id, val));
//        }
//    }

    public static void outputMap() {
        for (Map.Entry<String, Cache> cacheMap : cacheMap.entrySet()) {
            System.out.println(cacheMap.getKey() + "---" + cacheMap.getValue().id + "---" + cacheMap.getValue().val + "---" + TimeUtils.formatTim(cacheMap.getValue().tagDate)
            );
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Cache cache1 = new Cache("1", "value1");
        Cache cache2 = new Cache("2", "value2");
        Cache cache3 = new Cache("3", "value3");
        Cache cache4 = new Cache("4", "value4");
        Cache cache5 = new Cache("5", "value5");
        Cache cache6 = new Cache("6", "value6");
        Cache cache7 = new Cache("7", "value7");
        CacheManager.putCache(cache1);
        CacheManager.putCache(cache2);
        CacheManager.putCache(cache3);
        CacheManager.putCache(cache4);
        CacheManager.putCache(cache5);
        CacheManager.putCache(cache6);
        CacheManager.putCache(cache7);
        outputMap();
//        CacheManager.getCache("5");
//        CacheManager.getCache("6");
//        CacheManager.getCache("6");

//        Thread refreshTD = new Thread() {
//            public void run() {
//                while (true) {
//                    refreshCaches();
//                    try {
//                        Thread.sleep(1000);//每一秒刷新一次
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        //      refreshTD.start();
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


