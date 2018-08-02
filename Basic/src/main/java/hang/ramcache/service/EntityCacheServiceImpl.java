package hang.ramcache.service;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap.Builder;
import com.hang.ramcache.IEntity;
import com.hang.ramcache.anno.Cached;
import com.hang.ramcache.anno.CachedEntityConfig;
import com.hang.ramcache.anno.InitialConfig;
import com.hang.ramcache.exception.ConfigurationException;
import com.hang.ramcache.exception.InvaildEntityException;
import com.hang.ramcache.exception.StateException;
import com.hang.ramcache.exception.UniqueFieldException;
import com.hang.ramcache.orm.Accessor;
import com.hang.ramcache.orm.Querier;
import com.hang.ramcache.persist.AbstractListener;
import com.hang.ramcache.persist.Element;
import com.hang.ramcache.persist.Persister;
import com.hang.ramcache.util.ConcurrentHashSet;
import com.hang.ramcache.util.LruSoftHashMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ZhangHang
 * @create 2018-01-30 16:07
 **/
public class EntityCacheServiceImpl<PK extends Comparable<PK> & Serializable, T extends IEntity<PK>> implements EntityCacheService<PK, T>, CacheFinder<PK, T>, EntityEnhanceService<PK, T> {
    private static final Logger logger = LoggerFactory.getLogger(EntityCacheServiceImpl.class);
    /**
     * 初始化标识
     */
    private boolean initialize;
    /**
     * 实体类型
     */
    private Class<T> entityClz;
    /**
     * 缓存实体配置信息
     */
    private CachedEntityConfig config;
    /**
     * 存储器
     */
    private Accessor accessor;
    /**
     * 查询器
     */
    private Querier querier;
    /**
     * 实体持久化处理器
     */
    private Persister persister;
    /**
     * 实体缓存
     */
    private LruSoftHashMap<PK, T> cache;


    /**
     * 初始化方法
     */
    public synchronized void initialize(CachedEntityConfig config, Persister persister, Accessor accessor, Querier querier) {
        if (initialize) {
            throw new StateException("重复初始化异常");
        }
        //初始化属性域
        initFileds(config, persister, accessor, querier);
        //缓存初始化处理
        initCaches(config, querier);
        this.initialize = true;
    }

    /**
     * 正在被移除的实体的主键集合
     */
    private ConcurrentHashSet<PK> removing = new ConcurrentHashSet<>();
    /**
     * 实体操作读写锁
     */
    private ConcurrentHashMap<PK, ReentrantLock> locks = new ConcurrentHashMap<>();
    /**
     * 唯一键缓存
     */
    private HashMap<String, DualHashBidiMap> uniques;


    @Override
    public T load(PK id) {
        uninitializeThrowException();
        if (removing.contains(id)) {
            return null;
        }
        T current = cache.get(id);
        if (current != null) {
            return current;
        }

        Lock lock = lockPkLock(id);
        try {
            if (removing.contains(id)) {
                return null;
            }
            current = cache.get(id);
            if (current != null) {
                return current;
            }
            current = accessor.load(entityClz, id);
            if (current != null) {
                if (config.hasUniqueField()) {
                    Map<String, Object> uniqueValues = config.getUniqueValues(current);
                    for (Map.Entry<String, Object> entry : uniqueValues.entrySet()) {
                        addUniqueValue(entry.getKey(), entry.getValue(), id);
                    }
                }
                cache.put(id, current);
            }
            return current;
        } finally {
            releasePkLock(id, lock);
        }
    }

    private void releasePkLock(PK id, Lock lock) {
        lock.unlock();
        locks.remove(id);
    }

    private Lock lockPkLock(PK id) {
        ReentrantLock lock = locks.get(id);
        if (lock == null) {
            lock = new ReentrantLock();
            ReentrantLock prevLock = locks.putIfAbsent(id, lock);
            lock = prevLock != null ? prevLock : lock;
        }
        lock.lock();
        return lock;
    }

