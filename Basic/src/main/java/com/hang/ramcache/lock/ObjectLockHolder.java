package com.hang.ramcache.lock;

import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhangHang
 * @create 2018-01-25 21:29
 **/
public class ObjectLockHolder {
    private ConcurrentHashMap<Class,Holder> holders=new ConcurrentHashMap<Class, Holder>();

    public ObjectLockHolder(){

    }
    public ObjectLock getLock(Object object){
        ObjectLockHolder.Holder holder=this.getHolder(object.getClass());
        ObjectLock lock=holder.getLock(object);
        return lock;
    }

    private ObjectLockHolder.Holder getHolder(Class clz){
        ObjectLockHolder.Holder holder=this.holders.get(clz);
        if(holder!=null){
            return holder;
        }else {
            this.holders.putIfAbsent(clz,new ObjectLockHolder.Holder(clz));
            return this.holders.get(clz);
        }
    }

    public Lock getTieLock(Class clz){
        ObjectLockHolder.Holder holder=this.getHolder(clz);
        return holder.getTieLock();
    }

    public int count(Class<?> clz){
        if(this.holders.contains(clz)){
            ObjectLockHolder.Holder holder=this.getHolder(clz);
            return holder.count();
        }else {
            return 0;
        }
    }

    public class Holder{
        private final Class clz;
        private final Lock tieLock=new ReentrantLock();
        private final WeakHashMap<Object,ObjectLock> locks=new WeakHashMap<Object, ObjectLock>();

        public Holder(Class clz){
            this.clz=clz;
        }

        public ObjectLock getLock(Object object){
            ObjectLock result=this.locks.get(object);
            return result!=null?result:this.createLock(object);
        }

        private synchronized ObjectLock createLock(Object object){
            ObjectLock result=this.locks.get(object);
            if(result!=null){
                return result;
            }else {
                result=new ObjectLock(object);
                this.locks.put(object,result);
                return result;
            }
        }

        public Lock getTieLock() {
            return this.tieLock;
        }

        public int count(){
            return this.locks.size();
        }
    }


}
