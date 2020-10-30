package rabbitmq.demo.workQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import rabbitmq.demo.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @ClassName
 * @Description 工作队列模式的生产者
 * 当消息处理比较耗时的时候，可能生产消息的速度远远大于消息的消费速度，长此以往，消息堆积的会越来越多，无法及时处理。
 * 此时就可以使用work模型：让多个消费者绑定到一个队列，共同消费队列中的消息。队列中的消息一旦被消费，就会消失，因此任务是不会被重复执行的。
 *
 * @Autor wcy
 * @Date 2020/10/28 11:30
 */
public class provider {
    public static void main(String[] args) throws IOException {

        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明一个work queue队列
        channel.queueDeclare("work", true, false, false, null);
        //生产消息
        for (int i = 1; i <= 20; i++) {
            channel.basicPublish("", "work", MessageProperties.PERSISTENT_TEXT_PLAIN,(i+"hello work queue").getBytes());
        }
        RabbitMQUtils.close(channel, connection);
    }
}