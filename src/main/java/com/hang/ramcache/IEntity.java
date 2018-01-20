package com.hang.ramcache;

import java.io.Serializable;

/**
 * @author ZhangHang
 * @create 2018-01-20 14:48
 **/
public interface IEntity<PK extends Serializable & Comparable<PK>> {
    PK getId();

    boolean serialize();
}
