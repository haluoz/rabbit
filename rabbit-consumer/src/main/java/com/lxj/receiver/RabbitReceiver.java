package com.lxj.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Xingjing.Li
 * @since 2021/7/30
 */
@Component
public class RabbitReceiver {
    @RabbitHandler
    @RabbitListener( bindings = { @QueueBinding(
                    value = @Queue(value = "queue-1", durable = "true"),
                    exchange = @Exchange(name = "exchange-1", durable = "true", type = "topic", ignoreDeclarationExceptions = "true"),
                    key = {"springboot.*"})}
    )
    public void onMessage(Message message, Channel channel) throws IOException {
        // 业务消费处理
        System.out.println("------消费消息-------");
        System.out.println(message.getPayload());
        //手动处理成功之后 执行ack想要
        channel.basicAck((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG),false);
    }
}
