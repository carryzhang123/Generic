package com.hang.proxy.dynamicproxy.jdkself;

import com.hang.proxy.staticproxy.IUserDao;
import com.hang.proxy.staticproxy.UserDao;

/**
 * @author ZhangHang
 * @create 2018-07-12 11:28
 **/
public class App {
    public static void main(String[] args) {
        //目标对象
        IUserDao target=new UserDao();

        //给目标对象，创建代理对象
        IUserDao proxy= (IUserDao) new ProxyFactory(target).getProxyInstance();
        //执行方法
        proxy.save();
    }
}
