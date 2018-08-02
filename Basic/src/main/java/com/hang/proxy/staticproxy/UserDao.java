package com.hang.proxy.staticproxy;

/**
 * @author ZhangHang
 * @create 2018-07-08 15:24
 **/

/**
 * 目标对象
 */
public class UserDao implements IUserDao {
    @Override
    public void save() {
        System.out.println("---已经保存数据---");
    }
}
