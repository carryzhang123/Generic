package hang.ramcache.orm.exception;

/**
 * @author ZhangHang
 * @create 2018-01-27 16:33
 **/
public class OrmException extends RuntimeException {
    private static final long serialVersionUID = -7350239886905367486L;

    public OrmException() {
    }

    public OrmException(String message) {
        super(message);
    }

    public OrmException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrmException(Throwable cause) {
        super(cause);
    }
}
