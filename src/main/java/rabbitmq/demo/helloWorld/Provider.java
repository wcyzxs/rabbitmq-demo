package rabbitmq.demo.helloWorld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;
import rabbitmq.demo.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName
 * @Description 生产者
 * 点对点模式
 * @Autor wcy
 * @Date 2020/10/27 15:20
 */
public class Provider {

    @Test
    public void testSend() throws IOException, TimeoutException {
        //通过工具类获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        //获取连接中得通道
        Channel channel = connection.createChannel();

        //通道绑定对应得消息队列
        // 参数1：队列名称 不存在的话则自动创建
        // 参数2: 是否持久化 （重启后队列是否存在）
        // 参数3:是否独占队列 (是否只允许当前链接可用该队列)
        // 参数4:是否自动删除   ( 当消费者消费队列中得最后一条信息后，并终断消费者服务后，消息队列会被删除)
        // 参数5:其他属性 对队列的额外配置
        channel.queueDeclare("hello mq", true, false, false, null);

        //发布消息
        // 参数1: 交换机名称
        // 参数2: 队列名称
       //  参数3: 发布消息时得额外设置 (设置消息的持久化，否则尽管队列设置持久化了，那么重启后队列虽然仍然存在，但是消息会丢失)
        // 参数4: 发布消息得具体内容
        channel.basicPublish("","hello mq", MessageProperties.PERSISTENT_TEXT_PLAIN, "welcome rabbitmq ".getBytes());

       RabbitMQUtils.close(channel, connection);
    }
}