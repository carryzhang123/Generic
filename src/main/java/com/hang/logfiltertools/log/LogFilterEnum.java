package com.hang.logfiltertools.log;

import java.util.HashMap;

/**
 * @author ZhangHang
 * @create 2018-05-09 11:40
 **/
public enum LogFilterEnum {
    BEFORELEVEL(1),;

    private static HashMap<Integer, ILogFilter> LOG_FILTERS = new HashMap<Integer, ILogFilter>(LogFilterEnum.values().length);

    private int id;

    LogFilterEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static HashMap<Integer, ILogFilter> getLogFilters() {
        return LOG_FILTERS;
    }
}
