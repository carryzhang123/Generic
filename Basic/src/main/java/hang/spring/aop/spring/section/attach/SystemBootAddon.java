package hang.spring.aop.spring.section.attach;

/**
 * Created by admin on 2017/7/14.
 */
@SuppressWarnings("ALL")
public interface SystemBootAddon {
    //在系统就绪后调用的方法
    @SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
    void  onReady();
}
