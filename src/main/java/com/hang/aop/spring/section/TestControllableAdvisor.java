package com.hang.aop.spring.section;

import com.hang.aop.spring.enhance.Monitorable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author ZhangHang
 * @create 2017-07-13 18:21
 **/
public class TestControllableAdvisor {
    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("/src/main/resources/spring/spring-aop-section.xml");
        Waiter waiter = (Waiter) ctx.getBean("waiter");

        //默认情况下，未开启性能监视功能
        waiter.greetTo("zhang");
        waiter.serveTo("hang");

        Monitorable monitorable = (Monitorable) waiter;
        monitorable.setMonitorActive(true);
        waiter.greetTo("zhang");
        waiter.greetTo("hang");
    }
}
