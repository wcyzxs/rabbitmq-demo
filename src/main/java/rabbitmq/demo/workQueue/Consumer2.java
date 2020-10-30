package rabbitmq.demo.workQueue;

import com.rabbitmq.client.*;
import rabbitmq.demo.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @ClassName
 * @Description
 * @Autor wcy
 * @Date 2020/10/28 13:35
 */
public class Consumer2 {
    public static void main(String[] args) throws IOException {

        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("work", true, false, false, null);

        // 每次只能消费一个消息，这样防止一次性分发到多条消息
        channel.basicQos(1);

        channel.basicConsume("work", false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-2 获取工作队列中的消息----》"+new String(body));
                //手动确认  参数1：确认队列中的哪个具体消息    参数2： 是否开启多个消息同时确认   false:每次确认一个
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

    }
}