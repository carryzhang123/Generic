package com.sanqi.aop.spring.section.attach;

/**
 * Created by admin on 2017/7/14.
 */
public interface BeanSelfProxyAware {
    void setSelfAware(Object object);//织入自身代理类接口
}
