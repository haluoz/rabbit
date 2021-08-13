package com.lxj.rabbit.producer.broker;

import com.lxj.rabbit.api.Message;

import java.util.List;

/**
 * 具体发送不同消息的接口
 * @author Xingjing.Li
 * @since 2021/8/12
 */
public interface RabbitBroker {
    void rapidSend(Message message);
    void confirmSend(Message message);
    void relianceSend(Message message);
    void sendMessages();
}