    @Override
    public T unique(String name, Object value) {
        uninitializeThrowException();
        if (!config.hasUniqueField()) {
            throw new UniqueFieldException();
        }
        PK id = null;
        ReentrantReadWriteLock.ReadLock readLock = config.getUniqueReadLock(name);
        readLock.lock();
        try {
            DualHashBidiMap unique = uniques.get(name);
            id = (PK) unique.get(value);
        } finally {
            readLock.unlock();
        }
        if (id != null) {
            return load(id);
        }
        T current = querier.unique(entityClz, config.getUniqueQuery(name), value);
        if (current == null) {
            return current;
        }
        id = current.getId();
        Lock lock = lockPkLock(id);
        try {
            if (removing.contains(id)) {
                return null;
            }
            T prev = cache.get(id);
            if (prev != null) {
                return prev;
            }
            if (config.hasUniqueField()) {
                Map<String, Object> uniqueValues = config.getUniqueValues(current);
                for (Map.Entry<String, Object> entry : uniqueValues.entrySet()) {
                    addUniqueValue(entry.getKey(), entry.getValue(), id);
                }
            }
            cache.put(id, current);
        } finally {
            releasePkLock(id, lock);
        }
        return current;
    }

    @Override
    public T loadOrCreate(PK id, EntityBuilder<PK, T> builder) {
        uninitializeThrowException();
        if (!removing.contains(id)) {
            T current = cache.get(id);
            if (current != null) {
                return current;
            }
        }
        T current = null;
        Lock lock = lockPkLock(id);
        try {
            current = cache.get(id);
            if (current != null) {
                return current;
            }
            current = accessor.load(entityClz, id);
            boolean flag = removing.contains(id);
            if (current == null || flag) {
                current = builder.newInstance(id);
                if (current == null) {
                    throw new InvaildEntityException();
                }
                if (current.getId() == null) {
                    throw new InvaildEntityException();
                }
                if (config.hasUniqueField()) {
                    Map<String, Object> uniqueValues = config.getUniqueValues(current);
                    boolean rollback = false;
                    List<Map.Entry<String, Object>> temp = new ArrayList<>(uniqueValues.size());
                    for (Map.Entry<String, Object> entry : uniqueValues.entrySet()) {
                        String uniqueName = entry.getKey();
                        Object uniqueValue = entry.getValue();
                        ReentrantReadWriteLock.WriteLock uniqueLock = config.getUniqueWriteLock(uniqueName);

                        uniqueLock.lock();
                        try {
                            DualHashBidiMap unique = uniques.get(uniqueName);
                            if (unique.containsKey(uniqueValue)) {
                                rollback = true;
                                break;
                            }
                            T chkEntity = querier.unique(entityClz, config.getUniqueQuery(uniqueName), uniqueValue);
                            if (chkEntity != null) {
                                rollback = true;
                                break;
                            }
                            PK prev = (PK) unique.put(uniqueValue, id);
                            if (prev != null) {
                                logger.error("错误");
                            }
                            temp.add(entry);
                        } finally {
                            uniqueLock.unlock();
                        }
                    }
                    if (rollback) {
                        for (Map.Entry<String, Object> entry : temp) {
                            removeUniqueValue(entry.getKey(), entry.getValue());
                        }
                        throw new UniqueFieldException();
                    }
                }
                if (flag) {
                    removing.remove(id);
                }
                persister.put(Element.saveOf(current));
            } else {
                if (config.hasUniqueField()) {
                    Map<String, Object> uniqueValues = config.getUniqueValues(current);
                    for (Map.Entry<String, Object> entry : uniqueValues.entrySet()) {
                        addUniqueValue(entry.getKey(), entry.getValue(), id);
                    }
                }
            }
            cache.put(id, current);
            return current;
        } finally {
            releasePkLock(id, lock);
        }
    }

    @Override
    public T create(PK id, EntityBuilder<PK, T> builder) {
        uninitializeThrowException();
        if (!removing.contains(id)) {
            T current = cache.get(id);
            if (current != null) {
                return current;
            }
        }
        T current = null;
        Lock lock = lockPkLock(id);
        try {
            current = cache.get(id);
            if (current != null) {
                return current;
            }
            boolean flag = removing.contains(id);
            current = builder.newInstance(id);
            if (current == null || current.getId() == null) {
                throw new InvaildEntityException();
            }
            if (config.hasUniqueField()) {
                Map<String, Object> uniqueValues = config.getUniqueValues(current);
                for (Map.Entry<String, Object> entry : uniqueValues.entrySet()) {
                    String uniqueName = entry.getKey();
                    Object uniqueValue = entry.getValue();
                    ReentrantReadWriteLock.WriteLock uniqueLock = config.getUniqueWriteLock(uniqueName);
                    uniqueLock.lock();
                    try {
                        DualHashBidiMap unique = uniques.get(uniqueName);
                        PK prev = (PK) unique.put(uniqueValue, id);
                        if (prev != null) {
                            logger.error("cuowu");
                        }
                    } finally {
                        uniqueLock.lock();
                    }
                }
            }
            if (flag) {
                removing.remove(id);
            }
            persister.put(Element.saveOf(current));
            cache.put(id, current);
            return current;
        } finally {
            releasePkLock(id, lock);
        }
    }

