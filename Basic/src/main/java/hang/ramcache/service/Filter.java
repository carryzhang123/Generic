package hang.ramcache.service;

import com.hang.ramcache.IEntity;

/**
 * 查询结果过滤器
 *
 * @param <T>
 */
public interface Filter<T extends IEntity> {
    /**
     * 检查是否排除该实体
     *
     * @param var1 被检查的实体，不会为null
     * @return true:排除被检查的实体（不会出现在返回结果），false:不排除被检查的实体（会出现在返回结果中）
     */
    boolean isExclude(T var1);
}
