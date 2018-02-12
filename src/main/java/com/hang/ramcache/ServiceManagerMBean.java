package com.hang.ramcache;

import com.hang.ramcache.anno.CachedEntityConfig;

import java.util.Map;

/**
 * @author ZhangHang
 * @create 2018-01-30 14:15
 **/
public interface ServiceManagerMBean {
    Map<String, Map<String, String>> getAllPersisterInfo();

    Map<String, String> getPersisterInfo(String var1);

    Map<String, CachedEntityConfig> getAllCachedEntityConfig();
}
