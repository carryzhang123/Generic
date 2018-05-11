package com.hang.logfiltertools.data;


import com.hang.logfiltertools.data.impl.DataWriteImpl;
import com.hang.logfiltertools.excel.ExcelUtil;
import com.hang.logfiltertools.http.HttpUtils;
import com.hang.logfiltertools.http.impl.LogNotify;
import com.hang.logfiltertools.log.ILogAnalysis;
import com.hang.logfiltertools.log.ILogFilter;
import com.hang.logfiltertools.log.impl.ILogMerge;
import com.hang.logfiltertools.tools.TimeUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author ZhangHang
 * @create 2018-05-08 9:42
 **/
public class GenerateData {
    private String beginDate;
    private String endDate;
    private String log;
    private boolean formMerge = true;
    private List<String> serverIds;
    private List<String> cellNameList;

    private String tempurl = "http://%s.37wan.tt2.5ooq.com:8888/log/37wan/admin/%s/%s/%s";
    private final String basePath = "F:/log/";
    private Map<String, Workbook> workbooks;
    //    private CellStyle cellStyle;
    private Map<String, String> fileNames;
    private Map<String, String> urls;

    private IDataWrite dataWrite;
    private ILogFilter logFilter;
    private ILogMerge logMerge;

    private CountDownLatch countDownLatchBegin;
    private CountDownLatch countDownLatchEnd;

    public void generate() throws Exception {
        //创建Excel
        createExcel();
        //生成Urls
        generateUrls();
        //初始化数据
        countDownLatchEnd = new CountDownLatch(urls.size());
        dataWrite = new DataWriteImpl(workbooks, cellNameList, countDownLatchEnd, fileNames, logMerge);

        countDownLatchBegin = new CountDownLatch(1);

        for (Map.Entry<String, String> urlEntry : urls.entrySet()) {
            final String serverId = urlEntry.getKey().split("-")[0];
            String url = urlEntry.getValue();

            LogNotify logNotify = new LogNotify(url, "GET", null);
            logNotify.setLogAnalysis(new ILogAnalysis() {
                @Override
                public void analysis(String result) throws Exception {
                    countDownLatchBegin.await();

                    if (result == null || "".equals(result)) {
                        countDownLatchEnd.countDown();
                        return;
                    }

                    String[] res = result.split("\r\n");
                    final List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
                    for (String curRes : res) {
                        Map<String, String> filterMap = analysisResult(curRes);
                        if (filterMap != null) {
                            mapList.add(filterMap);
                        }
                    }

                    HttpUtils.addWriteTask(new Runnable() {
                        @Override
                        public void run() {
                            dataWrite.offerData(mapList, serverId);
                        }
                    });

                    countDownLatchEnd.countDown();
                }
            });
            HttpUtils.addTask(logNotify.hashCode(), logNotify);
        }
        countDownLatchBegin.countDown();
        countDownLatchEnd.await();

        HttpUtils.addWriteTask(new Runnable() {
            @Override
            public void run() {
                try {
                    dataWrite.ultimateData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 设置日志中需要的字段
     *
     * @param tmpCellNames
     */
    public void setExcelHead(String... tmpCellNames) {
        String[] cellNames = new String[tmpCellNames.length + 1];
        cellNames[0] = "serverId";
        System.arraycopy(tmpCellNames, 0, cellNames, 1, tmpCellNames.length);
        cellNameList = Arrays.asList(cellNames);
        if (workbooks == null) {
            workbooks = new HashMap<String, Workbook>();
        }
        if (formMerge) {
            workbooks.put("-1", ExcelUtil.makeNewExcel(cellNames));
            return;
        }
        for (String serverid : serverIds) {
            workbooks.put(serverid, ExcelUtil.makeNewExcel(cellNames));
        }
    }

    /**
     * 分析数据
     *
     * @param result
     */
    private Map<String, String> analysisResult(String result) {
        String[] strs = result.split("\u0001");
        final Map<String, String> filterMap = new HashMap<String, String>();
        for (String str : strs) {
            String[] pars = str.split(":");
            if (cellNameList.contains(pars[0])) {
                if (logFilter != null && !logFilter.filter(pars[0], pars[1])) {
                    return null;
                }

                if (cellNameList.contains("time") && "time".equals(pars[0])) {
                    filterMap.put(pars[0], TimeUtils.longTimeFormate(Long.parseLong(pars[1])));
                } else {
                    filterMap.put(pars[0], pars[1]);
                }
            }
        }
        return filterMap;
    }


    /**
     * 生成Excel格式
     */
    private void createExcel() throws Exception {
        if (fileNames == null) {
            fileNames = new HashMap<String, String>();
        }

        if (formMerge) {
            fileNames.put("-1", basePath + TimeUtils.fileNameByNowDate());
            return;
        }

        for (String serverid : serverIds) {
            fileNames.put(serverid, basePath + TimeUtils.fileNameByNowDate());
        }
    }

    /**
     * 生成地址
     *
     * @throws Exception
     */
    private void generateUrls() throws Exception {
        urls = new HashMap<String, String>();
        Calendar startCalendar = TimeUtils.getCalendar(beginDate);
        Calendar endCalendar = TimeUtils.getCalendar(endDate);
        for (String serverId : serverIds) {
            for (Calendar calendar = startCalendar; calendar.getTimeInMillis() <= endCalendar.getTimeInMillis(); calendar = TimeUtils.nextCalendar(calendar)) {
                String time = TimeUtils.urlTimeFormate(calendar.getTimeInMillis());
                String url = String.format(tempurl, "s" + serverId, "s" + serverId, time, log);
                urls.put(serverId + "-" + time, url);
            }
        }
    }

    /**
     * 设置获取数据的服务器
     *
     * @param serverids
     */
    public void setServerId(String... serverids) {
        serverIds = new ArrayList<String>();
        for (String serverId : serverids) {
            String[] strs = serverId.split("-");
            if (strs.length == 1) {
                serverIds.add(strs[0]);
            } else {
                for (int i = Integer.valueOf(strs[0]); i <= Integer.valueOf(strs[1]); i++) {
                    serverIds.add(String.valueOf(i));
                }
            }
        }
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public void setLogFilter(ILogFilter logFilter) {
        this.logFilter = logFilter;
    }

    public void setLogMerge(ILogMerge logMerge) {
        this.logMerge = logMerge;
    }

    public void setFormMerge(boolean formMerge) {
        this.formMerge = formMerge;
    }
}
