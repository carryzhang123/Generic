package com.hang.pulllog.log.impl;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public interface ILogMerge {
    void merge(Map<String, String> dataMap, LinkedBlockingQueue<Map<String, String>> linkedQueue);
}
