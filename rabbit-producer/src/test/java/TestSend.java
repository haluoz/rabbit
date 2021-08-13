import com.lxj.ProducerApplication;
import com.lxj.sender.RabbitSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * @author Xingjing.Li
 * @since 2021/7/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProducerApplication.class)
public class TestSend {
    @Autowired
    private RabbitSender rabbitSender;

    @Test
    public void testSender(){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("attr1", "123");
        map.put("attr2", "456");
        rabbitSender.send("Hello World!", map);
    }
}
