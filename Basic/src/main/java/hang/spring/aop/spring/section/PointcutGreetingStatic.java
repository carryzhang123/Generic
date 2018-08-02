package hang.spring.aop.spring.section;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

/**
 * @author ZhangHang
 * @create 2017-07-12 21:00
 * 静态普通方法名匹配切面
 **/
public class PointcutGreetingStatic extends StaticMethodMatcherPointcutAdvisor {
    @Override
    public boolean matches(Method method, Class clazz) {//切点方法匹配规则：方法名为greetTo
        return "greetTo".equals(method.getName());
    }
    public ClassFilter getClassFilter(){//切点类匹配规则：为waiter的类或子类
        return new ClassFilter() {
            @Override
            public boolean matches(Class clazz) {
                return Waiter.class.isAssignableFrom(clazz);
            }
        };
    }
}
