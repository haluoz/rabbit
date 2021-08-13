package com.lxj.rabbit.api;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xingjing.Li
 * @since 2021/8/3
 */
@Getter
@Setter
public class Message implements Serializable {

    private static final long serialVersionUID = -472041032891159547L;
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

    public Message() {
    }

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delay, String messageType) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delay = delay;
        this.messageType = messageType;
    }

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delay) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delay = delay;
    }
}
