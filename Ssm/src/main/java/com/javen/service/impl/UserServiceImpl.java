package com.javen.service.impl;

import com.javen.dao.IUserDao;
import com.javen.model.User;
import com.javen.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 缓存机制说明：所有的查询结果都放进了缓存，也就是把MySQL查询的结果放到了redis中去，
 * 然后第二次发起该条查询时就可以从redis中去读取查询的结果，从而不与MySQL交互，从而达到优化的效果，
 * redis的查询速度之于MySQL的查询速度相当于 内存读写速度 /硬盘读写速度
 * value：缓存位置名称，不能为空，同上
 *
 * key：缓存的key，默认为空，同上
 *
 * condition：触发条件，只有满足条件的情况才会清除缓存，默认为空，支持SpEL
 *
 * allEntries：true表示清除value中的全部缓存，默认为false
 */
@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    /**
     * 用于查询的注解，第一次查询的时候返回该方法返回值，并向redis服务器保存数据，
     * 以后的查询将不再执行方法体内的代码，而是直接查询redis服务器获取数据并返回。
     * value属性做键，key属性则可以看作为value的子键，
     * 一个value可以有多个key组成不同值存在redis服务器，
     * 这里再介绍一个属性是condition，用法condition="#key<10"，
     * 就是说当key小于10的时候才将数据保存到redis服务器
     * @param user
     * @return
     */
    //标注该方法的查询结果进入缓存，再次访问直接读取缓存中的数据
    @Cacheable(value = "user",key = "#user.id")
    @Override
    public User getUser(User user) {
        return userDao.getByUser(user);
    }

    //清空缓存
    @CacheEvict(value = "user",allEntries = true)
    @Override
    public void saveUser(User user) {
        userDao.insert(user);
    }

    //清空缓存
    @CacheEvict(value = "user",allEntries = true)
    @Override
    public void insertUserBatch(List<User> list) {
        userDao.insertUserBatch(list);
    }

    //清空缓存
    @CacheEvict(value = "user",allEntries = true)
    @Override
    public void deleteUser(int userId) {
        userDao.deleteByUserId(userId);
    }

    //清空缓存
    @CacheEvict(value = "user",allEntries = true)
    @Override
    public void updateUser(User user) {
        userDao.updateByUserId(user);
    }

}  
