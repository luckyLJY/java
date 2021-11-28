import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * TODO
 *
 * @author LJY
 * @description
 * @createdate 2021/3/24:14:39
 */
public class RpcRcv {
    private static final String RPC_QUEUE_NAME = "rpc_queue";

    public static void main(String[] args){

        //配置连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");//rabbitmq默认虚拟机名称为“/”，虚拟机相当于一个独立的mq服务器


        Connection connection = null;
        Channel channel = null;
        try{
            //建立TCP连接
            connection = factory.newConnection();
            //在TCP连接的基础上创建通道
            channel = connection.createChannel();
            //定义临时队列，并返回生成的队列名称
            String replyQueueName = channel.queueDeclare().getQueue();

            //唯一标志本次请求
            final String corrId = UUID.randomUUID().toString();

            //生成发送消息的属性
            AMQP.BasicProperties props = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(corrId)//唯一标志本次请求
                    .replyTo(replyQueueName)//设置回调队列
                    .build();
           String  message = "Hello World";
            //发送消息，发送到默认交换机
            channel.basicPublish("",RPC_QUEUE_NAME,props,message.getBytes("UTF-8"));
            System.out.println(message);

            //阻塞队列，用于存储回调结果
            final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);

            //定义消息的回退方法
            channel.basicConsume(replyQueueName, true,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    if(properties.getCorrelationId().equals(corrId)){
                        response.offer(new String(body, "UTF-8"));
                    }
                }
            });
            //获取回调结果
            String result = response.take();
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                // 空值判断，为了代码简洁略
                channel.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
