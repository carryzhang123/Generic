package com.javen.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;

/**
 * @Author zhanghang
 * @Date 2018/8/30 上午10:54
 **/
public class DirectConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        MessageProperties m = message.getMessageProperties();
        String msg = new String(message.getBody());
        System.out.println("消费掉了:" + msg + "------->>>>>");
    }
}
