package com.hang.pulllog.log;

public interface ILogFilter {
    /**
     * 分析数据
     * @return
     */
    boolean filter(String key, String value);
}
