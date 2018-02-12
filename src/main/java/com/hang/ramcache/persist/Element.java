package com.hang.ramcache.persist;


import com.hang.ramcache.IEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author ZhangHang
 * @create 2018-01-29 11:11
 **/
public class Element {
    private static final Logger logger = LoggerFactory.getLogger(Element.class);
    private EventType type;
    private final Serializable id;
    private IEntity entity;
    private final Class<? extends IEntity> entityClass;

    public static Element saveOf(IEntity entity) {
        return new Element(EventType.SAVE, entity.getId(), entity, entity.getClass());
    }

    public static Element removeOf(Serializable id, Class<? extends IEntity> entityClass) {
        return new Element(EventType.REMOVE, id, null, entityClass);
    }

    public static Element updateOf(IEntity entity) {
        return new Element(EventType.UPDATE, entity.getId(), entity, entity.getClass());
    }

    private Element(EventType type, Serializable id, IEntity entity, Class<? extends IEntity> entityClass) {
        this.type = type;
        this.id = id;
        this.entity = entity;
        this.entityClass = entityClass;
    }

    public String getIdentity() {
        return this.entityClass.getName() + ":" + this.id;
    }

    public boolean update(Element element) {
        this.entity = element.getEntity();
        switch (this.type) {
            case SAVE:
                switch (element.getType()) {
                    case SAVE:
                        logger.error("更新元素异常，实体[{}]当前状态[{}]不进行修正", new Object[]{this.getIdentity(), this.type, element.getType()});
                        return true;
                    case UPDATE:
                        if (logger.isDebugEnabled()) {
                            logger.debug("实体[{}]原状态[{}]当前状态[{}]修正后状态[{}]是否保留队列元素", new Object[]{this.getIdentity(), EventType.SAVE, element.getType(), this.type, true});
                        }
                        return true;
                    case REMOVE:
                        if (logger.isDebugEnabled()) {
                            logger.debug("实体[{}]原状态[{}]当前状态[{}]是否保留队列元素[{}]", new Object[]{this.getIdentity(), EventType.SAVE, element.getType(), this.type, false});
                        }
                        return false;
                    default:
                        return true;
                }
            case UPDATE:
                switch (element.getType()) {
                    case SAVE:
                        logger.error("更新元素异常，实体[{}]当前状态[{}]不进行修正", new Object[]{this.getIdentity(), this.type, element.getType()});
                        return true;
                    case UPDATE:
                        if (logger.isDebugEnabled()) {
                            logger.debug("实体[{}]原状态[{}]当前状态[{}]修正后状态[{}]是否保留队列元素", new Object[]{this.getIdentity(), EventType.SAVE, element.getType(), this.type, true});
                        }
                        return true;
                    case REMOVE:
                        if (logger.isDebugEnabled()) {
                            logger.debug("实体[{}]原状态[{}]当前状态[{}]是否保留队列元素[{}]", new Object[]{this.getIdentity(), EventType.SAVE, element.getType(), this.type, true});
                        }
                        return true;
                    default:
                        return true;
                }
            case REMOVE:
                switch (element.getType()) {
                    case SAVE:
                        this.type = EventType.UPDATE;
                        if (logger.isDebugEnabled()) {
                            logger.debug("实体[{}]原状态[{}]当前状态[{}]修正后状态[{}]是否保留队列元素", new Object[]{this.getIdentity(), EventType.REMOVE, EventType.SAVE, this.type, true});
                        }
                        break;
                    case UPDATE:
                        logger.error("更新元素异常，实体[{}]当前状态[{}]不进行修正", new Object[]{this.getIdentity(), this.type, element.getType()});
                        break;
                    case REMOVE:
                        logger.error("更新元素异常，实体[{}]当前状态[{}]不进行修正", new Object[]{this.getIdentity(), this.type, element.getType()});
                }
        }
        return true;
    }

    public EventType getType() {
        return type;
    }

    public Serializable getId() {
        return id;
    }

    public IEntity getEntity() {
        return entity;
    }

    public Class<? extends IEntity> getEntityClass(){
        return this.entityClass;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.type).append("ID:").append(this.id);
        return builder.toString();
    }

}