    @Override
    public void writeBack(PK id, T entity) {
        uninitializeThrowException();
        if (removing.contains(id)) {
            if (logger.isWarnEnabled()) {
                logger.warn("错误", entityClz.getSimpleName(), id);
            }
            return;
        }
        persister.put(Element.updateOf(entity));
    }

    @Override
    public T remove(PK id) {
        uninitializeThrowException();
        if (removing.contains(id)) {
            return null;
        }
        Lock lock = lockPkLock(id);
        try {
            if (removing.contains(id)) {
                return null;
            }
            removing.add(id);
            T current = cache.remove(id);
            if (current != null && config.hasUniqueField()) {
                Map<String, Object> uniqueValues = config.getUniqueValues(current);
                for (Map.Entry<String, Object> entry : uniqueValues.entrySet()) {
                    removeUniqueValue(entry.getKey(), entry.getValue());
                }
            }
            persister.put(Element.removeOf(id, entityClz));
            return current;
        } finally {
            releasePkLock(id, lock);
        }
    }

    @Override
    public void clear(PK id) {
        T current = cache.get(id);
        if (current != null) {
            return;
        }
        Lock lock = lockPkLock(id);
        try {
            current = cache.remove(id);
            if (current != null && config.hasUniqueField()) {
                Map<String, Object> uniqueValues = config.getUniqueValues(current);
                for (Map.Entry<String, Object> entry : uniqueValues.entrySet()) {
                    removeUniqueValue(entry.getKey(), entry.getValue());
                }
            }
        } finally {
            releasePkLock(id, lock);
        }
    }

    @Override
    public CacheFinder<PK, T> getFinder() {
        uninitializeThrowException();
        return this;
    }

