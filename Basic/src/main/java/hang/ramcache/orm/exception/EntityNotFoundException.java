package hang.ramcache.orm.exception;

/**
 * @author ZhangHang
 * @create 2018-01-27 16:35
 **/
public class EntityNotFoundException extends OrmException {
    private static final long serialVersionUID = 8543668521913881519L;

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
