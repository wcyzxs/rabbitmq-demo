package rabbitmq.demo.workQueue;

import com.rabbitmq.client.*;
import rabbitmq.demo.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @ClassName
 * @Description
 * 测试后： 不做任何设置的情况下，消费者是平均消费生产者的信息的
 * 注意点：（1）basicConsume的第二个参数autoAck一般不设置自动确认。消费者拿到平均分配的消息后，就会告诉队列自己已消费完。比如说5个消息，实际上可能消费者才消费3个之后就宕机了，那剩下的2条信息就会丢失。
 * 这样的话尽管消费者2处于空闲状态，但是由于消费1已经告诉队列消费完了，队列删除消息后，消费者2也是获取不到的。所以一般设置false.
 * @Autor wcy
 * @Date 2020/10/28 13:35
 */
public class Consumer1 {
    public static void main(String[] args) throws IOException {

        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("work", true, false, false, null);

        // 每次只能消费一个消息，这样防止一次性分发到多条消息
        channel.basicQos(1);

        channel.basicConsume("work", false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者-1 获取工作队列中的消息----》"+new String(body));
                //手动确认  参数1：确认队列中的哪个具体消息    参数2： 是否开启多个消息同时确认   false:每次确认一个
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

    }
}