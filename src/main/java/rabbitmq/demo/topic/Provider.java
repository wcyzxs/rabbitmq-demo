package rabbitmq.demo.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import rabbitmq.demo.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @ClassName
 * @Description 动态路由-订阅模式
 * 和direct相比，动态路由 routingKey更加方便扩展，不再是写死的了。Topic类型得Exchange可以让队列在绑定Exchange时Routing Key使用通配符！
 * 这种模型Routing key一般由一个或多个单词组成，多个单词以"."分割，如： item.insert
 *  *：匹配正好一个单词   如：audit*    则audit.pass
 *  #: 匹配0或者多个单词  如：audit.#   则aduit.pass.irs 或者audit
 * @Autor wcy
 * @Date 2020/10/29 15:39
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("topics", "topic");

        String routingKey = "audit.save";

        channel.basicPublish("topics", routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, ("动态路由发送消息"+routingKey).getBytes());

        RabbitMQUtils.close(channel, connection);
    }
}