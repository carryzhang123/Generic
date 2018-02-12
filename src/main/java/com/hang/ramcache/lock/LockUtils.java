package com.hang.ramcache.lock;

import java.util.*;
import java.util.concurrent.locks.Lock;

/**
 * @author ZhangHang
 * @create 2018-01-25 21:28
 **/
public class LockUtils {
    private static ObjectLockHolder holder = new ObjectLockHolder();

    public LockUtils()

    {
    }

    public static ChainLock getLock(Object... objects) throws IllegalAccessException {
        List<? extends Lock> locks = loadLocks(objects);
        return new ChainLock(locks);
    }

    public static List<? extends Lock> loadLocks(Object... objects) {
        List<ObjectLock> locks = new ArrayList<ObjectLock>();
        Object[] arr$ = objects;
        int len$ = objects.length;

        int i;
        ObjectLock lock2;
        for (i = 0; i < len$; i++) {
            Object obj = arr$[i];
            lock2 = holder.getLock(obj);
            locks.add(lock2);
        }

        Collections.sort(locks);
        TreeSet<Integer> idx = new TreeSet<Integer>();
        Integer start = null;

        for (i = 0; i < locks.size(); i++) {
            if (start == null) {
                start = i;
            } else {
                ObjectLock lock1 = locks.get(start.intValue());
                lock2 = locks.get(i);
                if (lock1.isTie(lock2)) {
                    idx.add(start);
                } else {
                    start = i;
                }
            }
        }

        if (idx.size() == 0) {
            return locks;
        } else {
            List<Lock> news = new ArrayList<Lock>(locks.size() + idx.size());
            news.addAll(locks);
            Iterator it = idx.descendingIterator();

            while (it.hasNext()) {
                Integer i$ = (Integer) it.next();
                ObjectLock lock = locks.get(i$.intValue());
                Lock tieLock = holder.getTieLock(lock.getClz());
            }
            return news;
        }
    }
}
