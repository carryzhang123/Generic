package hang.ramcache.exception;

/**
 * @author ZhangHang
 * @create 2018-01-26 21:24
 **/
public class ConfigurationException extends CacheException {
    private static final long serialVersionUID = 3606183487837109019L;

    public ConfigurationException(){}
    public ConfigurationException(String message,Throwable cause){
        super(message,cause);
    }

    public ConfigurationException(String message){
        super(message);
    }

    public ConfigurationException(Throwable cause){
        super(cause);
    }
}
