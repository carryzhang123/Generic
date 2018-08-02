package com.hang.ramcache.service;

import com.hang.ramcache.IEntity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public interface CacheFinder<PK extends Comparable<PK> & Serializable, T extends IEntity<PK>> {
    Set<T> find(Filter<T> var1);

    List<T> sort(Comparator<T> var1);

    List<T> find(Filter<T> var1, Comparator<T> var2);

    Set<T> all();

    int getAllSize();
}
