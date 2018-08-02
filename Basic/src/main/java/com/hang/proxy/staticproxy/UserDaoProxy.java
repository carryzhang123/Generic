package com.hang.proxy.staticproxy;

/**
 * @author ZhangHang
 * @create 2018-07-08 15:25
 **/

/**
 * 代理对象
 */
public class UserDaoProxy implements IUserDao {
    //目标对象
    private IUserDao target;

    public UserDaoProxy(IUserDao target) {
        this.target = target;
    }

    @Override
    public void save() {
        System.out.println("开始事物...");
        //执行目标对象方法
        target.save();
        System.out.println("提交事物...");
    }
}
