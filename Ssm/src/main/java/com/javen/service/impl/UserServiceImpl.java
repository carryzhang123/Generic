package com.javen.service.impl;

import com.javen.dao.IUserDao;
import com.javen.model.User;
import com.javen.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    @Override
    public User getUser(User user) {
        return userDao.getByUser(user);
    }

    @Override
    public void saveUser(User user) {
        userDao.insert(user);
    }

    @Override
    public void insertUserBatch(List<User> list) {
        userDao.insertUserBatch(list);
    }

    @Override
    public void deleteUser(int userId) {
        userDao.deleteByUserId(userId);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateByUserId(user);
    }

}  
