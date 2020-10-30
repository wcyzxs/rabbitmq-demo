package rabbitmq.demo.helloWorld;

import com.rabbitmq.client.*;
import rabbitmq.demo.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName
 * @Description 消费者
 * 注意点： autoAck一般不设置自动确认。消费者拿到平均分配的消息后，就会告诉队列自己已消费完。比如说5个消息，实际上可能消费者才消费3个之后就宕机了，那剩下的2条信息就会丢失
 * @Autor wcy
 * @Date 2020/10/27 16:03
 */
public class Consumer {
    //@Test不支持多线程，所以这里使用得是main方法进行得测试
    public static void main(String[] args) throws IOException, TimeoutException {

        //通过工具类获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        //通道绑定队列
        // 参数2:durable  是否持久化 （重启后队列是否存在）
        // 参数3:exclusive   是否独占队列 (是否只允许当前链接可用该队列)
        // 参数4:autoDelete  是否自动删除   ( 当消费者消费队列中得最后一条信息后，并终断消费者服务后，消息队列会被删除)
        // 参数5:其他属性 对队列的额外配置
        channel.queueDeclare("hello mq", true, false, false, null);

        //参数1： 队列名称
        //参数2： autoAck 消息的自动确认，true:消费者向rabbitmq确认消息消费，队列就会删除该条信息
        //参数3： 消费消息时得回调接口
        channel.basicConsume("hello mq", true, new DefaultConsumer(channel){
            //最后一个参数：   就是消息队列中取出得消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("取出得消息-----"+new String(body));
            }
        });
    }
}
