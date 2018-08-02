package hang.ramcache.persist;

import com.hang.ramcache.orm.Accessor;
import com.hang.tools.time.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZhangHang
 * @create 2018-01-29 15:42
 **/
public class TimingConsumer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(TimingConsumer.class);
    private String name;
    private String cron;
    private Accessor accessor;
    private TimingPersister owner;
    private Thread me;
    private TimingCounsumerState state;
    private volatile boolean stoped;
    private Date nextTime;
    private AtomicInteger error = new AtomicInteger();
    private volatile boolean interrupted;

    public TimingConsumer(String name, String cron, Accessor accessor, TimingPersister owner) {
        this.name = name;
        this.cron = cron;
        this.accessor = accessor;
        this.owner = owner;
        this.me = new Thread(this::run, "持久化[" + name + "]定时");
        this.me.setDaemon(true);
        this.me.start();
    }

    @Override
    public void run() {
        while (!this.stoped) {
            try {
                Collection<Element> elements = null;
                Thread var2 = this.me;
                synchronized (this.me) {
                    this.state = TimingCounsumerState.WAITING;
                    if (!this.interrupted) {
                        this.nextTime = TimeUtils.getNextTime(this.cron, new Date());
                        if (logger.isDebugEnabled()) {
                            logger.debug("定时入库[{}]的下个执行时间点为[{}]", this.name, TimeUtils.date2String(this.nextTime, "yyyy-MM-dd HH:mm:ss"));
                        }
                        try {
                            long ms = this.nextTime.getTime() - System.currentTimeMillis();
                            if (ms > 0L) {
                                this.me.wait(ms);
                            }
                        } catch (InterruptedException var8) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("定时入库[{}]被迫使立即执行[{}]", this.name, TimeUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
                            }
                        }
                    }
                    elements = this.owner.clearElements();
                    this.interrupted = false;
                    this.state = TimingCounsumerState.RUNNING;
                }
                Date start = new Date();
                if (logger.isDebugEnabled()) {
                    logger.debug("定时入库[{}]开始[{}]执行", this.name, TimeUtils.date2String(start, "yyyy-MM-dd HH:mm:ss"));
                }
                this.persist(elements);
                if (logger.isDebugEnabled()) {
                    logger.debug("定时入库[{}]入库[{}]条数据耗时[{}]", new Object[]{this.name, elements.size(), System.currentTimeMillis() - start.getTime()});
                }
            } catch (Throwable var10) {
                logger.error("Timing存储线程非法中断！", var10);
            }
        }
        Thread var11 = this.me;
        synchronized (this.me) {
            Collection<Element> elements = this.owner.clearElements();
            this.persist(elements);
            this.state = TimingCounsumerState.STOPPED;
            this.interrupted = false;
        }
    }

    private void persist(Collection<Element> elements) {
        Iterator i$ = elements.iterator();
        while (i$.hasNext()) {
            Element element = (Element) i$.next();
            boolean succFlag = false;
            Listener listener;
            try {
                Class clz = element.getEntityClass();
                switch (element.getType()) {
                    case SAVE:
                        if (element.getEntity().serialize()) {
                            this.accessor.save(clz, element.getEntity());
                            succFlag = true;
                        }
                        break;
                    case REMOVE:
                        this.accessor.remove(clz, element.getId());
                        succFlag = true;
                        break;
                    case UPDATE:
                        if (element.getEntity().serialize()) {
                            this.accessor.saveOrUpdate(clz, element.getEntity());
                            succFlag = true;
                        }
                }
                listener = this.owner.getListener(clz);
                if (listener != null) {
                    listener.notify(element.getType(), succFlag, element.getId(), element.getEntity(), null);
                }
            } catch (Exception var8) {
                this.error.getAndIncrement();
                if (element == null) {
                    logger.error("获取更新队列元素时线程被非法打断", var8);
                } else {
                    logger.error("更新队列处理出现未知异常", var8);
                }
            }
        }
    }

    public void stop() {
        if (logger.isDebugEnabled()) {
            logger.debug("定时入库[{}]收到停止通知", this.name);
        }

        Thread var1 = this.me;
        synchronized (this.me) {
            this.stoped = true;
            this.interrupted = true;
            this.me.notify();
        }

        while (this.interrupted) {
            Thread.yield();
        }
    }

    public void interrupt() {
        Thread var1 = this.me;
        synchronized (this.me) {
            this.interrupted = true;
            this.me.notify();
        }

        while (this.interrupted) {
            Thread.yield();
        }
    }

    public TimingCounsumerState getState() {
        Thread var1 = this.me;
        synchronized (this.me) {
            return this.state;
        }
    }

    public Date getNextTime() {
        return this.nextTime;
    }

    public int getError() {
        return this.error.get();
    }
}
