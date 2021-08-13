package com.lxj.rabbit.api;

/**
 * @author Xingjing.Li
 * @since 2021/8/3
 */
public final class MessageType {
    // 迅速消息 不需要保证消息可靠性，不需要confirm
    public final static String RAPID = "0";
    // 确认消息 不需要保证消息可靠性，需要confirm
    public final static String CONFIRM = "1";
    // 可靠性消息 保证保证消息可靠性 不允许消息丢失
    // 保障数据库和所发消息是原子性（最终一致性）
    public final static String RELIANCE = "2";
}
