package com.sanqi.aop.spring.enhance;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * @author ZhangHang
 * @create 2017-07-12 14:53
 * 异常抛出增强
 **/
public class AdviceGreetingTransaction implements ThrowsAdvice {
    /**
     * 定义增强逻辑
     * ThrowAdvice没有定义任何方法，在允许期间Spring使用反射机制自行判断，须采用以下签名形式定义
     * void afterThrowing(Method method,Object[] args,Object target,Exception ex) throws Throwable
     * 方法名必须afterThrowing，前三个要么都入，要么都没，最后一个是Throwable或其子类
     */
    public void afterThrowing(Method method,Object[] args,Object target,Throwable ex) throws Throwable{
        System.out.println("----------");
        System.out.println("method:"+method.getName());
        System.out.println("抛出异常："+ex.getMessage());
        System.out.println("成功回滚事务");
    }
}
