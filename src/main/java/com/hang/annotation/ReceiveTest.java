package com.hang.annotation;

import com.hang.thread.tiantask.EventBusManager;
import com.hang.thread.tiantask.RunnableKey;
import com.hang.thread.tiantask.TianEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ZhangHang
 * @create 2018-05-02 22:17
 **/
public class ReceiveTest {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        TianEvent event = TianEvent.valueOf(RunnableKey.FIRST.threadId(), new Runnable() {
            @Override
            public void run() {
                System.out.println("This is a about annotation and event receive test!" + Thread.currentThread().getId());
            }
        });

        Method[] methods = ReceiveMethod.class.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(ReceiveEvent.class)) {
                method.invoke(new ReceiveMethod(), event);
            }
        }
    }
}
