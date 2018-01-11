package com.hang.aop.common;

/**
 * @author ZhangHang
 * @create 2017-07-10 16:43
 **/
public class ForumService implements Forum{
    public void removeTopic(int topicId){
        //开始对该方法进行性能监视
//        PerformanceMonitor.begin("com.sanqi.aop.commonaop.ForumService.removeTopic");
        System.out.println("模拟删除Topic记录："+topicId);
        try {
            Thread.sleep(20);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        //结束对该方法的性能监视
//        PerformanceMonitor.end();
    }

    public void removeForum(int forumId){
        //开始对该方法进行性能监视
//        PerformanceMonitor.begin("com.sanqi.aop.commonaop.ForumService.removeForum");
        System.out.println("模拟删除Forum记录："+forumId);
        try {
            Thread.sleep(30);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        //结束对该方法的性能监视
//        PerformanceMonitor.end();
    }
}
