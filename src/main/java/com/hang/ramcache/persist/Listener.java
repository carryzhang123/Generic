package com.hang.ramcache.persist;

import com.hang.ramcache.IEntity;

import java.io.Serializable;

/**
 * @author ZhangHang
 * @create 2018-01-29 11:06
 **/
public interface Listener {
    void notify(EventType eventType, boolean flag, Serializable sio, IEntity entity,RuntimeException exception);
}
