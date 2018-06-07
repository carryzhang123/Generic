package com.hang.spring.aop.spring.section.attach;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ZhangHang
 * @create 2017-07-14 12:12
 * 可复用的注入装配期BeanSelfProxyAwareMounter
 **/
@Component
public class BeanSelfProxyAwareMounter implements SystemBootAddon, ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ApplicationContext applicationContext;

    /**
     * 注入Spring容器
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 实现系统启动器接口中的装配就绪方法
     */
    @Override
    public void onReady() {
        //从容器中获取所有注入的自动代理Bean
        Map<String, BeanSelfProxyAware> proxyAwareMap = applicationContext.getBeansOfType(BeanSelfProxyAware.class);
        if (proxyAwareMap != null) {
            for (BeanSelfProxyAware beanSelfProxyAware : proxyAwareMap.values()) {
                beanSelfProxyAware.setSelfAware(beanSelfProxyAware);
                if (logger.isDebugEnabled()) {
                    logger.debug("{}注册自身被代理的实例");
                }
            }
        }
    }

    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
