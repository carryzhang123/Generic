package com.javen.dubbo.impl;

import com.javen.dubbo.DemoService;

/**
 * @Author zhanghang
 * @Date 2018/8/30 下午6:05
 **/
public class DemoServiceImpl implements DemoService {
    @Override
    public String changeUserName(String userName) {
        return "provider  "+userName;
    }
}
