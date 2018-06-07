package com.hang.pulllog.http;

public interface CallbackService {
    void addListener(String key, CallbackListener listener);
}
