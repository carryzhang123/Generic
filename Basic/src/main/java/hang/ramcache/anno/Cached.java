package hang.ramcache.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ZhangHang
 * @create 2018-01-20 14:12
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Cached {
    String size();

    int initialCapacity() default 16;

    int concurrencyLevel() default 16;

    Persister persister() default @Persister;

    CacheType type() default CacheType.LRU;

    CacheUnit unit() default CacheUnit.ENTITY;

    boolean enhanced() default true;
}
