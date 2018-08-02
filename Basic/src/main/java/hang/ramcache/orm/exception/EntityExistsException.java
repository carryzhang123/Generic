package hang.ramcache.orm.exception;

/**
 * @author ZhangHang
 * @create 2018-01-27 16:34
 **/
public class EntityExistsException extends OrmException {
    private static final long serialVersionUID = 1152675465310479717L;

    public EntityExistsException() {
    }

    public EntityExistsException(String message) {
        super(message);
    }

    public EntityExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityExistsException(Throwable cause) {
        super(cause);
    }
}
