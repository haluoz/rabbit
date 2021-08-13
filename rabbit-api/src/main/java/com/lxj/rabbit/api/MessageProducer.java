package com.lxj.rabbit.api;

import java.util.List;

/**
 * 消息生产者
 * @author Xingjing.Li
 * @since 2021/8/3
 */
public interface MessageProducer {
    /**
     * 消息的发送，附带回调执行业务处理逻辑
     * @param message
     * @param sendCallback
     */
    void send(Message message, SendCallback sendCallback);

    /**
     * 消息的发送
     * @param message
     */
    void send(Message message);

    /**
     * 消息批量发送
     * @param messages
     */
    void send(List<Message> messages);

}
