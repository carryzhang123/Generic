package hang.ramcache.orm.exception;

/**
 * @author ZhangHang
 * @create 2018-01-27 16:36
 **/
public class TransactionException extends OrmException {
    private static final long serialVersionUID = 2501715990701120931L;

    public TransactionException() {
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }
}
