import com.lxj.rabbit.api.Message;
import com.lxj.rabbit.api.MessageType;
import com.lxj.rabbit.producer.broker.ProducerClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Xingjing.Li
 * @since 2021/8/18
 */
@SpringBootTest(classes = MessageTest.class)
@RunWith(SpringRunner.class)
public class MessageTest {

    @Autowired
    private ProducerClient producerClient;

    @Test
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
