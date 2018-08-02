package com.hang.ramcache.exception;

/**
 * @author ZhangHang
 * @create 2018-01-26 21:31
 **/
public class UniqueFieldException extends CacheException {
    private static final long serialVersionUID = -2631517848335473304L;

    public UniqueFieldException(){

    }
    public UniqueFieldException(String message,Throwable cause){
        super(message,cause);
    }

    public UniqueFieldException(String message){
        super(message);
    }

    public UniqueFieldException(Throwable cause){
        super(cause);
    }
}
