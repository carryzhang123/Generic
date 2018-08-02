package hang.ramcache.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ZhangHang
 * @create 2018-01-20 14:45
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Unique {
    String query();
}
