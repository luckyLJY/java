import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

/**
 * TODO
 *
 * @author LJY
 * @description
 * @createdate 2021/3/24:8:21
 */
public class RouteRcv1 {
    //定义队列名称test_queue_direct2
    private final  static String QUEUE_NAME = "test_queue_direct2";

    //定义交换机名称test_exchange_direct
    private  final static String EXCHANGE_NAME= "test_exchange_direct";

    public static void main(String [] args) throws Exception{

        //获取到连接以及mq通道
        Connection connection = util.ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //绑定到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"insert");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"update");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"delete");

        //同一时刻服务器只会发送一条信息
        channel.basicQos(1);

        //定义队列消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);

        //监听队列，手动完成
        channel.basicConsume(QUEUE_NAME,false,consumer);

        //获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String (delivery.getBody());
            System.out.println(message);
            Thread.sleep(10);

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }
    }
}
