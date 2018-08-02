package com.hang.ramcache.persist;

import com.hang.ramcache.IEntity;
import com.hang.ramcache.orm.Accessor;

import java.util.Map;

public interface Persister {
    void initialize(String var1, Accessor var2, String var3);

    void put(Element var1);

    void addListener(Class<? extends IEntity> var1, Listener var2);

    Listener getListener(Class<? extends IEntity> var1);

    void shutdown();

    Map<String,String> getInfo();
}
