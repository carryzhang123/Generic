package com.hang.annotation;

/**
 * @author ZhangHang
 * @create 2018-01-25 17:39
 **/

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段注释
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyField {
    public String name() default "";     //名称
    public String type() default "";    //类型

}
