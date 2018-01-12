package com.hang.converter;

/**
 * @author ZhangHang
 * @create 2018-01-05 16:37
 **/
public interface Converter<S,T> {
    T converse(S source);
}
