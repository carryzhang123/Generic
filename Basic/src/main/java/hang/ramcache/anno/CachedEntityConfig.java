package hang.ramcache.anno;

import com.hang.ramcache.IEntity;
import com.hang.ramcache.exception.StateException;
import com.hang.ramcache.util.ReflectionUtility;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import javax.naming.ConfigurationException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ZhangHang
 * @create 2018-01-20 14:46
 **/
public class CachedEntityConfig implements Serializable {
    private static final long serialVersionUID = -6067788531240033388L;
    private Class<? extends IEntity> clz;
    private Cached cached;
    private InitialConfig initialConfig;
    private int cachedSize;
    private transient Map<String, Unique> uniques;
    private transient Map<String, Field> uniqueFields;
    private transient Map<String, ReentrantReadWriteLock> uniqueLocks;
    private transient Map<String, Index> indexs;
    private transient Map<String, Field> indexFields;
    private transient Map<String, ReentrantReadWriteLock> indexLocks;

    public static boolean isValid(Class<?> clz, Map<String, Integer> constants) throws ConfigurationException {
        if (Modifier.isAbstract(clz.getModifiers())) {
            return false;
        } else if (Modifier.isInterface(clz.getModifiers())) {
            return false;
        } else if (!IEntity.class.isAssignableFrom(clz)) {//父类->子类
            return false;
        } else if (!clz.isAnnotationPresent(Cached.class)) {
            return false;
        } else {
            Cached cached = (Cached) clz.getAnnotation(Cached.class);
            if (!constants.containsKey(cached.size())) {
                throw new ConfigurationException("缓存字体[" + clz.getName() + "]要求的缓存数量定义[" + cached.size() + "]不存在");
            } else {
                switch (cached.unit()) {
                    case ENTITY:
                        if (ReflectionUtility.getDeclaredFieldsWith(clz, Index.class).length > 0) {
                            throw new ConfigurationException("缓存单位为[" + cached.unit() + "]的实体" + clz.getName() + "]不支持索引属性配置");
                        }
                        break;
                    case REGION:
                        if (ReflectionUtility.getDeclaredFieldsWith(clz, Unique.class).length > 0) {
                            throw new ConfigurationException("缓存单位为[" + cached.unit() + "]的实体" + clz.getName() + "]不支持唯一值属性配置");
                        }
                        break;
                    default:
                        throw new ConfigurationException("实体[" + clz.getName() + "]使用了未支持的缓存单位[" + cached.unit() + "]配置");
                }
            }
            return true;
        }
    }

    public static CachedEntityConfig valueOf(Class<? extends IEntity> clz, Map<String, Integer> constants) {
        CachedEntityConfig result = new CachedEntityConfig();
        result.clz = clz;
        result.cached = clz.getAnnotation(Cached.class);
        result.initialConfig = clz.getAnnotation(InitialConfig.class);
        result.cachedSize = constants.get(result.cached.size()).intValue();
        Field[] fields = ReflectionUtility.getDeclaredFieldsWith(clz, Unique.class);
        HashMap indexs;
        HashMap indexFields;
        HashMap indexLocks;
        Field[] arr$;
        int len$;
        int i$;
        Field field;
        String name;
        if (fields != null && fields.length > 0) {
            indexs = new HashMap(fields.length);
            indexFields = new HashMap(fields.length);
            indexLocks = new HashMap(fields.length);
            arr$ = fields;
            len$ = fields.length;

            for (i$ = 0; i$ < len$; ++i$) {
                field = arr$[i$];
                Unique unique = field.getAnnotation(Unique.class);
                name = field.getName();
                ReflectionUtility.makeAccessible(field);
                indexs.put(name, unique);
                indexFields.put(name, field);
                indexLocks.put(name, new ReentrantReadWriteLock());
            }

            result.uniques = indexs;
            result.uniqueFields = indexFields;
            result.uniqueLocks = indexLocks;
        }
        fields = ReflectionUtility.getDeclaredFieldsWith(clz, Index.class);
        if (fields != null && fields.length > 0) {
            indexs = new HashMap(fields.length);
            indexFields = new HashMap(fields.length);
            indexLocks = new HashMap(fields.length);
            arr$ = fields;
            len$ = fields.length;

            for (i$ = 0; i$ < len$; ++i$) {
                field = arr$[i$];
                Index unique = field.getAnnotation(Index.class);
                name = field.getName();
                ReflectionUtility.makeAccessible(field);
                indexs.put(name, unique);
                indexFields.put(name, field);
                indexLocks.put(name, new ReentrantReadWriteLock());
            }

            result.indexs = indexs;
            result.indexFields = indexFields;
            result.indexLocks = indexLocks;
        }

        return result;
    }

    private CachedEntityConfig() {

    }

    public Collection<String> getIndexNames() {
        if (this.indexs == null) {
            return Collections.EMPTY_SET;
        } else {
            HashSet<String> result = new HashSet<String>(this.indexs.size());
            Iterator i$ = this.indexs.keySet().iterator();

            while (i$.hasNext()) {
                String name = (String) i$.next();
                result.add(name);
            }
            return result;
        }
    }

