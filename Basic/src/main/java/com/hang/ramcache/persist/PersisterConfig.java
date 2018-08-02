package com.hang.ramcache.persist;

/**
 * @author ZhangHang
 * @create 2018-01-29 11:51
 **/
public class PersisterConfig {
    private final PersistType type;
    private final String value;

    public PersisterConfig(PersistType type, String value) {
        this.type = type;
        this.value = value;
    }

    public PersistType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
