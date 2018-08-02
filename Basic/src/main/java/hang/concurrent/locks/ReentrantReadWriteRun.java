package hang.concurrent.locks;

/**
 * @author ZhangHang
 * @create 2018-05-03 21:05
 **/

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 1、读锁共享，写锁独占
 * 2、读锁请求成功：没有线程拥有写锁(writes==0) -> 没有线程请求写锁(writeRequests==0)，请求成功
 * 3、写锁请求成功：writeRequest++ -> 没有线程拥有写锁(writes==0) -> 没有线程拥有读锁(readers==0)，请求成功
 * 4、读锁重入：满足2中的条件或者本线程已经拥有读锁
 * 5、写锁重入：满足3中的条件或者本线程已经拥有写锁
 * 6、读锁升到写锁：本线程已经拥有读锁想获取写锁 -> 判断本线程读锁是否唯一
 * 7、写锁降到读锁：本线程已经拥有写锁想获取读锁 -> 由于写锁唯一，满足前提就可以自然获得读锁
 */
public class ReentrantReadWriteRun {
    public static Map<Integer, Integer> data = new HashMap<Integer, Integer>();
    //非公平锁
    private static ReadWriteLock lock = new ReentrantReadWriteLock(false);
    private static Lock rlock = lock.readLock();
    private static Lock wlock = lock.writeLock();

    /**
     * 读锁，共享，写锁情况下可获取
     *
     * @param key
     * @return
     */
    public static Integer get(Integer key) {
        rlock.lock();
        try {
            return data.get(key);
        } finally {
            rlock.unlock();
        }
    }

    /**
     * 写锁，独占，读锁情况下不能获取
     *
     * @param key
     * @param value
     * @return
     */
    public static Integer put(Integer key, Integer value) {
        wlock.lock();
        try {
            return data.put(key, value);
        } finally {
            wlock.unlock();
        }
    }


    public class Thread1 implements Runnable {
        private int key;
        private int value;

        public Thread1(int key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void run() {
            put(key, value);
        }
    }

    public class Thread2 implements Runnable {
        private int key;
        private int value;

        public Thread2(int key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void run() {
            System.out.println("begin get data!");
            System.out.println(get(key));
        }
    }
}
