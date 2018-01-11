package com.hang.aop.spring.section;

/**
 * @author ZhangHang
 * @create 2017-07-13 16:38
 **/
public class WaiterFlow {
    private Waiter waiter;
    public void service(String clientName){
        waiter.greetTo(clientName);
        waiter.serveTo(clientName);
    }
    public void setWaiter(Waiter waiter){
        this.waiter=waiter;
    }
}
