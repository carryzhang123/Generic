package com.hang.logfiltertools.http.impl;


import com.hang.logfiltertools.http.AbstractHttpHandle;
import com.hang.logfiltertools.http.CallbackListener;
import com.hang.logfiltertools.http.CallbackService;
import com.hang.logfiltertools.http.HttpUtils;
import com.hang.logfiltertools.log.ILogAnalysis;

import java.util.Map;

public class LogNotify extends AbstractHttpHandle implements Runnable, CallbackService {
    private ILogAnalysis logAnalysis;

    public LogNotify(String url, String method, Map<String, String> params) {
        super(url, method, params);
    }

    @Override
    public void notifyMsg(String code, String result) throws Exception {
        if (logAnalysis == null) {
            return;
        }
        logAnalysis.analysis(result);
    }

    @Override
    public void addListener(String key, CallbackListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        String result = HttpUtils.httpRequest(url, method, params);

        try {
            notifyMsg(null, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLogAnalysis(ILogAnalysis logAnalysis) {
        this.logAnalysis = logAnalysis;
    }
}
