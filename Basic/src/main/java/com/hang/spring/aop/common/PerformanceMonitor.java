package com.hang.spring.aop.common;

/**
 * @author ZhangHang
 * @create 2017-07-10 16:44
 **/
@SuppressWarnings("ALL")
public class PerformanceMonitor {
    //通过一个ThreadLocal保存与调用线程相关的性能监视信息
    @SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
    private static ThreadLocal<MethodPerformance> performanceRecord = new ThreadLocal<MethodPerformance>();

    //启动对某一目标方法的性能监视
    @SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
    public static void begin(String method) {
        System.out.println("begin monitor...");
        System.out.println("Thread  ID："+Thread.currentThread().getId());
        MethodPerformance mp = new MethodPerformance(method);
        performanceRecord.set(mp);
    }

    public static void end() {
        System.out.println("end monitor...");
        MethodPerformance mp = performanceRecord.get();
        //打印出方法性能监视的结果信息
        mp.printPerformance();
    }
}
