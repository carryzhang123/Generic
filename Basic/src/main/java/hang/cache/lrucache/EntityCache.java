package hang.cache.lrucache;

/**
 * @author ZhangHang
 * @create 2018-05-31 18:00
 **/
public class EntityCache {
    /**
     * 保存的数据
     */
    private Object datas;

    public EntityCache(Object datas) {
        this.datas = datas;
    }

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }

}
