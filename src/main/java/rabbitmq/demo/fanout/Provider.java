package rabbitmq.demo.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import rabbitmq.demo.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @ClassName
 * @Description 广播模型
 * 可以有多个消费者，每个消费者都有自己临时的queue,每个队列都要绑定到exchange。
 * 生产者发送的消息，只能发送到交换机，由交换机决定发送，发送给绑定过的所有队列
 * 消费者都能拿到消息，实现了一条消息被多个消费者消费
 * @Autor wcy
 * @Date 2020/10/28 14:42
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        //通道声明指定交换机    参数1： 交换机名称       参数2：交换机类型
        channel.exchangeDeclare("logs", "fanout");

        //参数1： exchange  交换机名称  参数2：routingKey 路由key
        channel.basicPublish("logs", "", MessageProperties.PERSISTENT_TEXT_PLAIN, "fanout type message".getBytes());

        //释放资源
        RabbitMQUtils.close(channel, connection);
    }
}