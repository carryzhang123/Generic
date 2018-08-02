package com.hang.proxy.dynamicproxy.cglib;

/**
 * @author ZhangHang
 * @create 2018-07-12 11:44
 **/

/**
 * 目标对象，没有实现任何接口
 */
public class UserDao {
    public void save(){
        System.out.println("已经保存数据！");
    }
}
