package com.hang.compare;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author ZhangHang
 * @create 2017-08-14 11:47
 **/
public class CompareMap {
    public static void main(String[] args) {
        CompareMap compareMap = new CompareMap();
        compareMap.sortByKey();
    }

    public void sortByKey() {
        /**
         * TreeMap对key进行排序，其默认排序是升序
         */
        TreeMap<Integer, User> treeMap = new TreeMap<Integer, User>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 == o2) {
                    return 0;
                } else if (o1 < o2) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        User user = new User(1, "zhanghang");
        treeMap.put(2, user);
        treeMap.put(34, user);
        treeMap.put(48, user);
        Iterator it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            System.out.println("sortByKey----" + entry.getKey() + "----" +entry.getValue());
        }
        System.out.println("--------------------------------------");
    }
}
