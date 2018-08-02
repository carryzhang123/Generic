package com.hang.spring.aop.common;

/**
 * @author ZhangHang
 * @create 2017-07-10 21:17
 **/
public class TestForumService {
    public static void main(String[] args) throws InterruptedException {
        final ForumService forumServiceImpl = new ForumService();
        forumServiceImpl.removeForum(10);
       new Thread() {
            @Override
            public void run() {
                forumServiceImpl.removeTopic(1012);
            }
        }.start();
    }
}
