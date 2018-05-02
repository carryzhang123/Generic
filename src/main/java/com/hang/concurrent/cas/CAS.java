package com.hang.concurrent.cas;

/**
 * @author ZhangHang
 * @create 2018-05-02 16:25
 **/

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS采用的是乐观锁，即假设当前状态不冲突情况下去执行，如果冲突，则一直尝试，直至不冲突为止
 * CAS传入三个值，旧值 现在值 更改值，只有旧值和现在值相等时，才会将更改值赋值于现在值
 * concurrent下的锁类和atomic类都是采用CAS和violate原理
 * 缺点：ABA，即将值改为B，在改为A，则CAS会默认没有更改，还有就是CAS不断的尝试，可能会大量消耗CPU
 */
public class CAS {
    private volatile int i;

    public CAS(int i) {
        this.i = i;
    }

    //获取最新值
    public int get() {
        return i;
    }

    //更新最新的值
    public boolean update(int expect,int update) {
        if(compareSafe(expect)){
            i = update;
            return true;
        }else {
            System.out.println("current is not equal expect! please try again!");
            return false;
        }
    }

    //将expect与现值进行比较
    private boolean compareSafe(int expect) {
        return expect == get() ? true : false;
    }
}
