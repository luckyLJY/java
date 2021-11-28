import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * TODO
 *
 * @author LJY
 * @description
 * @createdate 2021/3/24:14:12
 */
public class RpcSend {

    //定义RPC的消息队列名称：rpc_queue
    private static final String RPE_QUEUE_NAME = "rpc_queue";

    public  static  void main(String[] args){

        //配置工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");//rabbitmq默认虚拟机名称为“/”，虚拟机相当于一个独立的mq服务器

        Connection connection = null;
        try{
            //建立TCP连接
            connection = factory.newConnection();

            //创建通道
            final Channel channel = connection.createChannel();

            //声明一个rpec_queue队列
            channel.queueDeclare(RPE_QUEUE_NAME,false,false,false,null);
            //设置同时最多只能获取一个消息
            //channel.basicQos(1);
            System.out.println("RPC SEND");

            //定义消息的回调处理类
            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //生成返回的结果，关键是设置correlationId值
                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                            .Builder()
                            .correlationId(properties.getCorrelationId())
                            .build();
                    //生成返回
                    String response = generateResponse(body);

                    //回复消息，通知已经收到请求
                    channel.basicPublish("",properties.getReplyTo(),replyProps,response.getBytes());
                    //对消息进行应答
                    channel.basicAck(envelope.getDeliveryTag(),false);
                    //唤醒正在消费所有的线程
                    synchronized (this){
                        this.notify();
                    }
                }
            };
            //消费消息
            channel.basicConsume(RPE_QUEUE_NAME,false,consumer);
            //在收到消息前，本线程进入等待状态
            while (true) {
                synchronized(consumer) {
                    try {
                        consumer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                // 空值判断，为了代码简洁略
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static String generateResponse(byte[] body){
        System.out.println(" [RpcServer] receive requests: " + new String(body));
        try {
            Thread.sleep(1000 *1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "response:" + new String(body) + "-" + System.currentTimeMillis();
    }
}
