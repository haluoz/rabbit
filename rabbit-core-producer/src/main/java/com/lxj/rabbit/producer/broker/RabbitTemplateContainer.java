package com.lxj.rabbit.producer.broker;

import com.google.common.base.Splitter;
import com.lxj.rabbit.api.Message;
import com.lxj.rabbit.api.MessageType;
import com.lxj.rabbit.common.convert.GenericMessageConverter;
import com.lxj.rabbit.common.convert.RabbitMessageConverter;
import com.lxj.rabbit.common.serializer.Serializer;
import com.lxj.rabbit.common.serializer.SerializerFactory;
import com.lxj.rabbit.common.serializer.impl.JacksonSerializerFactory;
import com.lxj.rabbit.producer.service.MessageStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * rabbit池化操作
 * 每一个topic对应一个rabbit template
 *  ->提高发送效率
 *  ->根据不同需求定制不同的rabbit template，每一个topic都有自己的routing key
 * @author Xingjing.Li
 * @since 2021/8/13
 */
@Component
@Slf4j
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback {

    private Map<String, RabbitTemplate> rabbitMap = new ConcurrentHashMap<>();
    private Splitter splitter = Splitter.on("#");

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private MessageStoreService messageStoreService;

    private SerializerFactory factory = JacksonSerializerFactory.INSTANCE;

    public RabbitTemplate getRabbitTemplate(Message message) throws NullPointerException {
        if (message == null){
            throw new NullPointerException("message is null");
        }
        String topic = message.getTopic();
        RabbitTemplate rabbitTemplate = rabbitMap.get(topic);
        if (rabbitTemplate != null){
            return rabbitTemplate;
        }
        log.info("topic {} not exist, creating", topic);
        RabbitTemplate newRabbitTemplate = new RabbitTemplate(connectionFactory);
        newRabbitTemplate.setExchange(message.getTopic());
        newRabbitTemplate.setRetryTemplate(new RetryTemplate());
        newRabbitTemplate.setRoutingKey(message.getRoutingKey());
        //message序列化方式
        Serializer serializer = factory.create();
        GenericMessageConverter genericMessageConverter = new GenericMessageConverter(serializer);
        RabbitMessageConverter rabbitMessageConverter = new RabbitMessageConverter(genericMessageConverter);
        newRabbitTemplate.setMessageConverter(rabbitMessageConverter);
        String messageType = message.getMessageType();
        if (!MessageType.RAPID.equals(messageType)){
            newRabbitTemplate.setConfirmCallback(this);
        }
        rabbitMap.putIfAbsent(message.getTopic(), newRabbitTemplate);
        return newRabbitTemplate;
    }

    /**
     * 无论是confirm消息还是reliance消息，发送消息后broker都会去回调confirm方法
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        List<String> strings = splitter.splitToList(correlationData.getId());
        String messageId = strings.get(0);
        Long sendTime = Long.valueOf(strings.get(1));
        String messageType = strings.get(2);
        if (ack){
            if (MessageType.RELIANCE.endsWith(messageType)) {
                //更新消息发送状态
                messageStoreService.success(messageId);
            }
            log.info("send message is ok, confirm message id {}, send time is {}", messageId, sendTime);
        }else {
            //消息补偿
            log.error("send message is fail, confirm message id {}, send time is {}", messageId, sendTime);
        }
    }
}
