package hang.cache.commoncache;

/**
 * @author ZhangHang
 * @create 2018-05-31 18:00
 **/
public class EntityCache {
    /**
     * 保存的数据
     */
    private Object datas;
    /**
     * 失效时间
     */
    private long timeOut;
    /**
     * 最后刷新时间
     */
    private long lastRefreshTime;

    public EntityCache(Object datas, long timeOut, long lastRefreshTime) {
        this.datas = datas;
        this.timeOut = timeOut;
        this.lastRefreshTime = lastRefreshTime;
    }

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public long getLastRefreshTime() {
        return lastRefreshTime;
    }

    public void setLastRefreshTime(long lastRefreshTime) {
        this.lastRefreshTime = lastRefreshTime;
    }
}
