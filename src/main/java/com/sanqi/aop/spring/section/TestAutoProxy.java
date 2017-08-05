package com.sanqi.aop.spring.section;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author ZhangHang
 * @create 2017-07-14 11:13
 **/
public class TestAutoProxy {
    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("/src/main/resources/spring/spring-aop-section.xml");
        Waiter waiter= (Waiter) ctx.getBean("waiterTarget");
        Seller seller= (Seller) ctx.getBean("sellerTarget");
        waiter.greetTo("John");
        waiter.greetTo("Tom");
    }
}
