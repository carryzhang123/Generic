package com.javen;

import com.javen.model.User;
import com.javen.service.IUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangHang
 * @create 2018-08-14 22:40
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationcontext.xml", "classpath*:/spring-mvc.xml",
        "classpath*:/spring-mybatis.xml"})
public class BaseJunitTest {

    @Autowired
    private IUserService userService;

    private long i;

    @Before
    public void setBeginTime() {
        i = System.currentTimeMillis();
    }

    @Test
    public void insertUserBatch() throws Exception {
        List<User> list = new ArrayList<>();
        for (Integer i = 350000; i < 400000; i++) {
            User user = new User();
            user.setId(i);
            user.setUserName("hang"+i);
            user.setPassword("root123456");
            user.setAddress("上海");
            user.setCareer("student"+i);
            user.setTelephone("15507314418");
            user.setAge(22);
            list.add(user);
        }
        userService.insertUserBatch(list);
        System.out.println(list.size());
    }

    @Test
    public void getUser() {
        User user = new User();
        user.setId(222);
        User result=userService.getUser(user);
        System.out.println(result.getId());
    }

    @After
    public void endTime() {
        System.out.println("consume time:" + (System.currentTimeMillis() - i));
    }

}
