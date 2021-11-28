import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * TODO
 *
 * @author LJY
 * @description
 * @createdate 2021/3/23:16:46
 */
public class RouteSend {
    private final static String EXCHANGE_NAME = "test_exchange_direct";
    public static  void main(String[] args) throws Exception{
        //获取到连接以及mq通道
        Connection connection = util.ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        //消息内容
        String messange = "删除商品";
        channel.basicPublish(EXCHANGE_NAME,"delete",null,messange.getBytes());
        System.out.println(messange);

        channel.close();
        connection.close();
    }
}
