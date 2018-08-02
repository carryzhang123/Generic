package hang.ramcache.exception;

/**
 * @author ZhangHang
 * @create 2018-01-22 16:38
 **/
public class StateException extends CacheException {
    private static final long serialVersionUID=3236441373096075879L;

    public StateException() {
    }

    public StateException(String message,Throwable cause){
        super(message,cause);
    }

    public StateException(String message){
        super(message);
    }

    public StateException(Throwable cause){
        super(cause);
    }
}
