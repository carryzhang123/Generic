package com.javen;

import com.alibaba.fastjson.JSON;
import com.javen.model.User;
import com.javen.service.impl.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author ZhangHang
 * @create 2018-08-14 22:40
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationcontext.xml", "classpath*:/spring-mvc.xml",
        "classpath*:/spring-mybatis.xml"})
public class BaseJunitTest {
//    @Autowired
//    private WebApplicationContext wac;

    @Autowired
    private UserServiceImpl userService;

    private long i;
    private MockMvc mockMvc;
//
//    @Before
//    public void setup() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//    }

    @Before
    public void setBeginTime() {
        i = System.currentTimeMillis();
    }

    @Test
    public void testHelloWorld() throws Exception {
        List<User> list = new ArrayList<>();
        for (Integer i = 2; i < 5000; i++) {
            User user = new User();
            user.setId(i);
            user.setUserName(i.toString());
            user.setPassword(i.toString());
            user.setAge(i);
            list.add(user);
        }
        System.out.println(list.size());
        System.out.println(list.get(1000).getPassword());
//        userService.insertUserBatch(list);

//        User user = new User();
//        user.setUserName("DA");
//        user.setPassword("FD");
//        user.setAge(00);
//        userService.saveUser(user);
    }

    @After
    public void endTime() {
        System.out.println(System.currentTimeMillis() - i);
    }

}
