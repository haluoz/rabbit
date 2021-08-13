package com.lxj.sender;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @author Xingjing.Li
 * @since 2021/7/30
 */
@Component
public class RabbitSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 确认消息的回调接口，，用于确认broker是否收到
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        /**
         * @param correlationData 唯一标识
         * @param ack 是否成功
         * @param cause 失败的消息
         */
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        }
    };

    public void send(Object message, Map<String, Object> properties){
        MessageHeaders mh = new MessageHeaders(properties);
        Message<Object> msg = MessageBuilder.createMessage(message, mh);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        MessagePostProcessor mpp = new MessagePostProcessor() {
            public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
                System.out.println("---- error -----");
                return message;
            }
        };
        rabbitTemplate.convertAndSend("exchange-1", "springboot.abc", msg, correlationData);
    }
}
