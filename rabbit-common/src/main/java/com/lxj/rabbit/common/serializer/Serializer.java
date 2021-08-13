package com.lxj.rabbit.common.serializer;

/**
 * @author Xingjing.Li
 * @since 2021/8/13
 */
public interface Serializer {

    byte[] serializeRaw(Object data);

    String serialize(Object data);

    <T> T  deserialize(byte[] content);

    <T> T  deserialize(String content);
}
