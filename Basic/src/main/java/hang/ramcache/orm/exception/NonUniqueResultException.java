package hang.ramcache.orm.exception;

/**
 * @author ZhangHang
 * @create 2018-01-27 16:36
 **/
public class NonUniqueResultException extends OrmException {
    private static final long serialVersionUID = 6866933713292336309L;

    public NonUniqueResultException() {
    }

    public NonUniqueResultException(String message) {
        super(message);
    }

    public NonUniqueResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonUniqueResultException(Throwable cause) {
        super(cause);
    }
}
