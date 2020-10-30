package rabbitmq.demo.fanout;

import com.rabbitmq.client.*;
import rabbitmq.demo.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @ClassName
 * @Description
 * @Autor wcy
 * @Date 2020/10/29 10:19
 */
public class Consumer {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        //通道绑定交换机
        channel.exchangeDeclare("logs", "fanout");
        //创建临时队列
        String queue = channel.queueDeclare().getQueue();
        //参数分别是： queue,  exchange,  routingKey
        channel.queueBind(queue, "logs", "");

        channel.basicConsume(queue, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1：广播模型获取数据-->"+new String(body));
            }
        });
    }
}