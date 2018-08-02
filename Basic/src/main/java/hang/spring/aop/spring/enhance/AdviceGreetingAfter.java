package hang.spring.aop.spring.enhance;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @author ZhangHang
 * @create 2017-07-12 12:10
 **/
public class AdviceGreetingAfter implements AfterReturningAdvice {
    /**
     * 在目标类方法调用后执行
     */
    @Override
    public void afterReturning(Object retrunObj, Method method, Object[] args, Object obj) throws Throwable {
        System.out.println("Please enjoy yourself!");
    }
}
