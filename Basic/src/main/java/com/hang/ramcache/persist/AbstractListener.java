package com.hang.ramcache.persist;

import com.hang.ramcache.IEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author ZhangHang
 * @create 2018-01-29 11:29
 **/
public abstract class AbstractListener implements Listener {
    private static final Logger logger = LoggerFactory.getLogger(AbstractListener.class);

    public AbstractListener() {
    }

    public void notify(EventType type, boolean isSuccess, Serializable id, IEntity entity, RuntimeException ex) {
        try {
            if (isSuccess) {
                switch (type) {
                    case SAVE:
                        this.onSaveSuccess(id, entity);
                        break;
                    case REMOVE:
                        this.onRemoveSuccess(id);
                        break;
                    case UPDATE:
                        this.onUpdateSuccess(entity);
                        break;
                    default:
                        logger.error("未支持的更新事件类型[{}]", type);
                }
            } else {
                switch (type) {
                    case SAVE:
                        this.onSaveError(id, entity, ex);
                        break;
                    case UPDATE:
                        this.onUpdateError(entity, ex);
                        break;
                    case REMOVE:
                        this.onRemoveError(id, ex);
                        break;
                    default:
                        logger.error("未支持的更新事件类型[{}]", type);
                }
            }
        } catch (Exception var7) {
            logger.error("队列监听器[{}]处理出现异常", new Object[]{this.getClass().getName(), var7});
        }
    }

    protected void onSaveSuccess(Serializable id, IEntity entity) {
    }

    protected void onUpdateSuccess(IEntity entity) {
    }

    protected void onRemoveSuccess(Serializable id) {
    }

    protected void onSaveError(Serializable id, IEntity entity, RuntimeException ex) {
    }

    protected void onUpdateError(IEntity entity, RuntimeException ex) {
    }

    protected void onRemoveError(Serializable id, RuntimeException ex) {
    }
}
