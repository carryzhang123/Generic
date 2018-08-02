package com.hang.ramcache.persist;

import com.hang.ramcache.orm.Accessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZhangHang
 * @create 2018-01-29 11:53
 **/
public class QueueConsumer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(QueueConsumer.class);
    private final String name;
    private final BlockingQueue<Element> queue;
    private final QueuePersister owner;
    private final Accessor accessor;
    private final Thread me;
    private final AtomicInteger error = new AtomicInteger();
    private volatile boolean pause;

    public QueueConsumer(String name, Accessor accessor, BlockingQueue<Element> queue, QueuePersister owner) {
        this.name = name;
        this.queue = queue;
        this.accessor = accessor;
        this.owner = owner;
        this.me = new Thread(this, "持久化[" + name + ":队列]");
        this.me.setDaemon(true);
        this.me.start();
    }

    @Override
    public void run() {
        while (true) {
            if (this.pause) {
                Thread.yield();
            } else {
                Element element = null;
                Class clz = null;

                try {
                    element = this.queue.take();
                    clz = element.getEntityClass();
                    switch (element.getType()) {
                        case SAVE:
                            if (element.getEntity().serialize()) {
                                this.accessor.save(clz, element.getEntity());
                            }
                            break;
                        case REMOVE:
                            this.accessor.remove(clz, element.getId());
                            break;
                        case UPDATE:
                            this.owner.removeUpdating(element.getIdentity());
                            if (element.getEntity().serialize()) {
                                this.accessor.saveOrUpdate(clz, element.getEntity());
                            }
                            break;
                        default:
                            logger.error("未支持的更新队列元素类型[{}]");
                    }
                    Listener listener = this.owner.getListener(clz);
                    if (listener != null) {
                        listener.notify(element.getType(), true, element.getId(), element.getEntity(), null);
                    }
                } catch (RuntimeException var5) {
                    this.error.incrementAndGet();
                    if (logger.isWarnEnabled()) {
                        logger.warn("实体更新队列[{}]处理元素[{}]时出现异常:[{}]", new Object[]{this.name, element, var5.getMessage()});
                    }

                    Listener listener = this.owner.getListener(clz);
                    if (listener != null) {
                        listener.notify(element.getType(), false, element.getId(), element.getEntity(), var5);
                    }
                } catch (Throwable var6) {
                    this.error.incrementAndGet();
                    if (element == null) {
                        logger.error("获取更新队列元素时线程被非法打断", var6);
                    } else {
                        logger.error("更新队列处理未知异常", var6);
                    }
                }
            }
        }
    }

    public int getError() {
        return this.error.get();
    }
}
