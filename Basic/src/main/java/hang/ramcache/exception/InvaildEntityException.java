package hang.ramcache.exception;

/**
 * @author ZhangHang
 * @create 2018-01-26 21:30
 **/
public class InvaildEntityException extends CacheException {
    private static final long serialVersionUID = 4472940368871692110L;

    public InvaildEntityException(){}
    public InvaildEntityException(String message,Throwable cause){
        super(message,cause);
    }

    public InvaildEntityException(String message){
        super(message);
    }

    public InvaildEntityException(Throwable cause){
        super(cause);
    }
}
