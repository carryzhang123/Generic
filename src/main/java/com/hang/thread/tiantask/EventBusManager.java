package com.hang.thread.tiantask;

/**
 * @author ZhangHang
 * @create 2018-05-02 21:29
 **/
public class EventBusManager {
    public static void submit(TianEvent event) {
        Actor actor = RunnableKey.getActor(event.getThreadId());
        actor.dispatch(event.getRunnable());
    }
}
