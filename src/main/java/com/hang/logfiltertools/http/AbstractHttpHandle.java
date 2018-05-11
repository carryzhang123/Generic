package com.hang.logfiltertools.http;

import java.util.Map;

public abstract class AbstractHttpHandle {
    protected String url;
    protected Map<String,String> params;
    protected String method;
    protected  CallbackListener listener;

    public AbstractHttpHandle(String url,String method,Map<String,String> params){
        this.url = url;
        this.method = method;
        this.params = params;
    }
    public abstract void notifyMsg(String code,String result) throws Exception;
}
