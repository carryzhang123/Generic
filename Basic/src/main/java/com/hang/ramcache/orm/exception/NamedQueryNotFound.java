package com.hang.ramcache.orm.exception;

/**
 * @author ZhangHang
 * @create 2018-01-27 16:35
 **/
public class NamedQueryNotFound extends OrmException {
    private static final long serialVersionUID = 5000626770200551199L;

    public NamedQueryNotFound() {
    }

    public NamedQueryNotFound(String message) {
        super(message);
    }

    public NamedQueryNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public NamedQueryNotFound(Throwable cause) {
        super(cause);
    }
}
