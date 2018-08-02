package hang.spring.aop.spring.enhance;

import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author ZhangHang
 * @create 2017-07-11 21:20
 **/
public class TestAdvice {
    public static void main(String[] args) {

    }

    public static void BeforeProFactory() {
        Waiter target = new NaiveWaiter();
        BeforeAdvice advice = new AdviceGreetingBefore();

        //Spring提供的代理工厂
        ProxyFactory pf = new ProxyFactory();

        pf.setInterfaces(target.getClass().getInterfaces());//指定对接口进行代理
        pf.setOptimize(true);//启动优化，则针对接口的代理也会使用Cglib2AopProxy

        //设置代理目标
        pf.setTarget(target);

        //为代理目标添加增强
        pf.addAdvice(advice);

//        pf.addAdvice(0,advice);  //将增强添加到增强链的具体位置

        //生成代理实例
        Waiter proxy = (Waiter) pf.getProxy();
        proxy.greetTo("John");
        proxy.serveTo("Tom");
    }

    public static void TranscationProxyFactoryBean() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("/src/main/resources/spring/spring-aop-enhance.xml");
//        Waiter waiter = (Waiter) ctx.getBean("waiter");
//        waiter.greetTo("John");

        NaiveWaiterTwo waiterTwo = (NaiveWaiterTwo) ctx.getBean("waiter");
        try {
            waiterTwo.greetTo();
            waiterTwo.serveTo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
