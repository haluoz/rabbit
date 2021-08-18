package com.lxj.rabbit.test;

import com.lxj.rabbit.api.Message;
import com.lxj.rabbit.api.MessageType;
import com.lxj.rabbit.producer.broker.ProducerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Xingjing.Li
 * @since 2021/8/18
 */
@RestController
public class TestController {

    @Autowired
    private ProducerClient producerClient;

    @GetMapping("/test")
    public void testProducerClient() throws Exception {

        for(int i = 0 ; i < 1; i ++) {
            String uniqueId = UUID.randomUUID().toString();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "张三");
            attributes.put("age", "18");
            Message message = new Message(
                    uniqueId,
                    "exchange-1",
                    "springboot.abc",
                    attributes,
                    0);
            message.setMessageType(MessageType.RELIANCE);
//			message.setDelayMills(15000);
            producerClient.send(message);
        }

        Thread.sleep(100000);
    }
}
