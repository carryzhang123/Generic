package hang.spring.aop.spring.section;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author ZhangHang
 * @create 2017-07-12 21:06
 **/
public class AdviceGreetingBefore implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object obj) throws Throwable {
        System.out.println(obj.getClass().getName()+"."+method.getName()+"---"+(String)args[0]+"---前置增强");
    }
}
