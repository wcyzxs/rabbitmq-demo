package rabbitmq.demo.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import rabbitmq.demo.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @ClassName
 * @Description 路由模式之直连
 *这个模式与fanout类似，fanout是发送消息，消费者都能收到
 * 直连模式是生产者向交换机发送消息的时候，指定好路由key。消费者都有自己的临时队列，该队列和交换机绑定的时候，也会有路由key。
 * 消费者收到的消息是生产者指定路由key和消费者的路由key对应上的时候，才会收到信息。
 * 比如生产者发送消息给交换机的时候routingKye是info，然后消费者1的队列绑定交换机时routingKye是info，消费者2的队列绑定交换机时routingKye是error.那么交换机发送消息给队列的时候，只有消费者1才能收到消息
 * @Autor wcy
 * @Date 2020/10/29 11:13
 */
public class Provider {
    public static void main(String[] args) throws IOException {

        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        //参数1： 交换机名称    参数2：路由模式
        channel.exchangeDeclare("logs_direct", "direct");
        String routingKey = "info";
        //发布消息
        channel.basicPublish("logs_direct", routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, ("这是路由之直连模式基于routingKey发送消息:["+routingKey+"]").getBytes());
        RabbitMQUtils.close(channel, connection);
    }
}