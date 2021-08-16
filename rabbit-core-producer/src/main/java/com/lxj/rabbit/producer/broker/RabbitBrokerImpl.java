package com.lxj.rabbit.producer.broker;

import com.lxj.rabbit.api.Message;
import com.lxj.rabbit.api.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送不同消息的实现类
 * @author Xingjing.Li
 * @since 2021/8/12
 */
@Component
public class RabbitBrokerImpl implements RabbitBroker {

    private final static Logger log = LoggerFactory.getLogger(RabbitBrokerImpl.class);

    @Autowired
    private RabbitTemplateContainer rabbitTemplateContainer;



    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);
    }

    /**
     * 使用异步线程池发送消息核心方法
     * @param message
     */
    private void sendKernel(Message message) {
        AsyncBaseQueue.submit(() -> {
            CorrelationData correlationData = new CorrelationData(String.format("%s#%s", message.getMessageId(), System.currentTimeMillis()));
            String topic = message.getTopic();
            String routingKey = message.getRoutingKey();
            RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getRabbitTemplate(message);
            rabbitTemplate.convertAndSend(topic,routingKey,message, correlationData);
            log.info("#RabbitBrokerImpl.sendKernel# send message to rabbitmq, message id: {}", message.getMessageId());
        });

    }

    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.CONFIRM);
        sendKernel(message);
    }

    @Override
    public void relianceSend(Message message) {
        message.setMessageType(MessageType.RELIANCE);
        sendKernel(message);
    }

    @Override
    public void sendMessages() {

    }
}
