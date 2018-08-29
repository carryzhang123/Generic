package com.javen.service;  

import com.javen.model.User;

import java.util.List;


public interface IUserService {  
     User getUser(User user);

     void saveUser(User user);

     void insertUserBatch(List<User> list);

     void deleteUser(int userId);

     void updateUser(User user);
}  