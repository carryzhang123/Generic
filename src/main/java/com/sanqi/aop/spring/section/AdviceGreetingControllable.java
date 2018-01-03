package com.sanqi.aop.spring.section;

import com.sanqi.aop.common.PerformanceMonitor;
import com.sanqi.aop.spring.enhance.Monitorable;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * @author ZhangHang
 * @create 2017-07-12 17:50
 * 引介增强
 **/
@SuppressWarnings("ALL")
public class AdviceGreetingControllable extends DelegatingIntroductionInterceptor implements Monitorable {
    private ThreadLocal<Boolean> monitorStatusMap = new ThreadLocal<Boolean>();

    @Override
    public void setMonitorActive(boolean active) {
        monitorStatusMap.set(active);
    }

    //拦截方法
    @SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object obj = null;
        //对于支持性能监视可控代理，通过判断其状态决定是否开启性能监控功能
        if (monitorStatusMap.get() != null && monitorStatusMap.get()) {
            PerformanceMonitor.begin(mi.getClass().getName() + "." + mi.getMethod().getName());
            obj = super.invoke(mi);
            PerformanceMonitor.end();
        } else {
            obj = super.invoke(mi);
        }
        return obj;
    }
}
