package com.sanqi.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author ZhangHang
 * @create 2017-07-08 15:52
 * 在访问private或protected成员变量和方法时，必须通过setAccessible(boolean access)方法取消Java语言检查
 **/
public class PrivateCarReflect {
    public static void main(String[] args) throws Throwable{
        ClassLoader loader=Thread.currentThread().getContextClassLoader();
        Class clazz=loader.loadClass("com.sanqi.reflect.PrivateCar");
        PrivateCar pcar= (PrivateCar) clazz.newInstance();
        Field colorFld=clazz.getDeclaredField("color");

        //取消Java语言访问检查以便访问private变量
        colorFld.setAccessible(true);
        colorFld.set(pcar,"红色");

        //第一个参数：方法名   第二个参数：方法中参数类型的集合
        Method driveMtd=clazz.getDeclaredMethod("drive",null);

        //取消Java语言访问检查以便访问protected方法
        driveMtd.setAccessible(true);
        //执行pcar对象中带null参数的driveMtd方法
        driveMtd.invoke(pcar,null);
    }
}
