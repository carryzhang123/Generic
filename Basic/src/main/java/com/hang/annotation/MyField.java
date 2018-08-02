package com.hang.annotation;

/**
 * @author ZhangHang
 * @create 2018-01-25 17:39
 **/

import java.lang.annotation.*;

/**
 * 字段注释
 * @Retention、@Documented、@Target、@Inherited、@Repeatable 均属于元注解，可以对其它自定义注解进行注解
 * @Inherited是继承，@Inherited->@MyField->BaseLog->BaseLog的子类
 * @Deprecated注解标示过时的元素
 * @SuppressWarnings("deprecated")忽视过时方法引起的警告
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface MyField {
    public String name() default "";     //名称
    public String type() default "";    //类型
}
