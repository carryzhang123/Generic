package hang.cache.commoncache;

import com.hang.tools.threadpool.ExecutorUtils;

import java.util.logging.Logger;

/**
 * @author ZhangHang
 * @create 2018-05-31 18:40
 **/
public class TestCache {
    static Logger logger = Logger.getLogger("cachelog");

    public static void main(String[] args) {
        ICacheManager cacheManager = new CacheManagerImpl();
        cacheManager.putCache("test", "test", 10 * 1000L);
        cacheManager.putCache("myTest", "myTest", 15 * 1000L);
        CacheListener listener = new CacheListener(cacheManager);
        ExecutorUtils.addTask(listener, 0, 3000);

        logger.info("test:" + cacheManager.getCacheByKey("test").getDatas());
        logger.info("myTest:" + cacheManager.getCacheByKey("myTest").getDatas());
    }
}
