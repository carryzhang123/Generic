package com.hang.ramcache.exception;

/**
 * @author ZhangHang
 * @create 2018-01-22 16:38
 **/
public class CacheException extends RuntimeException {

    private static final long serialVersionUID = -2192025511611240701L;

    public CacheException() {
    }

    public CacheException(String message,Throwable cause){
        super(message,cause);
    }

    public CacheException(String message){
        super(message);
    }

    public CacheException(Throwable cause){
        super(cause);
    }
}
