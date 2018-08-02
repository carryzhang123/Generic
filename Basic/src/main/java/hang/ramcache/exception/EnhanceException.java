package hang.ramcache.exception;

import com.hang.ramcache.IEntity;

/**
 * @author ZhangHang
 * @create 2018-01-26 21:27
 **/
public class EnhanceException extends CacheException{

    private static final long serialVersionUID = 4160729653142280121L;
    private final IEntity entity;

    public EnhanceException(IEntity entity){
        this.entity=entity;
    }

    public EnhanceException(IEntity entity,String message,Throwable cause){
        super(message,cause);
        this.entity=entity;
    }

    public EnhanceException(IEntity entity,String message){
        super(message);
        this.entity=entity;
    }

    public EnhanceException(IEntity entity,Throwable cause){
        super(cause);
        this.entity=entity;
    }

    public IEntity getEntity() {
        return this.entity;
    }
}
