package com.hang.cache.commoncache;

import java.util.Map;
import java.util.logging.Logger;

/**
 * @author ZhangHang
 * @create 2018-05-31 18:30
 **/
public class CacheListener implements Runnable {
    Logger logger = Logger.getLogger("cachelog");
    private ICacheManager cacheManager;

    public CacheListener(ICacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void run() {
        for (Map.Entry<String, EntityCache> entry : cacheManager.getCacheAll().entrySet()) {
            if (cacheManager.isTimeOut(entry.getKey())) {
                cacheManager.clearByKey(entry.getKey());
                logger.info(entry.getKey() + "：缓存被清除");
            }
        }
    }
}
