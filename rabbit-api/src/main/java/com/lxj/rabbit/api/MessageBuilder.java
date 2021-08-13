package com.lxj.rabbit.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Xingjing.Li
 * @since 2021/8/3
 */
public class MessageBuilder {
    // 消息唯一id
    private String messageId;
    // 消息主题
    private String topic;
    // 消息路由规则
    private String routingKey = "";
    // 特殊附加属性
    private Map<String, Object> attributes = new HashMap<String, Object>();
    // 延迟消息参数配置
    private int delay;
    // 消息类型 默认为confirm消息
    private String messageType = MessageType.CONFIRM;

    public static MessageBuilder create(){
        return new MessageBuilder();
    }

    public MessageBuilder create(String messageId){
        this.messageId = messageId;
        return this;
    }

    public MessageBuilder createWithMessageId(String messageId){
        this.messageId = messageId;
        return this;
    }

    public MessageBuilder createWithTopic(String topic){
        this.topic = topic;
        return this;
    }

    public MessageBuilder createWithRoutingKey(String routingKey){
        this.routingKey = routingKey;
        return this;
    }

    public MessageBuilder createWithAttributes(Map<String, Object> attributes){
        this.attributes = attributes;
        return this;
    }

    public MessageBuilder createWithAttributes(String key, Object value){
        this.attributes.put(key, value);
        return this;
    }

    public MessageBuilder createWithDelay(int delay){
        this.delay = delay;
        return this;
    }

    public MessageBuilder createWithMessageType(String messageType){
        this.messageType = messageType;
        return this;
    }

    public Message build(){
        if (messageId == null){
            messageId = UUID.randomUUID().toString();
        }
        if (topic == null){
            throw new RuntimeException("topic is null");
        }
        Message message = new Message(messageId, topic, routingKey, attributes, delay, messageType);
        return message;
    }
}
