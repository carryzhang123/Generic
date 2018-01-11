package com.hang.aop.common;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author ZhangHang
 * @create 2017-07-11 15:27
 * 通过jdk自带方法进行横向代码的织入
 **/
public class PerformanceHandler implements InvocationHandler {
    private Object target;
    public PerformanceHandler(Object target){//target为目标业务类
        this.target=target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        PerformanceMonitor.begin(target.getClass().getName()+"."+method.getName());
        Object obj=method.invoke(target,args);//通过反射方法调用业务类的目标方法
        PerformanceMonitor.end();
        return obj;
    }
}
