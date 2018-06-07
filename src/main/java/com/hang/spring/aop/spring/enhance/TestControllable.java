package com.hang.spring.aop.spring.enhance;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author ZhangHang
 * @create 2017-07-13 18:21
 **/
public class TestControllable {
    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("/src/main/resources/spring/spring-aop-enhance.xml");
        NaiveWaiter naiveWaiter = (NaiveWaiter) ctx.getBean("waiter");

        //默认情况下，未开启性能监视功能
        naiveWaiter.greetTo("zhang");
        naiveWaiter.serveTo("hang");

        Monitorable monitorable = (Monitorable) naiveWaiter;
        monitorable.setMonitorActive(true);
        naiveWaiter.greetTo("zhang");
        naiveWaiter.greetTo("hang");
    }
}
