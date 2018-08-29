package com.javen.dao;

import com.javen.model.User;

import java.util.List;

public interface IUserDao {
    int deleteByUserId(Integer id);

    int insert(User user);

    int insertSelective(User user);

    User getByUser(User user);

    void updateByUserId(User user);

    void insertUserBatch(List<User> list);
}