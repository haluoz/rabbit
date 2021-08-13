package com.lxj.rabbit.producer.broker;

import com.google.common.base.Preconditions;
import com.lxj.rabbit.api.Message;
import com.lxj.rabbit.api.MessageProducer;
import com.lxj.rabbit.api.MessageType;
import com.lxj.rabbit.api.SendCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消息发送实现类
 * @author Xingjing.Li
 * @since 2021/8/3
 */
@Component
public class ProducerClient implements MessageProducer {

    @Autowired
    private RabbitBroker rabbitBroker;

    public void send(Message message, SendCallback sendCallback) {

    }

    public void send(Message message) {
        Preconditions.checkNotNull(message.getTopic());
        String messageType = message.getMessageType();
        switch (messageType){
            case MessageType.RAPID:
                rabbitBroker.rapidSend(message);
                break;
            case MessageType.CONFIRM:
                rabbitBroker.confirmSend(message);
                break;
            case MessageType.RELIANCE:
                rabbitBroker.relianceSend(message);
                break;
            default:
                break;
        }
    }

    public void send(List<Message> messages) {

    }
}
