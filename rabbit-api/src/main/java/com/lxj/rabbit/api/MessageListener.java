package com.lxj.rabbit.api;

/**
 * 消费者监听
 * @author Xingjing.Li
 * @since 2021/8/3
 */
public interface MessageListener {
    void onMessage(Message message);
}
