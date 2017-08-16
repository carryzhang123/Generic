package com.sanqi.compare; /**
 * @author ZhangHang
 * @create 2017-08-01 20:59
 * 对TreeSet进行排序，只需要里面集合的compareTo()方法即可
 */

import java.util.Iterator;
import java.util.TreeSet;

class CompareSet {
    public static void main(String[] args) {
        TreeSet<User> treeSet=new TreeSet<User>();
        User user1 = new User(5, "zhanghang");
        User user2 = new User(23, "zhanghang");
        User user3 = new User(2, "zhanghang");
        treeSet.add(user1);
        treeSet.add(user2);
        treeSet.add(user3);
        Iterator<User> it=treeSet.iterator();
        while (it.hasNext()){
            User user=it.next();
            System.out.println(user.getId()+"---------"+user.getName());
        }
    }
}
