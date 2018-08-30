package com.javen.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ZhangHang
 * @create 2018-08-29 22:33
 **/
@Controller
@RequestMapping("/rabbit")
public class RabbitController {

    @Autowired
    private AmqpTemplate amqpTemplate;


    @RequestMapping("/sendMsg")
    @ResponseBody
    public String sendRabbitMsg(@RequestParam(value = "msg", defaultValue = "this is topic 1 !!!") String msg) {
        amqpTemplate.convertAndSend("rabbit.topicExchange", "rabbit.topic1.send", msg);
        return "success";
    }

    @RequestMapping("/sendMsg2")
    @ResponseBody
    public String sendAmqbMsg2(Model model, @RequestParam(value = "msg", defaultValue = "this is topic 2 !!!") String msg) {
        amqpTemplate.convertAndSend("rabbit.topicExchange", "rabbit.topic2.send", msg);
        return "success";
    }

    @RequestMapping("/sendMsg3")
    @ResponseBody
    public String sendAmqbMsg3(Model model, @RequestParam(value = "msg", defaultValue = "this is direct !!!") String msg) {
        amqpTemplate.convertAndSend("rabbit.directExchange", "rabbit.direct.send", msg);
        return "success";
    }


}
