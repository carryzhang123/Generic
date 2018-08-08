package com.javen.dao;

import com.javen.mapper.UserMapper;
import com.javen.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ZhangHang
 * @create 2018-08-08 1:15
 **/
public class IUserDao {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public User selectByPrimaryKey(int userId){
        SqlSession sqlSession=sqlSessionFactory.openSession();

        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);

        return userMapper.selectByPrimaryKey(userId);
    }
}
