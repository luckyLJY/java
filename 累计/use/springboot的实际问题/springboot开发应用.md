## SpringBoor时间监听的4中实现方式

### 手工向ApplicationContext中添加监听器

创建MyListener1类

```java
public class MyListener1 implements ApplicationListener<MyEvent>
{
 Logger logger = Logger.getLogger(MyListener1.class);
 
 public void onApplicationEvent(MyEvent event)
 {
  logger.info(String.format("%s监听到事件源：%s.", MyListener1.class.getName(), event.getSource()));
 }
}
```

然后在springboot应用启动类中获取ConfigurableApplicationContext上下文，装载监听

```java
@SpringBootApplication
public class LisenterApplication
{
 public static void main(String[] args)
 {
  ConfigurableApplicationContext context = SpringApplication.run(LisenterApplication.class, args);
  //装载监听
  context.addApplicationListener(new MyListener1());
 }
}
```

### 将监听器装载入spring容器

```java
@Component
public class MyListener2 implements ApplicationListener<MyEvent>
{
 Logger logger = Logger.getLogger(MyListener2.class);
 
 public void onApplicationEvent(MyEvent event)
 {
  logger.info(String.format("%s监听到事件源：%s.", MyListener2.class.getName(), event.getSource()));
 }
}
```

### 在application.propertied中配置监听器

首先创建MyListener3类

```java
public class MyListener3 implements ApplicationListener<MyEvent>
{
 Logger logger = Logger.getLogger(MyListener3.class);
 
 public void onApplicationEvent(MyEvent event)
 {
  logger.info(String.format("%s监听到事件源：%s.", MyListener3.class.getName(), event.getSource()));
 }
}
```

然后在application.properties中配置监听

```java
context.listener.classes=com.listener.MyListener3
```

### 通过@EventListener注解实现事件监听

创建MyListener4类，该类无需实现ApplicationListener接口，使用@EventListener装饰具体方法

```java
@Component
public class MyListener4
{
 Logger logger = Logger.getLogger(MyListener4.class);
 
 @EventListener
 public void listener(MyEvent event)
 {
  logger.info(String.format("%s监听到事件源：%s.", MyListener4.class.getName(), event.getSource()));
 }
}
```

自定义事件代码如下：

```java
@SuppressWarnings("serial")
public class MyEvent extends ApplicationEvent
{
 public MyEvent(Object source)
 {
  super(source);
 }
}
```

进行测试(在启动类中加入发布事件的逻辑)：

```java
@SpringBootApplication
public class LisenterApplication
{
 public static void main(String[] args)
 {
  ConfigurableApplicationContext context = SpringApplication.run(LisenterApplication.class, args);
  //装载事件
  context.addApplicationListener(new MyListener1());
  //发布事件
  context.publishEvent(new MyEvent("测试事件."));
 }
}
```

## SpringBoot多线程环境下，解决多个定时器冲突问题

逻辑代码

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SchedulerTaskController {
    private Logger logger= LoggerFactory.getLogger(SchedulerTaskController.class);
    private static final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
    private int count=0;
    @Scheduled(cron="*/6 * * * * ?")
    @Async("threadPoolTaskExecutor")
    public void process(){
        logger.info("英文:this is scheduler task runing "+(count++));
    }

    @Scheduled(fixedRate = 6000)
    @Async("threadPoolTaskExecutor")
    public void currentTime(){
        logger.info("中文:现在时间"+dateFormat.format(new Date()));
    }
}
```

配置类如下 :

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.ThreadPoolExecutor;



/**使用多线程的时候，往往需要创建Thread类，或者实现Runnable接口，如果要使用到线程池，我们还需要来创建Executors，
 * 在使用spring中，已经给我们做了很好的支持。只要要@EnableAsync就可以使用多线程
 * 通过spring给我们提供的ThreadPoolTaskExecutor就可以使用线程池。*/
//@Configuration 表示该类是一个配置类
@Configuration
@EnableAsync
//所有的定时任务都放在一个线程池中，定时任务启动时使用不同都线程。
public class TaskScheduleConfig {
    private static final int corePoolSize = 10;         // 默认线程数
    private static final int maxPoolSize = 100;       // 最大线程数
    private static final int keepAliveTime = 10;   // 允许线程空闲时间（单位：默认为秒）,十秒后就把线程关闭
    private static final int queueCapacity = 200;   // 缓冲队列数
    private static final String threadNamePrefix = "it-is-threaddemo-"; // 线程池名前缀

    @Bean("threadPoolTaskExecutor") // bean的名称，默认为首字母小写的方法名
    public ThreadPoolTaskExecutor getDemoThread(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(keepAliveTime);
        executor.setKeepAliveSeconds(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);

        //线程池拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化
        executor.initialize();

        return executor;
    }
}
```
