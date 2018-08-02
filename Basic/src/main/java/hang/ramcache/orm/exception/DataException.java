package hang.ramcache.orm.exception;

/**
 * @author ZhangHang
 * @create 2018-01-27 16:34
 **/
public class DataException extends OrmException {
    private static final long serialVersionUID = 6882397842658638791L;

    public DataException() {
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(Throwable cause) {
        super(cause);
    }
}
