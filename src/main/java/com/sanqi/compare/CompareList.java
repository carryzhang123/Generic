package com.sanqi.compare;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author ZhangHang
 * @create 2017-08-14 12:24
 * 对List集合进行排序，需要使用Collections.sort()方法
 **/
public class CompareList {
    public static void main(String[] args) {
        ArrayList<User> list = new ArrayList<User>();
        User user1 = new User(5, "zhanghang");
        User user2 = new User(23, "zhanghang");
        User user3 = new User(2, "zhanghang");
        list.add(user1);
        list.add(user2);
        list.add(user3);
        Collections.sort(list);
        for(User user:list){
            System.out.println(user.getId()+"--------"+user.getName());
        }
    }
}
