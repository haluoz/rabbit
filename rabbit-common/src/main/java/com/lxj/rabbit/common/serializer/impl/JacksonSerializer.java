package com.lxj.rabbit.common.serializer.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lxj.rabbit.common.serializer.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author Xingjing.Li
 * @since 2021/8/13
 */
@Slf4j
public class JacksonSerializer implements Serializer {

    private final static ObjectMapper MAPPER = new ObjectMapper();
    private final JavaType type;

    static {
        MAPPER.disable(SerializationFeature.INDENT_OUTPUT);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    public JacksonSerializer(JavaType type) {
        this.type = type;
    }

    public static JacksonSerializer createParametricType(Class<?> clazz){
        return new JacksonSerializer(MAPPER.getTypeFactory().constructType(clazz));
    }

    public byte[] serializeRaw(Object data) {
        try {
            return MAPPER.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            log.error("序列化错误", e);
        }
        return null;
    }

    public String serialize(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("序列化错误", e);
        }
        return  null;
    }

    public <T> T deserialize(byte[] content) {
        try {
            return MAPPER.readValue(content, type);
        }  catch (IOException e) {
            log.error("反序列化错误", e);
        }
        return  null;
    }

    public <T> T deserialize(String content) {
        try {
            return MAPPER.readValue(content, type);
        } catch (IOException e) {
            log.error("反序列化错误", e);
        }
        return  null;
    }
}
