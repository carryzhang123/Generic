package com.sanqi.aop.spring.enhance;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author ZhangHang
 * @create 2017-07-12 12:27
 **/
public class AdviceGreetingSurround implements MethodInterceptor {

    /**
     * 获取目标类方法的执行，并在前后添加横切逻辑
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] args = invocation.getArguments();//目标方法入参
        String clientName = (String) args[0];
        System.out.println("How are you! Mr." + clientName + ".");
        Object obj = invocation.proceed();//通过反射机制调用目标方法
        System.out.println("Please enjoy yourself!");
        return obj;
    }
}
