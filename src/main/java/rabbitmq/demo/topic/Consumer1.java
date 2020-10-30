package rabbitmq.demo.topic;

import com.rabbitmq.client.*;
import rabbitmq.demo.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @ClassName
 * @Description
 * @Autor wcy
 * @Date 2020/10/29 15:47
 */
public class Consumer1 {
    public static void main(String[] args) throws IOException {

        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("topics", "topic");
        //获取临时队列
        String queue = channel.queueDeclare().getQueue();

        //通道绑定队列
        channel.queueBind(queue,"topics","#.audit.*" );

        channel.basicConsume(queue, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("获取动态路由消息-->"+new String(body));
            }
        });

    }
}