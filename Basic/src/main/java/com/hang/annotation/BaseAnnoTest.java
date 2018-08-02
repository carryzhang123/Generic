package com.hang.annotation;

/**
 * @author ZhangHang
 * @create 2018-01-25 17:45
 **/

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 读取注释
 */
public class BaseAnnoTest {
    public static void main(String[] args) {
        boolean isAnnotation = BaseAnno.class.isAnnotationPresent(MyTable.class);
        if (isAnnotation) {
            MyTable myTable = BaseAnno.class.getAnnotation(MyTable.class);
            //获取类的注解
            System.out.println("Class Annotation ：" + myTable.name() + "   " + myTable.version());
        }

        //获取字段、方法的注解
        Field[] fields = BaseAnno.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            MyField myField = field.getAnnotation(MyField.class);
            System.out.println("Field  Annotation ：" + myField.name() + "     " + myField.type());
        }

        //通过注解测试方法
        Method[] methods = BaseAnno.class.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(MyMethod.class)) {
                MyMethod myMethod = method.getAnnotation(MyMethod.class);
                System.out.println("Methon Annotation ：" + myMethod.name() + "   " + myMethod.effect());
                try {
                    method.invoke(new BaseAnno(), null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
