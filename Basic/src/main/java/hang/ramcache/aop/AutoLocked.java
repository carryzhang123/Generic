package hang.ramcache.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ZhangHang
 * @create 2018-01-22 20:31
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AutoLocked {
}
