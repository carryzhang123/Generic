package com.hang.ramcache.persist;

import com.hang.ramcache.IEntity;
import com.hang.ramcache.exception.ConfigurationException;
import com.hang.ramcache.exception.StateException;
import com.hang.ramcache.orm.Accessor;
import com.hang.tools.time.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ZhangHang
 * @create 2018-01-29 15:45
 **/
public class TimingPersister implements Persister {
    private static final Logger logger = LoggerFactory.getLogger(TimingPersister.class);
    private ConcurrentHashMap<String, Element> elements = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Class<? extends IEntity>, Listener> listeners = new ConcurrentHashMap<>();
    private boolean initialize;
    private TimingConsumer consumer;
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private volatile boolean stop;
    private ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    public TimingPersister() {
    }

    @Override
    public synchronized void initialize(String name, Accessor accessor, String cron) {
        if (this.initialize) {
            throw new ConfigurationException("重复初始化异常");
        } else {
            Assert.notNull(accessor, "持久层数据访问器不能为null");
            try {
                TimeUtils.getNextTime(cron, new Date());
                this.elements = new ConcurrentHashMap<>();
                this.consumer = new TimingConsumer(name, cron, accessor, this);
                this.initialize = true;
            } catch (Exception var5) {
                throw new ConfigurationException("定时持久化处理器[" + name + "]初始化失败:" + var5.getMessage());
            }
        }
    }

    @Override
    public void put(Element element) {
        if (element != null) {
            if (this.stop) {
                FormattingTuple message = MessageFormatter.format("实体更新队列已经停止，更新元素[{}]降不被接受", element);
                logger.error(message.getMessage());
                throw new StateException(message.getMessage());
            } else {
                String id = element.getIdentity();
                this.rwLock.readLock().lock();
                Lock lock = this.lockIdLock(id);

                try {
                    Element prev = this.elements.get(id);
                    if (prev != null) {
                        EventType prevType = prev.getType();
                        if (!prev.update(element)) {
                            this.elements.remove(id);
                        } else if (prevType == EventType.REMOVE && prev.getType() == EventType.UPDATE) {
                            Listener listener = this.getListener(element.getEntityClass());
                            if (listener != null) {
                                listener.notify(EventType.REMOVE, true, prev.getId(), null, null);
                            }
                            return;
                        }
                        return;
                    }
                    this.elements.put(id, element);
                } finally {
                    {
                        this.releaseIdLock(id, lock);
                        this.rwLock.readLock().unlock();
                    }
                }
            }
        }
    }

    @Override
    public void addListener(Class<? extends IEntity> clz, Listener listener) {
        if (listener == null) {
            throw new ConfigurationException("被添加的监听器实例不能为空");
        } else {
            this.listeners.put(clz, listener);
        }
    }

    @Override
    public Listener getListener(Class<? extends IEntity> clz) {
        return this.listeners.get(clz);
    }

    @Override
    public void shutdown() {
        this.stop = true;
        this.consumer.stop();
        while (this.consumer.getState() != TimingCounsumerState.STOPPED) {
            Thread.yield();
        }
    }

    @Override
    public Map<String, String> getInfo() {
        HashMap<String, String> result = new HashMap<>();
        result.put("size", Integer.toString(this.size()));
        result.put("state", this.consumer.getState().name());
        result.put("nextTime", TimeUtils.date2String(this.consumer.getNextTime(), "yyyy-MM-dd HH:mm:ss"));
        return result;
    }

    public int size() {
        return this.elements.size();
    }

    private Lock lockIdLock(String id) {
        ReentrantLock lock = this.locks.get(id);
        if (lock == null) {
            lock = new ReentrantLock();
            ReentrantLock prevLock = this.locks.putIfAbsent(id, lock);
            lock = prevLock != null ? prevLock : lock;
        }
        lock.lock();
        return lock;
    }

    private void releaseIdLock(String id, Lock lock) {
        lock.unlock();
        this.locks.remove(id);
    }

    Collection<Element> clearElements() {
        this.rwLock.writeLock().lock();

        ArrayList var2;
        try {
            ArrayList<Element> result = new ArrayList<>(this.elements.values());
            this.elements.clear();
            var2 = result;
        } finally {
            this.rwLock.writeLock().unlock();
        }

        return var2;
    }
}
