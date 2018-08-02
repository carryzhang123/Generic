package com.hang.proxy.dynamicproxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author ZhangHang
 * @create 2018-07-12 11:46
 **/

/**
 * Cglib子类代理工厂
 * 对UserDao在内存中动态构建一个子类对象
 */
public class ProxyFactory implements MethodInterceptor {
    //维护目标对象
    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    //给目标对象创建一个代理对象
    public Object getProxyInstance(){
        //1、工具类
        Enhancer en=new Enhancer();
        //2、设置父类
        en.setSuperclass(target.getClass());
        //3、设置回调函数
        en.setCallback(this);
        //4、创建子类（代理对象）
        return en.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("开始事物！");

        //执行目标对象的方法
        Object returnValue=method.invoke(target,args);

        System.out.println("提交事物！");
        return returnValue;
    }
}