    public String getIndexQuery(String name) {
        if (this.indexs == null) {
            throw new StateException("实体[" + this.clz.getName() + "]的索引属性名[" + name + "]无效");
        } else {
            Index index = this.indexs.get(name);
            if (index == null) {
                throw new StateException("实体[" + this.clz.getName() + "]的索引属性名[" + name + "]无效");
            } else {
                return index.query();
            }
        }
    }

    public Map<String, Object> getIndexValues(IEntity entity) {
        if (this.indexFields == null) {
            throw new StateException("实体[" + this.clz.getName() + "]没有索引属性配置无法获取索引属性值");
        } else {
            try {
                Map<String, Object> result = new HashMap<String, Object>(this.indexFields.size());
                Iterator i$ = this.indexFields.entrySet().iterator();

                while (i$.hasNext()) {
                    Map.Entry<String, Field> entry = (Map.Entry<String, Field>) i$.next();
                    Object value = ((Field) entry.getValue()).get(entity);
                    result.put(entry.getKey(), value);
                }
                return result;
            } catch (IllegalAccessException e) {
                throw new StateException("无法获取索引属性值" + e.getMessage());
            }
        }
    }

    public Object getIndexValue(String name, IEntity entity) {
        Map<String, Object> values = this.getIndexValues(entity);
        if (values.containsKey(name)) {
            return values.get(name);
        } else {
            throw new StateException("索引属性[" + name + "]不存在");
        }
    }

    public ReentrantReadWriteLock.ReadLock getIndexReadLock(String name) {
        ReentrantReadWriteLock lock = this.indexLocks.get(name);
        if (lock == null) {
            throw new StateException("实体[" + this.clz.getName() + "]的索引属性名[" + name + "]无效");
        } else {
            return lock.readLock();
        }
    }

    public ReentrantReadWriteLock.WriteLock getIndexWriteLock(String name) {
        ReentrantReadWriteLock lock = this.indexLocks.get(name);
        if (lock == null) {
            throw new StateException("实体[" + this.clz.getName() + "]的索引属性名[" + name + "]无效");
        } else {
            return lock.writeLock();
        }
    }

    public boolean hasIndexField(String name) {
        if (this.indexFields == null) {
            return false;
        } else {
            return this.indexFields.containsKey(name);
        }
    }

    public String getUniqueQuery(String name) {
        if (this.uniques == null) {
            throw new StateException("实体[" + this.clz.getName() + "]的唯一属性名[" + name + "]无效");
        } else {
            Unique unique = this.uniques.get(name);
            if (unique == null) {
                throw new StateException("实体[" + this.clz.getName() + "]的唯一属性名[" + name + "]无效");
            } else {
                return unique.query();
            }
        }
    }

    public ReentrantReadWriteLock.ReadLock getUniqueReadLock(String name) {
        ReentrantReadWriteLock lock = this.uniqueLocks.get(name);
        if (lock == null) {
            throw new StateException("实体[" + this.clz.getName() + "]的唯一属性名" + name + "]无效");
        } else {
            return lock.readLock();
        }
    }

    public ReentrantReadWriteLock.WriteLock getUniqueWriteLock(String name) {
        ReentrantReadWriteLock lock = this.uniqueLocks.get(name);
        if (lock == null) {
            throw new StateException("实体[" + this.clz.getName() + "]的唯一属性名[" + name + "]无效");
        } else {
            return lock.writeLock();
        }
    }

    public HashMap<String, DualHashBidiMap> buildUniqueCache() {
        HashMap<String, DualHashBidiMap> result = new HashMap<String, DualHashBidiMap>(this.uniqueFields.size());
        Iterator i$ = this.uniqueFields.keySet().iterator();

        for (String name : uniqueFields.keySet()) {
            DualHashBidiMap map = new DualHashBidiMap();
            result.put(name, map);
        }
        return result;
    }

    public Map<String, Object> getUniqueValues(IEntity entity) {
        if (this.uniqueFields == null) {
            throw new StateException("实体[" + this.clz.getName() + "]没有唯一属性配置无法获取唯一属性值");
        } else {
            try {
                Map<String, Object> result = new HashMap<String, Object>(this.uniqueFields.size());
                Iterator i$ = this.uniqueFields.entrySet().iterator();

                while (i$.hasNext()) {
                    Map.Entry<String, Field> entry = (Map.Entry<String, Field>) i$.next();
                    Object value = ((Field) entry.getValue()).get(entity);
                    result.put(entry.getKey(), value);
                }
                return result;
            } catch (IllegalAccessException e) {
                throw new StateException("无法获取唯一属性值：" + e.getMessage());
            }
        }
    }

    public boolean hasUniqueField() {
        return this.uniqueFields != null;
    }

    public boolean cacheUnitIs(CacheUnit unit) {
        return this.cached.unit() == unit;
    }

    public Class<? extends IEntity> getClz() {
        return this.clz;
    }

    public Cached getCached() {
        return this.cached;
    }

    public InitialConfig getInitialConfig() {
        return this.initialConfig;
    }

    public int getCachedSize() {
        return this.cachedSize;
    }

    public String getPersisterName() {
        return this.cached.persister().value();
    }
}
