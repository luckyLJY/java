import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * TODO
 *
 * @author LJY
 * @description
 * @createdate 2021/3/23:14:06
 */
public class Consumer02_subscribe_email {

    //队列名称
    private static final String QUEUE_EMAIL = "queue_inform_email";
    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";

    public static void main(String[] args) throws Exception {

        //创建一个与MQ的连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");//rabbitmq默认虚拟机名称为“/”，虚拟机相当于一个独立的mq服务器
        Connection connection = factory.newConnection();

        //创建一个通道
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM,"fanout");

        //声明队列
        channel.queueDeclare(QUEUE_EMAIL,true,false,false,null);

        //将交换机与队列绑定
        channel.queueBind(QUEUE_EMAIL,EXCHANGE_FANOUT_INFORM,"");

        //定义消费
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                long deliveryTag = envelope.getDeliveryTag();
                String exchange = envelope.getExchange();
                //消息内容
                String message = new String(body, "utf-8");
                System.out.println(message);
            }
        };

        //监听队列
        channel.basicConsume(QUEUE_EMAIL,true,defaultConsumer);
    }
}
