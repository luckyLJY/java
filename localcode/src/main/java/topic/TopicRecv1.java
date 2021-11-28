import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;


/**
 * TODO
 *
 * @author LJY
 * @description
 * @createdate 2021/3/24:8:57
 */
public class TopicRecv1 {

    //定义队列名称为test_queue_topic_work_2
    private final static String QUEUE_NAME = "test_queue_topic_work_2";

    //定义交换机名称为test_exchange_topic
    private final static String EXCHANGE_NAME= "test_exchange_topic";

    public static void  main(String[] args) throws Exception {
        //获取到连接以及mq通道
        Connection connection = util.ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"*.*");

        //同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        //定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);

        //监听队列，手动返回完成
        channel.basicConsume(QUEUE_NAME,false,consumer);

        //获取消息
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(message);
            Thread.sleep(10);

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }
    }
}
