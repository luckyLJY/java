import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

/**
 * TODO
 *
 * @author LJY
 * @description
 * @createdate 2021/3/23:8:22
 */
public class Rcv {

    private final static String    QUEUE_NAME="test1";
    private final static String  EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws Exception {

        //获取连接
        Connection connection = util.ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //声明交换机

        //绑定交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");

        //同一时刻服务器只会发送一条消息给消费者
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