    @Override
    public CachedEntityConfig getEntityConfig() {
        uninitializeThrowException();
        return config;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean hasUniqueValue(String name, Object value) {
        DualHashBidiMap unique = uniques.get(name);
        if (config.getUniqueWriteLock(name).isHeldByCurrentThread()) {
            if (unique.containsKey(value)) {
                return true;
            }
            T current = querier.unique(entityClz, config.getUniqueQuery(name), value);
            if (current == null) {
                return false;
            }
            return true;
        }

        ReentrantReadWriteLock.WriteLock lock = config.getUniqueWriteLock(name);
        lock.lock();
        try {
            if (unique.containsKey(value)) {
                return true;
            }
            T currnet = querier.unique(entityClz, config.getUniqueQuery(name), value);
            if (currnet == null) {
                return false;
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void replaceUniqueValue(PK id, String name, Object value) {
        if (!config.getUniqueWriteLock(name).isHeldByCurrentThread()) {
            throw new StateException();
        }
        DualHashBidiMap unique = uniques.get(name);
        unique.removeValue(id);
        unique.put(value, id);
    }

    @Override
    public Persister getPersister() {
        return persister;
    }

    //内部方法

    /**
     * 检查是否已经完成初始化（未完成会抛出异常）
     */
    private void uninitializeThrowException() {
        if (!initialize) {
            throw new StateException("未完成初始化");
        }
    }

    @Override
    public Set<T> find(Filter<T> filter) {
        uninitializeThrowException();
        HashSet<T> result = new HashSet<T>();
        for (T entity : cache.values()) {
            if (filter.isExclude(entity)) {
                continue;
            }
            result.add(entity);
        }
        return result;
    }

    @Override
    public List<T> sort(Comparator<T> comparator) {
        uninitializeThrowException();
        ArrayList<T> result = new ArrayList<>(cache.values());
        Collections.sort(result, comparator);
        return result;
    }

    @Override
    public List<T> find(Filter<T> filter, Comparator<T> comparator) {
        uninitializeThrowException();
        ArrayList<T> result = new ArrayList<>();
        for (T entity : cache.values()) {
            if (filter.isExclude(entity)) {
                continue;
            }
            result.add(entity);

        }
        Collections.sort(result, comparator);
        return result;
    }

    @Override
    public Set<T> all() {
        uninitializeThrowException();
        HashSet<T> result = new HashSet<>(cache.values());
        return result;
    }

    @Override
    public int getAllSize() {
        return this.cache.getCacheMapSize();
    }

    //内部方法
    private void addUniqueValue(String name, Object value, PK id) {
        DualHashBidiMap unique = uniques.get(name);
        ReentrantReadWriteLock.WriteLock uniqueLock = config.getUniqueWriteLock(name);
        uniqueLock.lock();
        try {
            PK prev = (PK) unique.put(value, id);
            if (prev != null) {
                logger.error("bucunzai");
            }
        } finally {
            uniqueLock.unlock();
        }
    }

    private void removeUniqueValue(String name, Object value) {
        DualHashBidiMap unique = uniques.get(name);
        ReentrantReadWriteLock.WriteLock uniqueLock = config.getUniqueWriteLock(name);
        uniqueLock.lock();
        try {
            unique.remove(value);
        } finally {
            uniqueLock.unlock();
        }
    }

    /**
     * 初始化可直接获取的域属性
     *
     * @param config
     * @param persister
     * @param accessor
     * @param querier
     */
    private void initFileds(final CachedEntityConfig config, final Persister persister, Accessor accessor, Querier querier) {
        Cached cached = config.getCached();
        //初始化属性域
        this.config = config;
        this.accessor = accessor;
        this.querier = querier;
        this.entityClz = (Class<T>) config.getClz();
        this.persister = persister;
        this.persister.addListener(entityClz, new AbstractListener() {

            @Override
            protected void onRemoveSuccess(Serializable id) {
                removing.remove(id);
            }

            @Override
            protected void onRemoveError(Serializable id, RuntimeException ex) {
                removing.remove(id);
            }

            @Override
            protected void onSaveError(Serializable id, IEntity entity, RuntimeException ex) {
                if (ex instanceof ConcurrentModificationException) {
                    persister.put(Element.saveOf(entity));
                }
            }

            @Override
            protected void onUpdateError(IEntity entity, RuntimeException ex) {
                if (ex instanceof ConcurrentModificationException) {
                    persister.put(Element.updateOf(entity));
                }
            }
        });
        if (config.hasUniqueField()) {
            this.uniques = config.buildUniqueCache();
        }
        //初始化实体缓存空间
        switch (cached.type()) {
            case LRU:
                Builder<PK, T> builder = new Builder<PK, T>().initialCapacity(cached.initialCapacity()).maximumWeightedCapacity(config.getCachedSize()).concurrencyLevel(cached.concurrencyLevel());
                this.cache = new LruSoftHashMap<>(builder.build());
                break;
            case MANUAL:
                this.cache = new LruSoftHashMap<PK, T>(new ConcurrentHashMap<PK, T>(cached.initialCapacity(), (float) 0.75, cached.concurrencyLevel()));
                break;
            default:
                throw new ConfigurationException("未支持的缓存管理类型[" + cached.type() + "]");
        }
    }

    /**
     * 初始化缓存配置
     *
     * @param config
     * @param querier
     */
    private void initCaches(CachedEntityConfig config, Querier querier) {
        InitialConfig initial = config.getInitialConfig();
        if (initial == null) {
            return;
        }
        //获取要初始化的实体列表
        List<T> entities = null;
        switch (initial.type()) {
            case ALL:
                entities = querier.all(entityClz);
                break;
            case QUERY:
                entities = querier.list(entityClz, initial.query());
                break;
            default:
                throw new ConfigurationException("无法按配置[" + initial + "]初始化实体[" + this.entityClz.getName() + "]的缓存");
        }
        //初始化缓存数据
        for (T entity : entities) {
            PK id = entity.getId();
            cache.put(id, entity);
            if (config.hasUniqueField()) {
                Map<String, Object> uniqueValues = config.getUniqueValues(entity);
                for (Map.Entry<String, Object> entry : uniqueValues.entrySet()) {
                    DualHashBidiMap unique = uniques.get(entry.getKey());
                    unique.put(entry.getValue(), id);
                }
            }
        }
    }
}
