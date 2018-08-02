package com.hang.ramcache.persist;

import com.hang.ramcache.IEntity;
import com.hang.ramcache.exception.ConfigurationException;
import com.hang.ramcache.exception.StateException;
import com.hang.ramcache.orm.Accessor;
import com.hang.ramcache.util.ConcurrentHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author ZhangHang
 * @create 2018-01-29 14:51
 **/
public class QueuePersister implements Persister {
    private static final Logger logger = LoggerFactory.getLogger(QueuePersister.class);
    private static final String SPLIT = ":";
    private String name;
    private BlockingQueue<Element> queue;
    private ConcurrentHashMap<Class<? extends IEntity>, Listener> listeners = new ConcurrentHashMap<Class<? extends IEntity>, Listener>();
    private boolean initialize;
    private boolean flag;
    private ConcurrentHashSet<String> updating = new ConcurrentHashSet<>();
    private QueueConsumer consumer;
    private volatile boolean stop;

    public QueuePersister() {
    }

    public synchronized void initialize(String name, Accessor accessor, String config) {
        if (this.initialize) {
            throw new ConfigurationException("重复初始化异常");
        } else {
            Assert.notNull(accessor, "持久化数据访问器不能为null");
            try {
                String[] array = config.split(":");
                int size = Integer.parseInt(array[0]);
                if (size >= 0) {
                    this.queue = new ArrayBlockingQueue<>(size);
                } else {
                    this.queue = new LinkedBlockingDeque<>();
                }

                this.flag = Boolean.parseBoolean(array[1]);
                this.name = name;
                this.consumer = new QueueConsumer(name, accessor, this.queue, this);
                this.initialize = true;
            } catch (Exception var6) {
                throw new ConfigurationException("持久化处理器[" + name + "]初始化异常:" + var6.getMessage());
            }
        }
    }

    public void addListener(Class<? extends IEntity> clz, Listener listener) {
        if (listener == null) {
            throw new ConfigurationException("被添加的监听器实例不能为空");
        } else {
            this.listeners.put(clz, listener);
        }
    }

    @Override
    public void put(Element element) {
        if (element != null) {
            if (this.stop) {
                FormattingTuple message = MessageFormatter.format("实体更新队列[{}]已经停止，更新元素[{}]将不被接受", this.name, element);
                logger.error(message.getMessage());
                throw new StateException(message.getMessage());
            } else {
                try {
                    if (this.flag && element.getType() == EventType.UPDATE) {
                        String identify = element.getIdentity();
                        if (this.updating.contains(identify)) {
                            return;
                        }
                        this.updating.add(identify);
                    }
                    this.queue.put(element);
                } catch (InterruptedException var3) {
                    logger.error("等待将元素[{}]添加到队列时被打断", new Object[]{element, var3});
                    if (element.getType() == EventType.UPDATE) {
                        this.updating.remove(element.getIdentity());
                    }
                }
            }
        }
    }


    @Override
    public Listener getListener(Class<? extends IEntity> clz) {
        return this.listeners.get(clz);
    }

    @Override
    public void shutdown() {
        this.stop = true;
        while (!this.queue.isEmpty()) {
            Thread.yield();
        }
        if (logger.isDebugEnabled()) {
            logger.debug("实体更新队列[{}]完成关闭", this.name);
        }
    }

    @Override
    public Map<String, String> getInfo() {
        HashMap<String, String> result = new HashMap<>();
        result.put("size", Integer.toString(this.size()));
        result.put("error", Integer.toString(this.consumer.getError()));
        return result;
    }

    public void removeUpdating(String identify) {
        if (this.flag) {
            this.updating.remove(identify);
        }
    }

    public int size() {
        return this.queue.size();
    }
}
