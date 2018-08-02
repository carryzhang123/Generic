package hang.ramcache.lock;

import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * @author ZhangHang
 * @create 2018-01-25 21:00
 **/
public class ChainLock {
    private final Lock current;
    private final ChainLock next;

    public ChainLock(List<? extends Lock> locks) throws IllegalAccessException {
        if(locks!=null&&locks.size()!=0){
            this.current=locks.remove(0);
            if(locks.size()>0){
                this.next=new ChainLock(locks);
            }else {
                this.next=null;
            }
        }else {
            throw new IllegalAccessException("构件锁链的锁数量不能为0");
        }
    }

    public void lock(){
        this.current.lock();
         if(this.next!=null){
             this.next.lock();
         }
    }

    public void unlock(){
        if(this.next!=null){
            this.next.unlock();
        }

        this.current.unlock();
    }
}
