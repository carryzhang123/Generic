package com.hang.ramcache.orm;

/**
 * @author ZhangHang
 * @create 2018-01-27 16:49
 **/
public class Paging {
    private final int page;
    private final int size;

    public static Paging valueOf(int page, int size) {
        return new Paging(page, size);
    }

    public Paging(int page, int size) {
        if (page > 0 && size > 0) {
            this.page = page;
            this.size = size;
        } else {
            throw new IllegalArgumentException("页码或页容量必须是大于或等于1的正整数");
        }
    }

    public int getFirst() {
        return this.size * this.page - this.size;
    }

    public int getLast() {
        return this.size * this.page;
    }

    public int getPage() {
        return this.page;
    }

    public int getSize() {
        return this.size;
    }
}
