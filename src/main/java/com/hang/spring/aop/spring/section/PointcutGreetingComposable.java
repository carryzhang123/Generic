package com.hang.spring.aop.spring.section;

import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.NameMatchMethodPointcut;

/**
 * @author ZhangHang
 * @create 2017-07-13 17:08
 * 复合切面
 **/
public class PointcutGreetingComposable {
    public Pointcut getIntersectionPointcut(){
        ComposablePointcut cp=new ComposablePointcut();//创建一个复合切点
        Pointcut pt1=new ControlFlowPointcut(WaiterFlow.class,"service");//创建一个流程切点
        NameMatchMethodPointcut pt2=new NameMatchMethodPointcut();//创建一个方法名切点
        pt2.addMethodName("greetTo");
        return cp.intersection(pt1).intersection((MethodMatcher) pt2);//将两个切点进行交集操作
    }
}
