package hang.spring.aop.common;

import java.lang.reflect.Proxy;

/**
 * @author ZhangHang
 * @create 2017-07-11 15:41
 **/
public class ForumServiceTest {
    public static void main(String[] args) {
        proxyJdk();//jdk方式
        proxyCGLib();//CGLib方式
    }

    public static void proxyJdk() {
        Forum target = new ForumService();//希望被代理的目标业务类
        PerformanceHandler handler = new PerformanceHandler(target);//将目标业务类和横切代码编织到一起
        //根据编织了目标业务类逻辑和性能监视横切逻辑的InvocationHandler实例创建代理实例
        final Forum proxy = (Forum) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);

        proxy.removeForum(10);
        new Thread() {
            @Override
            public void run() {
                proxy.removeTopic(1012);
            }
        }.start();
    }

    public static void proxyCGLib(){
        CglibProxy proxy=new CglibProxy();
        final ForumService forumService= (ForumService) proxy.getProxy(ForumService.class);
        forumService.removeTopic(10);
        new Thread(){
            @Override
            public void run() {
                forumService.removeForum(1023);
            }
        }.start();
    }
}
