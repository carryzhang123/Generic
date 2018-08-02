package com.hang.proxy.dynamicproxy.cglib;

/**
 * @author ZhangHang
 * @create 2018-07-12 11:57
 **/
public class App {
    public static void main(String[] args) {
        //目标对象
        UserDao target=new UserDao();

        //代理对象
        UserDao proxy= (UserDao) new ProxyFactory(target).getProxyInstance();

        //执行代理对象的方法
        proxy.save();
    }
}
