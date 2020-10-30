package rabbitmq.demo.direct;

import com.rabbitmq.client.*;
import rabbitmq.demo.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @ClassName
 * @Description
 * @Autor wcy
 * @Date 2020/10/29 13:24
 */
public class Consumer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "logs_direct";

        channel.exchangeDeclare(exchangeName, "direct");


        String queue = channel.queueDeclare().getQueue();
        //基于routingKey 绑定队列和交换机
        channel.queueBind(queue,exchangeName,"error" );
        channel.queueBind(queue,exchangeName,"info" );

        //不设置消息的自动确认
        channel.basicConsume(queue, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2：路由直连模式获取数据--》"+new String(body));
            }
        });

    }
}