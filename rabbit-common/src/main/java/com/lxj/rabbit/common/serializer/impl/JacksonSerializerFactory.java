package com.lxj.rabbit.common.serializer.impl;

import com.lxj.rabbit.api.Message;
import com.lxj.rabbit.common.serializer.Serializer;
import com.lxj.rabbit.common.serializer.SerializerFactory;

/**
 * @author Xingjing.Li
 * @since 2021/8/13
 */
public class JacksonSerializerFactory implements SerializerFactory {

    public final static JacksonSerializerFactory INSTANCE = new JacksonSerializerFactory();

    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }
}
