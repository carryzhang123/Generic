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
@RequestMapping("/amqpTest")
public class AmqpTestController {

    @Autowired
    private AmqpTemplate amqpTemplate;


    @RequestMapping("/sendMsg")
    @ResponseBody
    public String sendAmqbMsg(Model model,@RequestParam(value="msg",defaultValue="hello world!!!")String msg){
        if(model!=null&&!"".equals(msg)){
            amqpTemplate.convertAndSend("mq.asdfExChange", "mq.asdf.send", msg);
        }else{
            amqpTemplate.convertAndSend("mq.asdfExChange", "mq.asdf.send", "hello world");
        }
        return "success";
    }
    @RequestMapping("/sendMsg2")
    @ResponseBody
    public String sendAmqbMsg2(Model model, @RequestParam(value="msg",defaultValue="hello world!!!")String msg){
        if(model!=null&&!"".equals(msg)){
            amqpTemplate.convertAndSend("mq.asdfExChange", "mq.asdf2.send", "这个世界很奇妙!!!");
        }else{
            amqpTemplate.convertAndSend("mq.asdfExChange", "mq.asdf2.send", "这个世界很奇妙");
        }
        return "success";
    }
    @RequestMapping("/sendMsg3")
    @ResponseBody
    public String sendAmqbMsg3(Model model,@RequestParam(value="msg",defaultValue="hello world!!!")String msg){
        if(model!=null&&!"".equals(msg)){
            amqpTemplate.convertAndSend("mq.qwerExChange", "mq.qwer.send", "神奇的世界!!!");
        }else{
            amqpTemplate.convertAndSend("mq.qwerExChange", "mq.qwer.send", "神奇的世界");
        }
        return "success";
    }





}
