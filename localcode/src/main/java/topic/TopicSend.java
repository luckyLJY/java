
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * TODO
 *
 * @author LJY
 * @description
 * @createdate 2021/3/24:8:37
 */
public class TopicSend {
    //定义exchange名称为test_exchange_topic
    private final static String EXCHANGE_NAME = "test_exchange_topic";
    public static void main(String[] args) throws Exception {
        //获取到连接以及mq通道
        Connection connction = util.ConnectionUtil.getConnection();
        Channel channel = connction.createChannel();

        //声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        //消息内容
        String message = "Hello World!";

        //发送消息
        channel.basicPublish(EXCHANGE_NAME,"routekey.1",null,message.getBytes());

        //打印消息
        System.out.println(message);

        //关闭连接
        channel.close();
        connction.close();

    }

}
