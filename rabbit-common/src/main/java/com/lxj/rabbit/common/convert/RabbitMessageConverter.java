package com.lxj.rabbit.common.convert;

import com.google.common.base.Preconditions;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 *
 * @author Xingjing.Li
 * @since 2021/8/13
 */
public class RabbitMessageConverter implements MessageConverter {

    private GenericMessageConverter delegate;

    private final String defaultExpire = String.valueOf(24*60*60*1000);

    public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
        Preconditions.checkNotNull(genericMessageConverter);
        this.delegate = genericMessageConverter;
    }

    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        messageProperties.setExpiration(defaultExpire);
        return delegate.toMessage(o, messageProperties);
    }

    public Object fromMessage(Message message) throws MessageConversionException {
        com.lxj.rabbit.api.Message msg = (com.lxj.rabbit.api.Message) delegate.fromMessage(message);
        return msg;
    }
}
