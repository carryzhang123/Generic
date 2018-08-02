package hang.pulllog;

import com.hang.pulllog.data.GenerateData;
import com.hang.pulllog.log.ILogFilter;
import com.hang.pulllog.log.impl.ILogMerge;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ZhangHang
 * @create 2018-05-08 16:52
 **/
public class TestRun {
    public static void main(String[] args) throws Exception {
        GenerateData generateData = new GenerateData();
        generateData.setBeginDate("2018-05-01");
        generateData.setEndDate("2018-05-03");
        generateData.setLog("recycle.log");
        generateData.setServerId("50003-50004");
        //是否合并
        generateData.setFormMerge(false);
        //选取的字段
        generateData.setExcelHead("time", "account", "level", "exp");
        //数据筛选器
        generateData.setLogFilter(new ILogFilter() {
            @Override
            public boolean filter(String key, String value) {
                if (key.equals("level") && Integer.valueOf(value) <= 20) {
                    return false;
                } else {
                    return true;
                }
            }
        });
        //数据合并
        generateData.setLogMerge(new ILogMerge() {
            @Override
            public void merge(Map<String, String> dataMap, LinkedBlockingQueue<Map<String, String>> linkedQueue) {
                linkedQueue.offer(dataMap);
            }
        });
        generateData.generate();
    }
}
