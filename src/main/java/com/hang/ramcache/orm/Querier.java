package com.hang.ramcache.orm;

import java.util.List;

/**
 * @author ZhangHang
 * @create 2018-01-27 16:40
 **/
public interface Querier {
    <T> List<T> all(Class<T> var1);

    <T> List<T> list(Class<T> var1, String var2, Object... var3);

    <E> List<E> list(Class var1, Class<E> var2, String var3, Object... var4);

    <T> T unique(Class<T> var1, String var2, Object... var3);

    <E> E unique(Class var1, Class<E> var2, String var3, Object... var4);

    <T> List<T> paging(Class<T> var1, String var2, Paging var3, Object... var4);

    <E> List<E> pagint(Class var1, Class<E> var2, String var3, Paging var4, Object... var5);


}
