package rabbitmq.demo.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;



/**
 * @ClassName
 * @Description
 * @Autor wcy
 * @Date 2020/10/28 10:36
 */
public class RabbitMQUtils {

    private static ConnectionFactory connectionFactory;

    static {
        //创建连接工厂  在类加载得时候执行，只执行一次
        connectionFactory = new ConnectionFactory();
        //设置rabbitmq主机
        connectionFactory.setHost("47.100.24.174");
        //设置端口  (阿里服务器上面需要开启这个端口)   web界面访问是ip:15672  ,但是java程序访问得时候，端口就是5672
        connectionFactory.setPort(5672);
        //设置链接得虚拟主机(可通过界面创建)
        connectionFactory.setVirtualHost("/ems");
        //设置访问虚拟主机得用户名密码(可通过界面创建用户，用于生产者发送消息)
        connectionFactory.setUsername("wcy");
        connectionFactory.setPassword("123456");
    }

    /**
     * 提供获取链接对象得方法
     * @return
     */
    public static Connection getConnection() {
        try {
            //获取链接对象
            Connection connection = connectionFactory.newConnection();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭通道和连接
     * @param channel
     * @param connection
     */
    public static void   close(Channel channel,Connection connection){
        try {
            if(channel != null){
                channel.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}