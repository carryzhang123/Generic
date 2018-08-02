package hang.spring.aop.spring.section;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author ZhangHang
 * @create 2017-07-13 16:52
 **/
public class TestFlowAdvisor {
    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("/src/main/resources/spring/spring-aop-section.xml");
        Waiter waiter= (Waiter) ctx.getBean("waiter");
        WaiterFlow wd=new WaiterFlow();
        wd.setWaiter(waiter);
        waiter.serveTo("Peter");
        waiter.greetTo("Peter");
        wd.service("Peter");
    }
}
