import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import jdk.nashorn.internal.runtime.ECMAException;

/**
 * TODO
 *
 * @author LJY
 * @date 2021/3/22:16:30
 */
public class Back {


    private final static String EXCHANGE_NAME="test_exchange_fanout";


    public static void main(String[] args) throws Exception {
        //获取到连接以及mq通道
        Connection connection = util.ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");



        //消息内容
        String message = "Hello World1!";
        /**
         *发送消息
         */
        channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
        System.out.println(message);

        channel.close();
        connection.close();

    }
}
