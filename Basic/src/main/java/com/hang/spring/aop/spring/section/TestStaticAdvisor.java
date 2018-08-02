package com.hang.spring.aop.spring.section;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author ZhangHang
 * @create 2017-07-12 21:25
 **/
public class TestStaticAdvisor {
    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("/src/main/resources/spring/spring-aop-section.xml");
        Waiter waiter= (Waiter) ctx.getBean("waiter");
        Seller seller= (Seller) ctx.getBean("seller");
        waiter.greetTo("John");
        waiter.serveTo("John");
//        seller.greetTo("John");
    }
}
