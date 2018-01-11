package com.hang.aop.spring.enhance;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author ZhangHang
 * @create 2017-07-11 21:11
 * Spring的前置增强
 **/
public class AdviceGreetingBefore implements MethodBeforeAdvice {
    /**
     * 在目标类方法调用前执行
     */
    @Override
    public void before(Method method, Object[] args, Object obj) throws Throwable {
        String clientName = (String) args[0];
        System.out.println("How are you! Mr." + clientName + ".");
    }

}
