package com.hang.ramcache.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author ZhangHang
 * @create 2018-02-05 16:14
 **/
public class NamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser(ElementNames.RAMCACHE,new RamCacheParser());
    }
}
