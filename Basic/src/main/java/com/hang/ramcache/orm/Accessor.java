package com.hang.ramcache.orm;

import com.hang.ramcache.IEntity;

import java.io.Serializable;
import java.util.List;

public interface Accessor {
    <PK extends Serializable, T extends IEntity> T load(Class<T> var1, PK var2);

    <PK extends Serializable, T extends IEntity> PK save(Class<T> var1, T var2);

    <PK extends Serializable, T extends IEntity> void remove(Class<T> var1, PK var2);

    <PK extends Serializable, T extends IEntity> void update(Class<T> var1, T var2);

    <PK extends Serializable, T extends IEntity> void saveOrUpdate(Class<T> var1, T var2);

    <PK extends Serializable, T extends IEntity> void batchSave(List<T> var1);

    <PK extends Serializable, T extends IEntity> void batchUpdate(List<T> var1);
}
