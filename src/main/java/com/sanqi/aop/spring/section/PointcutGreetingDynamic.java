package com.sanqi.aop.spring.section;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangHang
 * @create 2017-07-13 14:38
 * 动态切点
 **/
public class PointcutGreetingDynamic extends DynamicMethodMatcherPointcut {
    private static List<String> spcialClientList=new ArrayList<String>();
    static {
        spcialClientList.add("John");
        spcialClientList.add("Tom");
    }

    public ClassFilter getClassFilter(){//对类进行静态切点检查
        return new ClassFilter() {
            @Override
            public boolean matches(Class<?> aClass) {
                System.out.println("调用getClassFilter()对"+aClass.getName()+"类做静态检查.");
                return Waiter.class.isAssignableFrom(aClass);
            }
        };
    }

    public boolean matches(Method method,Class clazz){//对方法进行静态切点检查
        System.out.println("调用matches(method,clazz)"+clazz.getName()+"."+method.getName()+"方法做静态检查");
        return "greetTo".equals(method.getName());
    }

    @Override
    public boolean matches(Method method, Class clazz, Object[] args) {//对方法进行动态切点检查
        System.out.println("调用matches(method,clazz)"+clazz.getName()+"."+method.getName()+"方法做动态检查.");
        String clientName= (String) args[0];
        return spcialClientList.contains(clientName);
    }
}
