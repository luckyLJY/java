---


typora-root-url: img_api
---

# 一、FlinkAPI

![](/FlinkAPI过程.png)

## 1.1Environment

### 1.1.1getExecutionEnvironment

​	创建一个执行环境，表示当前执行程序的上下文。如果程序时独立调用的，则此方法返回本地执行环境；如果从命令行客户端电泳程序以提交到集群，则此方法决定返回此集群的执行环境，也就是说，getExecutionEnvironment会根据查询运行的方法返回什么样的运行环境，是最常用的一种创建执行环境的方式。

```java
ExecutionEnvironment env = ExrcutionEnviroment.getExecutionEnvironment();

StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnviroment();
```

​		如果没有设置并行度，会以flink-conf.yml中的配置为准，默认是1。

![](/并行度.png)

### 1.1.2createLocalEnviroment

返回本地执行环境，需要在调用时指定默认的并行度。

```java
LocalStreamEnviroment env = StreamExcutionEnviroment.createLocalEnvirment(1);
```

### 1.1.3createRemoteEnvironment

​	返回集群执行环境，将jar提交到远程服务器。需要在调用时指定JobManager的IP和端口号，并指定要在集群中运行的Jar包。

```java
StreamExecutionEnvironment = env
StreamExecutionEnvironment.createRemoteEnvironment("jobmanage-hostname",6123,"YOURPATH//WordCount.jar");
```

## 1.2Source

### 1.2.1从集合读取数据

```java
package com.guigu.apitest.source.beans;

/**
 * @deprecated 传感器温度读书类型
 */
public class SensorReading {
    //属性：id,时间戳，温度值
    private String id;
    private Long  timestap;
    private Double temperature;

    public SensorReading() {
    }

    public SensorReading(String id, Long timestap, Double temperature) {
        this.id = id;
        this.timestap = timestap;
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "SensorReading{" +
                "id='" + id + '\'' +
                ", timestap=" + timestap +
                ", temperature=" + temperature +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestap() {
        return timestap;
    }

    public void setTimestap(Long timestap) {
        this.timestap = timestap;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}


package com.guigu.apitest.source;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.util.Arrays;

/**
 * @Date 20210913
 * @version 1.0
 * @author ljy
 * @deprecated 读取集合文件
 */
public class SourceTest1_Collection{
    public static void main(String[] args)throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //并行度
        env.setParallelism(1);

        //1.Source:从集合读取数据
        DataStream<SensorReading> sesorDataStream = env.fromCollection(
                Arrays.asList(
                        new SensorReading("sensor_1",1547718199L, 35.8),
                        new SensorReading("sensor_6",1547718201L, 15.4),
                        new SensorReading("sensor_7",1547718202L, 6.7),
                        new SensorReading("sensor_10",1547718205L,38.1)
                )
        );

        //并行度为1
        DataStreamSource<Integer> integerDataStreamSource = env.fromElements(1, 2, 4, 4, 5, 6, 7 );

        //打印
        sesorDataStream.print("data");
        integerDataStreamSource.print("int");

        //执行可以传参jobName
        env.execute();
    }
}
```

### 1.2.2从文件中读取数据

```java
package com.guigu.apitest.source;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SourceTest2_File {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        //从文件读取数据
        DataStream<String> dataStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //打印
        dataStream.print();

        //执行
        env.execute();
    }
}

```

### 1.2.3从kafka读取数据

引入依赖

```xml
<dependency>
            <groupId>org.apache.flink</groupId>
            <artifactId>flink-connector-kafka-0.10_2.12</artifactId>
            <version>1.10.1</version>
</dependency>
```



```java
package com.guigu.apitest.source;

import javafx.beans.property.SimpleSetProperty;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

import java.util.Properties;

public class SourceTest3_Kafka {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //kafka配置项
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        properties.setProperty("group.id","consumer-group");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("auto.offset.rest","latest");

        //从Kafka读取数据
        DataStream<String> dataStream =
                env.addSource(new FlinkKafkaConsumer010<String>("sensor",new SimpleStringSchema(),properties));

        //打印
        dataStream.print();

        //执行
        env.execute();
    }
}

```

### 1.2.4自定义Source

​	除了以上的source数据来源，我们还可以自定义source。需要做的，只是传入一个SourceFunction就可以。具体调用如下：

```java
DataStream<SensorReading> dataStream = env.addSource(new MySensor());
```

​	我们希望可以随机生成传感器数据，MySensorSource具体代码实现如下：

```java
//实现自定义的SourceFunction
    public static class MySensorSource implements SourceFunction<SensorReading>{

        //定义标志为用于控制数据的产生
        private boolean running = true;

        @Override
        public void run(SourceContext<SensorReading> sourceContext) throws Exception {
            //定义随机数触发器
            Random random = new Random();

            //设置10个触感器的初始温度
            HashMap<String,Double>  sensorTempMap = new HashMap<>();
            for(int i = 0 ;i<10 ;i++){
                sensorTempMap.put("sensor_"+(i+1),60 + random.nextGaussian()*20);
            }

            while (running){
                for (String sensorId: sensorTempMap.keySet()){
                    //当前温度基础上随机波动
                    Double newTemp = sensorTempMap.get(sensorId) + random.nextGaussian();
                    sensorTempMap.put(sensorId,newTemp);
                    sourceContext.collect(new SensorReading(sensorId,System.currentTimeMillis(),newTemp));
                }
                //控制输出评率
                Thread.sleep(1000);
            }
        }

        @Override
        public void cancel() {
            running = false;
        }
    }
```



## 1.3数据处理Transform

### 1.3.1map

![](/map.png)

一对一

### 1.3.2flatmap

![](/flatmap.png)

数据拆分

### 1.3.3Filter

![](/fliter.png)

帅选过滤

基本算子

```java
package com.guigu.apitest.transform;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.operators.translation.KeyExtractingMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class TransformTest1_Base {
    public static void main(String[] args)throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //从文件读取数据
        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //1.map,把String转换为长度输出
        DataStream<Integer> mapSteam = inputStream.map(new MapFunction<String, Integer>() {
            @Override
            public Integer map(String value) throws Exception {

                return value.length();
            }
        });
        //2.flatmap,按逗号切分字段
        DataStream<String> flatMapStream = inputStream.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> collector) throws Exception {
                String[] fields = value.split(",");
                for (String field : fields){
                    collector.collect(field );
                }
            }
        });

        //Fliter，帅选sensor_1的数据
        DataStream<String> filterStream = inputStream.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String s) throws Exception {
                return s.startsWith("sensor_1");
            }
        });

        //打印输出
        mapSteam.print("map");
        flatMapStream.print("flatMap");
        filterStream.print("filter");

        env.execute();
    }
}

```

### 1.3.4KeyBy

![](/KeyBy.png)

### 1.3.5滚动聚合算子（Rolling Aggregation）

这些算子可以针对KeyedStream的每一个支流做聚合。

- sum()
- min()值
- max()值
- minBy()时间戳
- maxBy()时间戳

```java
package com.guigu.apitest.transform;

import akka.io.Udp;
import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TransformTest2_RollingAggrgation {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //从文件中读取数据
        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //转换成SensorReading类型
        /*DataStream<SensorReading> dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String value) throws Exception {
                String[] fields = value.split(",");
                return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
            }
        });*/
        DataStream<SensorReading> dataStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
        });

        //分组
        KeyedStream<SensorReading, Tuple> keyedStream = dataStream.keyBy("id");
        //KeyedStream<SensorReading, String> keyedStream1 = dataStream.keyBy(SensorReading::getId);

        //滚动聚合，取当前最大的温度值
        DataStream<SensorReading> resultStream = keyedStream.maxBy("temperature");

        resultStream.print();

        env.execute();
    }
}

```



### 1.3.6Reduce

**KeyedStream->DataStream：**一个分组数据流的聚合操作。合并当前的元素和上次聚合的结果，产生一个新的值。返回的流中包含每一次聚合的结果，而不是只返回最后一次聚合的最终结果。

```java
package com.guigu.apitest.transform;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TransformTest3_Reduce {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //从文件中读取数据
        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //转换成SensorReading类型
        /*DataStream<SensorReading> dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String value) throws Exception {
                String[] fields = value.split(",");
                return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
            }
        });*/
        DataStream<SensorReading> dataStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
        });

        //分组
        KeyedStream<SensorReading, Tuple> keyedStream = dataStream.keyBy("id");
        //KeyedStream<SensorReading, String> keyedStream1 = dataStream.keyBy(SensorReading::getId);

        //滚动聚合，取当前最大的温度值，以及当前最新的时间戳
        /*keyedStream.reduce(new ReduceFunction<SensorReading>() {
            @Override
            public SensorReading reduce(SensorReading sensorReading, SensorReading t1) throws Exception {
                return new SensorReading(sensorReading.getId(),t1.getTemperature(),Math.max(sensorReading.getTemperature(),t1.getTemperature()));
            }
        });*/
        DataStream<SensorReading> resultStream = keyedStream.reduce((curState, newDate) -> {
            return new SensorReading(curState.getId(), newDate.getTemperature(), Math.max(curState.getTemperature(), newDate.getTemperature()));
        });
        resultStream.print();

        env.execute();
    }
}

```



### 1.3.7Split和Select

![](/Split.png)

![](/select.png)



### 1.3.8Connect和CoMap

![](/Connect.png)





![](/CoMap.png)

处理



### 1.3.9union

![](/union.png)

```java
package com.guigu.apitest.transform;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

import java.util.Collections;

public class TransformTest3_MtipleStream {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //从文件中读取数据
        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //转换成SensorReading类型
        DataStream<SensorReading> dataStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
        });


        //1.分流，大于30度为界，分成两个流
        SplitStream<SensorReading> splitStream = dataStream.split(new OutputSelector<SensorReading>() {
            @Override
            public Iterable<String> select(SensorReading sensorReading) {
                return (sensorReading.getTemperature() > 30 ? Collections.singleton("high") : Collections.singleton("low"));
            }
        });

        DataStream<SensorReading> hightTempStream = splitStream.select("high");
        DataStream<SensorReading> lowTempStream = splitStream.select("low");
        DataStream<SensorReading> allStream = splitStream.select("high", "low");

        hightTempStream.print("high");
        lowTempStream.print("low");
        allStream.print("all");

        //2.connected将高温流转换为二元组，与低温流连接合并
        SingleOutputStreamOperator<Tuple2<String, Double>> waringStream = hightTempStream.map(new MapFunction<SensorReading, Tuple2<String, Double>>() {

            @Override
            public Tuple2<String, Double> map(SensorReading sensorReading) throws Exception {
                return new Tuple2<>(sensorReading.getId(), sensorReading.getTemperature());
            }
        });

        ConnectedStreams<Tuple2<String, Double>, SensorReading> connectedStreams = waringStream.connect(lowTempStream);

        SingleOutputStreamOperator<Object> resultStream = connectedStreams.map(new CoMapFunction<Tuple2<String, Double>, SensorReading, Object>() {
            @Override
            public Object map1(Tuple2<String, Double> stringDoubleTuple2) throws Exception {
                return new Tuple3<>(stringDoubleTuple2.f0, stringDoubleTuple2.f1, "height temp warning");
            }

            @Override
            public Object map2(SensorReading sensorReading) throws Exception {
                return new Tuple2<>(sensorReading.getId(), "normal");
            }
        });

        //3.union多条数据
        resultStream.print();
        hightTempStream.union(lowTempStream,allStream);
        env.execute();
    }
}

```

## 1.4支持的数据类型

​	Flink流应用程序处理的是以数据对象表示的事件流。所以Flink内部，我们需要能够处理这些对象。他们需要被序列化和反序列化，以便通过网络传送他们；或者从状态后端、检查点和保存点读取他们。为了有效地做到这一点，Flink需要明确直到应用程序所处理的数据类型。Flink使用类型信息的概念来表示数据类型，并为每个数据类型生成特定的序列化器，反序列化器和比较器。

​	Flink还具有一个类型提取系统，该系统分析函数的输入和返回类型，以自动获取类型信息，从而获得序列化器和反序列化器。但是，在某些情况下，例如lambda函数或泛型类型，需要显式地提供类型信息，才能应用程序工资或提供其性能。

​	Flink支持JAVA和Sacala中所有常见数据类型。使用最广泛的类型有以下几种。

### 1.4.1基础数据类型

![](/基础数据类型.png)



### 1.4.2JAVA和Scala元组（Tuples）

![](/元组类型.png)

### 1.4.3Scala样例类（case classes）

![](/Scaka样例.png)

### 1.4.4Java简单对象（POJOs）

类似JavaBean要求

### 1.4.5其他（Arrays,Lists,Maps,Enums,等等）

![](/其他数据类型.png)

## 1.5实现UDF函数-更细粒度的控制流

### 1.5.1函数类（Function Classes）

![](/UDF1.png)

![](/UDF2.png)

![](/UDF3.png)



### 1.5.2匿名函数（Lambda Functions）

![](/lambda.png)

### 1.5.3富函数(Rich Functions)

​		"富含鼠"是DataStream API提供的一个函数的接口，所有Flink函数类都有器Rich版本。它与常函数的不同在于，可以获取运行环境的上线问，并拥有一些生命周期方法，所以可以实现更复杂的功能

- RichMapFunction
- RichFlatMapFunction
- RichFilterFunction
- ...

Rich Function有一个生命周期的概念。典型的生命周期方法有：

- open()方法是rich function的初始化方法，当一个算子例如map或者filter被调用之前open()会被调用。
- close()方法是生命周期中的最后一个调用方法，做一些清理工作。
- getRuntimeContext()方法提供了函数的RuntimtContext的一些信息，例如函数的并行度，任务的名字，以及state状态

```java
package com.guigu.apitest.transform;import com.guigu.apitest.source.beans.SensorReading;import org.apache.flink.api.common.functions.MapFunction;import org.apache.flink.api.common.functions.RichMapFunction;import org.apache.flink.api.java.tuple.Tuple;import org.apache.flink.api.java.tuple.Tuple2;import org.apache.flink.configuration.Configuration;import org.apache.flink.streaming.api.datastream.DataStream;import org.apache.flink.streaming.api.datastream.KeyedStream;import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;public class TransformTest5_RichFunction {    public static void main(String[] args) throws Exception {        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();        env.setParallelism(4);        //从文件中读取数据        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");        //转换成SensorReading类型        DataStream<SensorReading> dataStream = inputStream.map(line -> {            String[] fields = line.split(",");            return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));        });        DataStream<Tuple2<String,Integer>> resultStream = dataStream.map(new MyMapper());        resultStream.print();        env.execute();    }    public static  class MyMpaper0 implements MapFunction<SensorReading,Tuple2<String,Integer>>{        @Override        public Tuple2<String, Integer> map(SensorReading sensorReading) throws Exception {            return new Tuple2<>(sensorReading.getId(),sensorReading.getId().length());        }    }        //实现自定义的富函数类        public static class  MyMapper extends RichMapFunction<SensorReading, Tuple2<String, Integer>> {            @Override            public Tuple2<String, Integer> map(SensorReading sensorReading) throws Exception {                return new Tuple2<>(sensorReading.getId(),getRuntimeContext().getIndexOfThisSubtask());            }            @Override            public void open(Configuration parameters) throws Exception {                //初始化工作，一般定义状态，或者建立数据库连接                System.out.println("open");            }            @Override            public void close() throws Exception {                //关闭连接，清空状态                System.out.println("close");            }        }    }
```

其他

```java
package com.guigu.apitest.transform;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TransformTest6_Partitation {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);

        //从文件中读取数据
        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //1.shuffle随机打乱
        DataStream<String> shuffleDataStream = inputStream.shuffle();

        //keyBy
        SingleOutputStreamOperator<SensorReading> keyStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] values = s.split(",");
                return new SensorReading(values[0], new Long(values[1]), new Double(values[2]));
            }
        });


        //shuffleDataStream.print("shuffle");
        //keyStream.keyBy("id").print("keybay");
        keyStream.global().print("global");
        env.execute();
    }
}
```

## 1.6Sink

​	Flink没有类似spark中的foreach方法，让用户迭代的操作。虽有对外的输出操作都要利用SlinK完成。最后通过类似如下方式完成整个任务最终输出操作。

```java
stream.addSink(new MySink(XXXX))
```

​	官方提供了一部分的框架的sink。除此之外，需要用户自定义实现sink。

Bundled Connectors(官方)

- Kafka连接器source/sink
- Apache Cassandra(sink)
- Elasticsearch(sink)
- Hadoop FileSystem(sink)
- RabbitMQ(source/sink)
- Apache NiFi(Source/sink)
- Twitter Streaming API(source)

Connectors in Apache Bahir(第三方)

- Apache ActiveMQ(source/sink)
- Apache Flume(sink)
- Redis(sink)
- Akka(sink)
- Netty(source)

### 1.6.1kafka

pom.xml

```xml
<dependency>
            <groupId>org.apache.flink</groupId>
            <artifactId>flink-connector-kafka-0.11_2.12</artifactId>
            <version>1.10.1</version>
        </dependency>
```

```java
package com.guigu.apitest.sink;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

import java.util.Properties;

public class SinkTest1_kafka {
    public static void main(String[] args)throws Exception {


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //kafka配置项
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        properties.setProperty("group.id","consumer-group");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("auto.offset.rest","latest");

        //从Kafka读取数据
        DataStream<String> inputStream =
                env.addSource(new FlinkKafkaConsumer010<String>("sensor",new org.apache.flink.api.common.serialization.SimpleStringSchema(),properties));

        //转换成SensorReading类型
        DataStream<String > dataStream = inputStream.map(new MapFunction<String, String>() {
            @Override
            public String map(String value) throws Exception {
                String[] fields = value.split(",");
                return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2])).toString();
            }
        });


        dataStream.addSink(new FlinkKafkaProducer011<String>("localhost:9092","sinktest",new SimpleStringSchema()));

        env.execute();
    }
}

```

### 1.6.2Redis

pom.xml

```xml
<dependency>
            <groupId>org.apache.bahir</groupId>
            <artifactId>flink-connector-redis_2.11</artifactId>
            <version>1.0</version>
        </dependency>
```

​	定义一个redis的mapper类，用于定义保存到redis时调用的命令：

```java
package com.guigu.apitest.sink;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisConfigBase;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;
import org.apache.hadoop.fs.Stat;

public class SinkTest2_Redis {
    public static void main(String[] args)throws Exception {


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //从文件读取数据
        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //转换成SensorReading类型
        DataStream<SensorReading > dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String value) throws Exception {
                String[] fields = value.split(",");
                return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
            }
        });
        //定义redis配置
        FlinkJedisPoolConfig config = new FlinkJedisPoolConfig.Builder()
                .setHost("localhost")
                .setPort(6379)
                .build();

        dataStream.addSink(new RedisSink<>(config,new MyRedisMapper()));


        env.execute();
    }

    //自定义RedisMapper
    public static class MyRedisMapper implements RedisMapper<SensorReading>{
        //定义保存数据到redis的命令，存成Hash表，hset sensor_temp id temperature
        @Override
        public RedisCommandDescription getCommandDescription() {
            return new RedisCommandDescription(RedisCommand.HSET,"sensor_temp");
        }

        @Override
        public String getKeyFromData(SensorReading sensorReading) {
            return sensorReading.getId();
        }

        @Override
        public String getValueFromData(SensorReading sensorReading) {
            return sensorReading.toString();
        }
    }
}

```

启动redis服务端:redis-server

启动redis客户端:redis-cli

### 1.6.3Elasticsearch

pom.xml

```xml
<dependency>
            <groupId>org.apache.flink</groupId>
            <artifactId>flink-connector-elasticsearch6_2.12</artifactId>
            <version>1.10.1</version>
        </dependency>
```

在主函数中调用

```java
package com.guigu.apitest.sink;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkBase;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;
import org.omg.CORBA.Request;

import java.util.ArrayList;
import java.util.HashMap;

public class SinkTest3_Es {
    public static void main(String[] args)throws Exception {


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //从文件读取数据
        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //转换成SensorReading类型
        DataStream<SensorReading > dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String value) throws Exception {
                String[] fields = value.split(",");
                return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
            }
        });

        //定义es的链接配置
        ArrayList<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("localhost",9200));

        dataStream.addSink(new ElasticsearchSink.Builder<SensorReading>(httpHosts,new MyEsSinkFunction()).build());

        env.execute();
    }
    //实现自定义的ES写入操作
    public static class MyEsSinkFunction implements ElasticsearchSinkFunction<SensorReading>{
        @Override
        public void process(SensorReading sensorReading, RuntimeContext runtimeContext, RequestIndexer requestIndexer) {
            //定义写入数据source
            HashMap<String,String> dataSource = new HashMap<>();
            dataSource.put("id",sensorReading.getId());
            dataSource.put("temp",sensorReading.getTemperature().toString());
            dataSource.put("ts",sensorReading.getTimestap().toString());

            //创建请求，作为向es发起的写入命令
            IndexRequest indexRequest = Requests.indexRequest()
                    .index("sensor")
                    .type("readingdata")
                    .source(dataSource);

            //用index发送请求
            requestIndexer.add(indexRequest);
        }
    }
} 
 
```



### 1.6.4JDBC自定义sink

pom.xml

```xml
<dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.44</version>
        </dependency>
```

​	添加MyJdbcSink

```java
package com.guigu.apitest.sink;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

public class SinkTest4_Mysql {
    public static void main(String[] args)throws Exception {


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //从文件读取数据
        DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");

        //转换成SensorReading类型
        DataStream<SensorReading > dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String value) throws Exception {
                String[] fields = value.split(",");
                return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
            }
        });

        //
        dataStream.addSink(new MyJdbcSink());

        env.execute();
    }

    public static  class  MyJdbcSink extends RichSinkFunction<SensorReading> {

        //声明连接和预编译语句
        Connection connection = null;
        PreparedStatement insertStmt = null;
        PreparedStatement updateStmt = null;

        @Override
        public void open(Configuration parameters) throws Exception {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test","root","123456");
            insertStmt = connection.prepareStatement("insert into sensor_temp(ID, temp) values (?,?)");
            updateStmt = connection.prepareStatement("update sensor_temp set temp =? where ID =?");
        }

        //每来一条数据，调用连接，执行sql
        @Override
        public void invoke(SensorReading value, Context context) throws Exception {
            //直接执行更新语句，如果没有更新那么就插入
            updateStmt.setDouble(1,value.getTemperature());
            updateStmt.setString(2,value.getId());
            updateStmt.execute();

            if(updateStmt.getUpdateCount() == 0){
                insertStmt.setString(1,value.getId());
                insertStmt.setDouble(2,value.getTemperature());
                insertStmt.execute();
            }
        }

        @Override
        public void close() throws Exception {
            insertStmt.close();
            updateStmt.close();
            connection.close();
        }
    }
}

```

### 1.6.5向文件写入

- **方式一 writeAsText(已过时)**

  ```java
  dataStreamSource.writeAsText("本地/HDFS的path（必填参数）",覆盖类型（选填参数）).setParallelism(并行度)
  
  ```

  

- **方式二 StreamingFileSink**

  ```java
            StreamingFileSink<User> sink2 = StreamingFileSink
                  .forRowFormat(
                          new Path(filePath),
                          new SimpleStringEncoder<User>("UTF-8"))
                  /**
                   * 设置桶分配政策
                   * DateTimeBucketAssigner --默认的桶分配政策，默认基于时间的分配器，每小时产生一个桶，格式如下yyyy-MM-dd--HH
                   * BasePathBucketAssigner ：将所有部分文件（part file）存储在基本路径中的分配器（单个全局桶）
                   */
                  .withBucketAssigner(new DateTimeBucketAssigner<>())
                  /**
                   * 有三种滚动政策
                   *  CheckpointRollingPolicy
                   *  DefaultRollingPolicy
                   *  OnCheckpointRollingPolicy
                   */
                  .withRollingPolicy(
                          /**
                           * 滚动策略决定了写出文件的状态变化过程
                           * 1. In-progress ：当前文件正在写入中
                           * 2. Pending ：当处于 In-progress 状态的文件关闭（closed）了，就变为 Pending 状态
                           * 3. Finished ：在成功的 Checkpoint 后，Pending 状态将变为 Finished 状态
                           *
                           * 观察到的现象
                           * 1.会根据本地时间和时区，先创建桶目录
                           * 2.文件名称规则：part-<subtaskIndex>-<partFileIndex>
                           * 3.在macos中默认不显示隐藏文件，需要显示隐藏文件才能看到处于In-progress和Pending状态的文件，因为文件是按照.开头命名的
                           *
                           */
                          DefaultRollingPolicy.builder()
                                  //每隔多久（指定）时间生成一个新文件
                                  .withRolloverInterval(TimeUnit.SECONDS.toMillis(2))
                                  //数据不活动时间 每隔多久（指定）未来活动数据，则将上一段时间（无数据时间段）也生成一个文件
                                  .withInactivityInterval(TimeUnit.SECONDS.toMillis(1))
                                  //每个文件大小
                                  .withMaxPartSize(1024 * 1024 * 1024)
                                  .build())
                  /**
                   * 设置sink的前缀和后缀
                   * 文件的头和文件扩展名
                   */
                  .withOutputFileConfig(OutputFileConfig
                          .builder()
                          .withPartPrefix("lei")
                          .withPartSuffix(".txt")
                          .build())
                  .build();
  
  ```

  

## 1.7window

### 1.7.1window概念

 <img src="/窗口.png" style="zoom:150%;" />

  

### 1.7.2window类型

- 时间窗口（Time Window）

  - 滚动时间窗口

  ![](/滚动时间窗口.png)

  

  - 滑动时间窗口

  ![](/滑动窗口.png)

  - 会话窗口

  ![](/会话窗口.png)

  基于时间

- 计数窗口（Count Window）

  - 滚动计数窗口
  - 滑动计数窗口

### 1.7.3window API

- 窗口分配器------window()方法

  - 我们可以用.window()来定义一个窗口，然后基于这个window去做一些聚合或者其他处理操作。注意window()方法必须在keyBy之后才能用。

  - Flink提供了更加简单的.timeWindow和.countWindow方法，用于定义时间窗口和计数窗口。

    ```java
    DataStream<Tuple2<String,Double>> minTempPerWindowStream = 
        dataStream
        .map(new MyMapper())
        .keyBy(data -> data.f0)
        .timeWindow(Time.seconds(15))
        .minBy(1);
    ```

- 窗口分配器

  - window()方法接收的输入参数是一个WindowAssigner
  - WindowAssigner负责将每条输入的数据分发到正确的window中
  - Flink提供了通用的WindowAssigner
    - 滚动窗口（tumbling window）
    - 滑动窗口（sliding window）
    - 会话窗口（session window）
    - 全局窗口（global window）

- 创建不同类型的窗口

  - 滚动时间窗口

    ```java
    .window(TumblingProcessingTimeWindows.of(Time.seconds(15)));
    .timeWindow(Time.seconds(15));
    ```

  - 滑动时间窗口

    ```java
    .timeWindow(Time.seconds(15),Time.seconds(5));
    ```

  - 会话窗口

    ```java
    .window(EventTimeSessionWindows.withGap(Time.minutes(1)));
    ```

  - 滚动计数窗口

    ```java
    .countWindow(5)
    ```

  - 滑动计数窗口

    ```java
    .countWindow(10,2)
    ```

  

- 窗口函数（window function）

  - window function定义了要对窗口中收集的数据做的计算操作

  - 可以分为两类

    - 增量聚合函数
      - 每条数据到来就进行计算，保持一个简单的状态
      - ReduceFunction,AggregateFunction
      - 简单聚合操作sum(),max()......
    - 全窗口函数
      - 先报函数所有数据收集起来，等到计算的时候会遍历所有的数据
      - ProcessWindowFunction--window, ProcessAllWindowFunction--windowAll

    时间窗口案例(全量)

    ```java
    package com.guigu.apitest.window;
    
    import com.guigu.apitest.source.beans.SensorReading;
    import org.apache.commons.collections.IteratorUtils;
    import org.apache.flink.api.common.functions.AggregateFunction;
    import org.apache.flink.api.common.functions.MapFunction;
    import org.apache.flink.api.java.tuple.Tuple;
    import org.apache.flink.api.java.tuple.Tuple3;
    import org.apache.flink.streaming.api.datastream.DataStream;
    import org.apache.flink.streaming.api.datastream.DataStreamSource;
    import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
    import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
    import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
    import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
    import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
    import org.apache.flink.streaming.api.windowing.evictors.CountEvictor;
    import org.apache.flink.streaming.api.windowing.time.Time;
    import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
    import org.apache.flink.util.Collector;
    
    public class WindowTest1_TimeWindow {
        public static void main(String[] args) throws Exception {
    
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env.setParallelism(1);
    
            //从文件读取数据
            //DataStream<String> inputStream = env.readTextFile("F:\\新建文件夹\\java\\flink\\FlinkTourtourial\\src\\main\\resources\\sensor.txt");
    
            //socket文本流
            DataStream<String> inputStream = env.socketTextStream("192.168.75.140", 7777);
    
            //转换成Sensording类型
            DataStream<SensorReading> dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
    
                @Override
                public SensorReading map(String s) throws Exception {
                    String[] fields = s.split(",");
                    return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
                }
            });
    
            //开窗测试
            /*DataStream<Integer> resultStream = dataStream.keyBy("id")
                    //时间窗口
                    //.window(TumblingProcessingTimeWindows.of(Time.seconds(15)))
    
                    //.timeWindow(Time.seconds(10))
                    //会话窗口
                    //.window(EventTimeSessionWindows.withGap(Time.minutes(1)));
                    //计数窗口
                    //.countWindow(10,2); evictor去除器 trigger触发器
                    //增量
                    .window(TumblingProcessingTimeWindows.of(Time.seconds(15)))
                    .aggregate(new AggregateFunction<SensorReading, Integer, Integer>() {
                        @Override
                        public Integer createAccumulator() {
                            return 0;
                        }
    
                        @Override
                        public Integer add(SensorReading value, Integer accumulator) {
                            return accumulator + 1;
                        }
    
                        @Override
                        public Integer getResult(Integer accumulator) {
                            return accumulator;
                        }
    
                        @Override
                        public Integer merge(Integer a, Integer b) {
                            return a + b;
                        }
                    });*/
            //全量聚合
            DataStream<Tuple3<String, Long, Integer>> resultStream2 = dataStream.keyBy(SensorReading::getId)
                    .timeWindow(Time.seconds(15))
                    /*.process(new ProcessWindowFunction<SensorReading, Object, Tuple, TimeWindow>() {
    /               })*/
                    .apply(new WindowFunction<SensorReading, Tuple3<String, Long, Integer>, String, TimeWindow>() {
                        @Override
                        public void apply(String s, TimeWindow window, Iterable<SensorReading> input, Collector<Tuple3<String, Long, Integer>> out) throws Exception {
                            String id = s;
                            long windowEnd = window.getEnd();
                            int count = IteratorUtils.toList(input.iterator()).size();
                            out.collect(new Tuple3<>(id, windowEnd, count));
                        }
                    });
    
    
    
            // resultStream.print("result");
            resultStream2.print("result2");
            env.execute();
    
        }
    }
    
    ```

    计数窗口(增量)

    ```java
    package com.guigu.apitest.window;
    
    import com.guigu.apitest.source.beans.SensorReading;
    import org.apache.flink.api.common.functions.AggregateFunction;
    import org.apache.flink.api.common.functions.MapFunction;
    import org.apache.flink.api.java.tuple.Tuple2;
    import org.apache.flink.streaming.api.datastream.DataStreamSource;
    import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
    import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
    import scala.Tuple3;
    
    public class WindowTest2_CountWindow {
        public static void main(String[] args) throws Exception{
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env.setParallelism(1);
    
            DataStreamSource<String> inputStream = env.socketTextStream("192.168.75.140", 7777);
    
            SingleOutputStreamOperator<SensorReading> dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
                @Override
                public SensorReading map(String s) throws Exception {
                    String[] fileds = s.split(",");
                    return new SensorReading(fileds[0], new Long(fileds[1]), new Double(fileds[2]));
                }
            });
    
            //计数窗口
            SingleOutputStreamOperator<Double> avgResultStream = dataStream.keyBy("id")
                    .countWindow(3, 1)
                    .aggregate(new MyAvgTemp());
    
            avgResultStream.print();
    
            env.execute();
        }
        public static class MyAvgTemp implements AggregateFunction<SensorReading, Tuple2<Double,Integer>,Double>{
            @Override
            public Tuple2<Double, Integer> createAccumulator() {
                return new Tuple2<>(0.0,0);
            }
    
            @Override
            public Tuple2<Double, Integer> add(SensorReading sensorReading, Tuple2<Double, Integer> accumulator) {
                return new Tuple2<>(accumulator.f0+ sensorReading.getTimestap(),accumulator.f1+1);
            }
    
            @Override
            public Double getResult(Tuple2<Double, Integer> accumulator) {
                return accumulator.f0 / accumulator.f1;
            }
    
            @Override
            public Tuple2<Double, Integer> merge(Tuple2<Double, Integer> a, Tuple2<Double, Integer> b) {
                return new Tuple2<>(a.f0+b.f0, a.f1+b.f1);
            }
        }
    }
    
    ```

    

- 其他可选API

  - .trigger()-触发器：定义window什么时候关闭，触发计算并输出结果

  - .evitor()-移除器：定义移除某些数据的逻辑

  - .allowedLateness()-允许处理迟到的数据

  - .sideOutputLateData()-将迟到的数据放入侧输出流

  - .getSideOuput()-获取侧输出流

    ```java
    //标签
    OutputTag<Sensording> outputTag = new OutputTag<Sensording>("late"){}
    DataStream<Sensording>sumStream=dataStream.keyBy("id")
                    .timeWindow(Time.seconds(15))
                    /*.trigger(new Trigger<SensorReading, TimeWindow>() {
                        @Override
                        public TriggerResult onElement(SensorReading sensorReading, long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
                            return null;
                        }
    
                        @Override
                        public TriggerResult onProcessingTime(long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
                            return null;
                        }
    
                        @Override
                        public TriggerResult onEventTime(long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
                            return null;
                        }
    
                        @Override
                        public void clear(TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
    
                        }
                    })*/
                    /*.evictor(new Evictor<SensorReading, TimeWindow>() {
                        @Override
                        public void evictBefore(Iterable<TimestampedValue<SensorReading>> iterable, int i, TimeWindow timeWindow, EvictorContext evictorContext) {
                            
                        }
    
                        @Override
                        public void evictAfter(Iterable<TimestampedValue<SensorReading>> iterable, int i, TimeWindow timeWindow, EvictorContext evictorContext) {
    
                        }
                    })*/
                    .allowedLateness(Time.minutes(1))//针对事件时间窗口
                    .sideOutputLateData(outputTag)
    				.sum("tempature");
    
    sumStream.getSideOutput(outputTag).print("late")
    ```

    ![](/其他API.png)

    reduce:

    ​		操作窗口中的数据，窗口数据进入的数据结构和输出的数据结构相同
    
    aggregate:
    
    ​		操作窗口中的数据，窗口数据进入的数据结构可以添加字段到输出数据结构中
    
    ```
    			key            window              apply
    DataStream-------keyStream--------windowStream-------DataStream
    ```

#### 触发器trigger

- EventTimeTrigger:通过对比Watermark和窗口的Endtime确定是否触发窗口计算，如果Watermark大于Window EndTime则触发，否则不触发，窗口将继续等待。
- ProcessTimeTrigger:通过对比ProcessTime和窗口EndTime确定是否触发窗口，如果ProcessTime大于EndTime则触发计算，否则窗口继续等待。
- ContinuousEventTimeTrigger:根据时间间隔周期性触发窗口或者Window的结束时间小于当前的ProcessTime触发窗口计算
- CountTrigger:根据接入数据量是否超过设定的阀值判断是否触发窗口计算
- DeltaTrigger:根据接入计算出来的Delta指标是否超过指定的Threshold去判断哪是否触发窗口计算。
- PurgingTrigger:可以将任意触发器作为参数转换为Purge类型的触发器，计算完成后数据被清理。

自定义Trigger

```java
public class MyTrigger extends Trigger<Tuple2<String, Integer>, TimeWindow> {
    private static final long serialVersionUID = 1L;
    ValueStateDescriptor<Integer> stateDescriptor = new ValueStateDescriptor<>("total", Integer.class);

    @Override
    public TriggerResult onElement(Tuple2<String, Integer> element, long timestamp, TimeWindow window, TriggerContext ctx) throws Exception {
        ValueState<Integer> sumState = ctx.getPartitionedState(stateDescriptor);
        if (null == sumState.value()) {
            sumState.update(0);
        }
        sumState.update(element.f1 + sumState.value());
        if (sumState.value() >= 2) {
            //这里可以选择手动处理状态
            //  默认的trigger发送是TriggerResult.FIRE 不会清除窗口数据
            return TriggerResult.FIRE_AND_PURGE;
        }
        return TriggerResult.CONTINUE;
    }

    @Override
    public TriggerResult onProcessingTime(long time, TimeWindow window, TriggerContext ctx) throws Exception {
        return TriggerResult.CONTINUE;
    }

    @Override
    public TriggerResult onEventTime(long time, TimeWindow window, TriggerContext ctx) throws Exception {
        return TriggerResult.CONTINUE;
    }

    @Override
    public void clear(TimeWindow window, TriggerContext ctx) throws Exception {
        System.out.println("清理窗口状态  窗口内保存值为" + ctx.getPartitionedState(stateDescriptor).value());
        ctx.getPartitionedState(stateDescriptor).clear();
    }
}
```

上述自定义Trigger例子会在Tuple f1数据叠加到100后触发计算并清除状态数据。
自定义Trigger需要实现的方法说明：

- OnElement ：每一个数据进入窗口都会触发。

- OnEventTime ：根据接入窗口的EventTime进行触发操作

- OnProcessTime ： 根据接入窗口的ProcessTime进行触发操作

- Clear ： 执行窗口及状态数据的清除方法。

  窗口触发方法返回结果的类型：

  - CONTINUE ： 不进行操作，等待。
  - FIRE ： 触发计算且数据保留。
  - PRUGE ： 窗口内部数据清除且不触发计算。
  - FIRE_AND_PURGE : 触发计算并清除对应的数据。

## 1.8Flink中时间语义和watermark

### 1.8.1Flink中的时间语义

![](/时间语义.png)

![](/时间语义举例.png)

Event Time可以从日志数据的时间戳(timestamp)中提取

- 在代码中设置EventTime

  - 默认为Processing Time

  - 在执行环境中调用setStreamTimeCharacteristic方法，设置流的时间特性

  - 具体的时间，还需要从数据中提取时间戳(timestamp)

    ```java
    StreamExecutionEnvironment env = 
        StreamExecutionEnvironment.getExecutionEnvironment();
    env.setStreamTimeCharacteristic(TimeCharacteristic.EvnetTime); 
    ```

- 乱序数据的影响

  ![](/乱序数据.png)

  

  - 当Flink以Event Time模式处理数据流时，它会根据数据里的时间戳来处理基于时间的算子

  （比如5s一个窗口，那么理想情况下，遇到时间戳是5s的数据时，就认为[0,5s)时间段的桶bucket就可以关闭了。）

  - 

    乱序产生原因网络、分布式等 

  - 乱序数据会导致窗口计算不准确（如果按照前面说法，获取到5s时间戳的数据，但是2s，3s乱序数据还没到，理论上不应该关闭桶）


### 1.8.2水位线（watermark）

- 怎样避免乱序数据带来的计算不正确？

- 遇到一个时间戳达到了窗口关闭时间，不应该立即触发窗口计算，而是等待一段时间，等迟到的数据来了再关闭窗口

  1. Watermark是一种衡量Event Time进展的机制，可以设定延迟触发

  2. Watermark是用于处理乱序事件的，而正确的处理乱序事件，通常用Watermark机制结合window来实现

  3. 数据流中的Watermark用于表示”timestamp小于Watermark的数据，都已经到达了“，因此，window的执行也是由Watermark触发的。

  4. Watermark可以理解成一个延迟触发机制，我们可以设置Watermark的延时时长t，每次系统会校验已经到达最大的maxEventTime,然后认定eventTime小于maxEvent -t的所有数据都已经到达，如果有窗口的停止时间等于maxEventTime - t,那么这个窗口被触发执行。

     Watermark = maxEventTime - 延迟时间t

  5. watermark用来让程序自己平衡延迟和结果正确性

  *watermark可以理解为把原本的窗口标准稍微放宽了一点。（比如原本5s，设置延迟时间=2s，那么实际等到7s的数据到达时，才认为是[0,5）的桶需要关闭了）*

- watermark的特点

  ![](/watermark的特点.png)

  - watermark是一条特殊的数据记录
  - watermark必须单调递增，以确保任务的事件施加时钟在向前推进，而不是在houtui
  - watermark与数据的时间戳相关

- watermark的传递

  ![](/watermark的传递.png)

  1. 图一，当前Task有四个上游Task给自己传输WaterMark信息，通过比较，只取当前最小值作为自己的本地Event-time clock，上图中，当前Task[0,2)的桶就可关闭了，因为所有上游中2s最小，能保证2s的WaterMark是准确的（所有上游Watermark都已经>=2s)。这时候将Watermark=2广播到当前Task的下游。
  2. 图二，上游的Watermark持续变动，此时Watermark=3成为新的最小值，更新本地Task的event-time clock，同时将最新的Watermark=3广播到下游
  3. 图三，上游的Watermark虽然更新了，但是当前最小值还是3，所以不更新event-time clock，也不需要广播到下游
  4. 图四，和图二同理，更新本地event-time clock，同时向下游广播最新的Watermark=4

  watermark的引入

  - 升序的引入

  对于排好序的数据，不需要延迟触发，可以只指定时间戳就行了

  ```java
   //升序数据设置设置事件时间和watermark
                  .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<SensorReading>() {
                      @Override
                      public long extractAscendingTimestamp(SensorReading sensorReading) {
                          return sensorReading.getTimestap()*1000L;
                      }
                  })
  ```

  AscendingTimestampExtractor实现AssignerWithPeriodicWatermarks接口，watermark为this.currentTimestamp - 1L)

  - 乱序的引入

  Flink 暴露了 TimestampAssigner 接口供我们实现，使我们可以自定义 如何从事件数据中抽取时间戳和生成watermark

  ```java
  StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
  // 设置事件时间语义 env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
  DataStream<SensorReading> dataStream = env.addSource(new SensorSource()) .assignTimestampsAndWatermarks(new MyAssigner());
  ```

  MyAssigner可以有两种类型，都继承自TimestampAssigner

  TimestampAssigner

  ​	定义了抽取时间戳，以及生成 watermark 的方法，有两种类型

  1. AssignerWithPeriodicWatermarks

     ```java
     //自定义周期性时间戳分配器
         public static class MyPeriodicWatermarks implements AssignerWithPeriodicWatermarks<SensorReading>{
     
             private Long bound = 60 * 1000L;//延迟一分钟
             private Long maxTs = Long.MIN_VALUE; //当前最大时间戳
     
     
             @Nullable
             @Override
             public Watermark getCurrentWatermark() {
                 return new Watermark(maxTs - bound);
             }
     
             @Override
             public long extractTimestamp(SensorReading sensorReading, long l) {
                 maxTs = Math.max(maxTs, sensorReading.getTimestap());
                 return sensorReading.getTimestap();
             }
         }
     
     ```

     

     - 周期性的生成 watermark：系统会周期性的将 watermark 插入到流中 

     - 默认周期是200毫秒，可以使用 ExecutionConfig.setAutoWatermarkInterval() 方法进行设置

     - 升序和前面乱序的处理 BoundedOutOfOrdernessTimestampExtractor， 都是基于周期性 watermark 的。

       ```java
       dataStream.assignTimestampsAndWatermarks( new BoundedOutOfOrdernessTimestampExtractor<SensorReading>(Time.milliseconds(1000)) {
         @Override
         public long extractTimestamp(element: SensorReading): Long = { 
           return element.getTimestamp() * 1000L;
         } 
       });
       
       ```

       

  2.  AssignerWithPunctuatedWatermarks

     ```java
      //自定义断点式时间分配器
         public static class MyPunctuatedWatermarks implements AssignerWithPunctuatedWatermarks<SensorReading>{
     
             private Long bound = 60 * 1000L;//延迟一分钟
     
             @Nullable
             @Override
             public Watermark checkAndGetNextWatermark(SensorReading sensorReading, long l) {
                if(sensorReading.getId().equals("sensor_1"))
                    return new Watermark(l -bound);
                else
                    return null;
             }
     
             @Override
             public long extractTimestamp(SensorReading sensorReading, long l) {
                 return sensorReading.getTimestap();
             }
         }
     ```

     

     - 没有时间周期规律，可打断的生成 watermark

  

  我们看到上面的例子中创建了一个看起来有点复杂的类，这个类实现的其实就是分配时间戳的接口。Flink暴露了TimestampAssigner接口供我们实现，使我们可以自定义如何从事件数据中抽取时间戳。


### 1.8.3watermark的设定

![](/watermark的设定.png)

处理乱序方法：

​	Flink对于迟到数据有三层保障，先来后到的保障顺序是：

​		WaterMark => 约等于放宽窗口标准
​		allowedLateness => 允许迟到（ProcessingTime超时，但是EventTime没超时）
​		sideOutputLateData => 超过迟到时间，另外捕获，之后可以自己批处理合并先前的数据

### 1.8.4测试

```java
package com.guigu.apitest.window;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.OutputTag;

import javax.annotation.Nullable;

/**
 * @author ljy
 * @date 2021-9-30 15:35
 * @description 事件时间语义
 */
public class WindowTest3_EventTimeWindow {
    public static void main(String[] args) throws Exception{

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //设置事件时间语义,默认200毫秒
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(100);

        //socket流
        DataStreamSource<String> inputStream = env.socketTextStream("192.168.75.140", 7777);

        //转换成Sensording类型
        SingleOutputStreamOperator<SensorReading> dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {

                String[] fields = s.split(",");
                return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
            }
        })
                //升序数据设置设置事件时间和watermark
                /*.assignTimestampsAndWatermarks(new AscendingTimestampExtractor<SensorReading>() {
                    @Override
                    public long extractAscendingTimestamp(SensorReading sensorReading) {
                        return sensorReading.getTimestap()*1000L;
                    }
                })*/
                //乱序数据设置时间戳和watermark
                .assignTimestampsAndWatermarks(
                        //延迟2秒
                        new BoundedOutOfOrdernessTimestampExtractor<SensorReading>(Time.seconds(2)) {
                    @Override
                    public long extractTimestamp(SensorReading sensorReading) {
                        return sensorReading.getTimestap() * 1000L;
                    }
                });

        /**
         * @// TODO: 2021-10-12
         * 添加侧输出流：
         *  时间：1.延迟(watermark)2s；2.窗口大小15s；3.允许迟到(allowedLateness)1分钟；4.另外捕获(sideOutputLateData)
         */
        OutputTag<SensorReading> outputTag = new OutputTag<SensorReading>("late"){};

        /**
         *
         * @kp 全窗口
         *
         */


        SingleOutputStreamOperator<SensorReading> minStream = dataStream.keyBy("id")
                .timeWindow(Time.seconds(15))
                .allowedLateness(Time.minutes(1))
                .sideOutputLateData(outputTag)
                .minBy("temperature");

        minStream.print("minTemp");
        minStream.getSideOutput(outputTag).print("late");

        env.execute();

    }


}

```

**分析**

1. **计算窗口起始位置Start和结束位置End**

   从`TumblingProcessingTimeWindows`类里的`assignWindows`方法，我们可以得知窗口的起点计算方法如下：

   ```java
   窗口起点start=timestamp−(timestamp−offset+WindowSize)%WindowSize
   ```

   计算得到窗口初始位置为`Start = 1547718195`，那么这个窗口理论上本应该在1547718195+15的位置关闭，也就是`End=1547718210`

   ```java
   //TumblingProcessingTimeWindows类
   public Collection<TimeWindow> assignWindows(Object element, long timestamp, WindowAssignerContext context) {
           long now = context.getCurrentProcessingTime();
           long start = TimeWindow.getWindowStartWithOffset(now, this.offset, this.size);
           return Collections.singletonList(new TimeWindow(start, start + this.size));
       }
   
   //TimeWindow
    public static long getWindowStartWithOffset(long timestamp, long offset, long windowSize) {
           return timestamp - (timestamp - offset + windowSize) % windowSize;
       }
   ```

   ​	offset

2. **计算修正后的window输出结果时间**

   测试代码中Watermark设置的maxOutOfOrderness最大乱序程度是2s，所以实际获取到End+2s的时间戳数据时（达到Watermark），才认为Window需要输出计算的结果（不关闭，因为设置了允许迟到1min）

   所以实际应该是1547718212的数据到来时才触发Window输出计算结果。

   ```java
   //BoundedOutOfOrdernessTimestampExtractor
   public BoundedOutOfOrdernessTimestampExtractor(Time maxOutOfOrderness) {
           if (maxOutOfOrderness.toMilliseconds() < 0L) {
               throw new RuntimeException("Tried to set the maximum allowed lateness to " + maxOutOfOrderness + ". This parameter cannot be negative.");
           } else {
               this.maxOutOfOrderness = maxOutOfOrderness.toMilliseconds();
               this.currentMaxTimestamp = -9223372036854775808L + this.maxOutOfOrderness;
           }
       }
   public final Watermark getCurrentWatermark() {
           long potentialWM = this.currentMaxTimestamp - this.maxOutOfOrderness;
           if (potentialWM >= this.lastEmittedWatermark) {
               this.lastEmittedWatermark = potentialWM;
           }
   
           return new Watermark(this.lastEmittedWatermark);
       }
   ```

3. **env.setParallelism(4);**

   1. **Watermark会向子任务广播**

      我们在map才设置Watermark，map根据Rebalance轮询方式分配数据。所以前4个输入分别到4个slot中，4个slot计算得出的Watermark不同（分别是1547718199-2，1547718201-2，1547718202-2，1547718205-2）

   2. **Watermark传递时，会选择当前接收到的最小一个作为自己的Watermark**

      1. 前4次输入中，有些map子任务还没有接收到数据，所以其下游的keyBy后的slot里watermark就是Long.MIN_VALUE（因为4个上游的Watermark广播最小值就是默认的Long.MIN_VALUE）
      2. 并行度4，在最后4个相同的输入，使得Rebalance到4个map子任务的数据的currentMaxTimestamp都是1547718212，经过getCurrentWatermark()的计算（currentMaxTimestamp-maxOutOfOrderness），4个子任务都计算得到watermark=1547718210，4个map子任务向4个keyBy子任务广播watermark=1547718210，使得keyBy子任务们获取到4个上游的Watermark最小值就是1547718210，然后4个KeyBy子任务都更新自己的Watermark为1547718210。
      3. **根据Watermark的定义，我们认为>=Watermark的数据都已经到达。由于此时watermark >= 窗口End，所以Window输出计算结果（4个子任务，4个结果）**

## 1.9Flink状态管理

### 1.9.1Flink中的状态

![](/flink状态.png)

- 由一个任务维护，并且用来计算某个结果的所有数据，都属于这个任务的状 态 • 

- 可以认为状态就是一个本地变量，可以被任务的业务逻辑访问 

-  Flink 会进行状态管理，包括状态一致性、故障处理以及高效存储和访问，以 便开发人员可以专注于应用程序的逻辑

  按管理分：

  ​	**managerState**

  ​	 **raw**

- 在 Flink 中，状态始终与特定算子相关联 • 

- 为了使运行时的 Flink 了解算子的状态，算子需要预先注册其状态

- 类型分成

  - 算子状态(Operator State):算子状态的作用范围限定为算子任务
  - 检控状态(Keyed State):根据输入数据流中定义的键（key）来维护和访问

### 1.9.2算子状态

​		![](/算子状态.png)

- 算子状态的作用范围限定为算子任务，由同一并行任务所处理的所有数据都 可以访问到相同的状态 • 
- 状态对于同一子任务而言是共享的 • 
- 算子状态不能由相同或不同算子的另一个子任务访问

算子状态数据结构

- 列表状态(List state)：将状态表示为一组数据的列表
- 联合列表状态(Union list state):也将状态表示为数据的列表。它与常规列表状态的区别在于，在发生故 障时，或者从保存点（savepoint）启动应用程序时如何恢复：将全部的列表给予节点自取
- 广播状态（Broadcast state）：如果一个算子有多项任务，而它的每项任务状态又都相同，那么这种特 殊情况最适合应用广播状态。

```java
package com.guigu.apitest.state;

import com.guigu.apitest.source.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.checkpoint.ListCheckpointed;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Collections;
import java.util.List;

/**
 * @author ljy
 * @date 2021-10-12 16:03
 * @description 算子状态
 */
public class OperatorState {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<String> inputStream = env.socketTextStream("192.168.75.140", 7777);

        SingleOutputStreamOperator<SensorReading> dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] fields = s.split(",");
                return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
            }
        });

        SingleOutputStreamOperator<Integer> countStream = dataStream.map(new MyOperator());
        countStream.print();

        env.execute();

    }
    public  static class MyOperator implements MapFunction<SensorReading,Integer>, ListCheckpointed<Integer> {

        //定义一个本地变量作为，算子状态
        private Integer count =0;


        @Override
        public Integer map(SensorReading sensorReading) throws Exception {
            count++;
            return count;
        }

        @Override
        public List<Integer> snapshotState(long l, long l1) throws Exception {
            return Collections.singletonList(count);
        }

        @Override
        public void restoreState(List<Integer> list) throws Exception {
            for (Integer num: list){
                count += num;
            }
        }
    }
}

```



### 1.9.3键控状态

​	<img src="/键控状态.png" style="zoom:60%;" />

- 键控状态是根据输入数据流中定义的键（key）来维护和访问的 • 
- Flink 为每个 key 维护一个状态实例，并将具有相同键的所有数据，都分区到 同一个算子任务中，这个任务会维护和处理这个 key 对应的状态 •
-  当任务处理一条数据时，它会自动将状态的访问范围限定为当前数据的 key

键控状态数据结构

- 值状态（Value state）：将状态表示为单个的值
- 列表状态（List state）：将状态表示为一组数据的列表
- 映射状态（Map state）：将状态表示为一组 Key-Value 对
- 聚合状态（Reducing state & Aggregating State）：将状态表示为一个用于聚合操作的列表
- 声明一个键控状态

```java
 //声明一个键控状态
        private ValueState<Integer> keyCountState;

        @Override
        public void open(Configuration parameters) throws Exception {
            keyCountState = getRuntimeContext().getState(new ValueStateDescriptor<Integer>("key-count", Integer.class));

        }
```



- 读取状态

```java
 //第一次必须赋值
 if(keyCountState.value() == null){
                keyCountState.update(0);
            }
            //读取状态
            Integer count = keyCountState.value();
```



- 对状态赋值

```java
keyCountState.update(10);
```

值状态

```java
package com.guigu.apitest.state;

import com.guigu.apitest.source.beans.SensorReading;
import com.guigu.apitest.transform.TransformTest5_RichFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.checkpoint.ListCheckpointed;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Collections;
import java.util.List;

/**
 * @author ljy
 * @date 2021-10-12 16:03
 * @description 算子状态
 */
public class KeyState {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<String> inputStream = env.socketTextStream("192.168.75.140", 7777);

        SingleOutputStreamOperator<SensorReading> dataStream = inputStream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] fields = s.split(",");
                return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
            }
        });

        SingleOutputStreamOperator<Integer> resultStream = dataStream.keyBy("id")
                .map(new MyKeyCountMapper());
        resultStream.print();

        env.execute();

    }

    /**
     * @// TODO: 2021-10-13 自定义实现RichMapFunction
     */
   public static  class MyKeyCountMapper extends RichMapFunction<SensorReading, Integer> {
        //声明一个键控状态
        private ValueState<Integer> keyCountState;

        @Override
        public void open(Configuration parameters) throws Exception {
            keyCountState = getRuntimeContext().getState(new ValueStateDescriptor<Integer>("key-count", Integer.class));

        }

        @Override
        public Integer map(SensorReading sensorReading) throws Exception {

            if(keyCountState.value() == null){
                keyCountState.update(0);
            }
            //读取状态
            Integer count = keyCountState.value();
            count+=1;
            //对状态赋值
            keyCountState.update(count);
            return count;
        }
    }
}

```



### 1.9.4状态后端

- 每传入一条数据，有状态的算子任务都会读取和更新状态 
- 由于有效的状态访问对于处理数据的低延迟至关重要，因此每个并行 任务都会在本地维护其状态，以确保快速的状态访问 
-  状态的存储、访问以及维护，由一个可插入的组件决定，这个组件就 叫做状态后端（state backend）
-  • 状态后端主要负责两件事：本地的状态管理，以及将检查点 （checkpoint）状态写入远程存储

选择一个状态后端

- MemoryStateBackend

  - 内存级的状态后端，会将键控状态作为内存中的对象进行管理，将它们存储在 TaskManager 的 JVM 堆上，而将 checkpoint 存储在 JobManager 的内存 中
  - 特点：快速、低延迟，但不稳定

  文件配置conf/flink-conf.yml

  ```yaml
  state.backend: hashmap
  
  # 默认为JobManagerCheckpointStorage
  state.checkpoint-storage: jobmanager
  ```

  代码配置

  ```java
  StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
  env.setStateBackend(new HashMapStateBackend());
  env.getCheckpointConfig().setCheckpointStorage(new JobManagerStateBackend());
  
  ```

- FsStateBackend

  - 将 checkpoint 存到远程的持久化文件系统（FileSystem）上，而对于本地状 态，跟 MemoryStateBackend 一样，也会存在 TaskManager 的 JVM 堆上
  - 同时拥有内存级的本地访问速度，和更好的容错保证

  文件配置conf/flink-conf.yml

  ```yaml
  state.backend: hashmap
  state.checkpoints.dir: file:///checkpoint-dir/
  
  # 默认为FileSystemCheckpointStorage
  state.checkpoint-storage: filesystem
  
  ```

  代码配置

  ```java
  StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
  env.setStateBackend(new HashMapStateBackend());
  env.getCheckpointConfig().setCheckpointStorage("file:///checkpoint-dir");
  
  // 高级 FsStateBackend 配置，例如写缓冲区大小，可以通过手动实例化 FileSystemCheckpointStorage 对象来设置。
  env.getCheckpointConfig().setCheckpointStorage(new FileSystemCheckpointStorage("file:///checkpoint-dir"));
  ```

-  RocksDBStateBackend

  - 将所有状态序列化后，存入本地的 RocksDB 中存储。RocksDBStateBackend 是唯一支持增量快照的状态后端

  文件配置conf/flink-conf.yml

  ```yaml
  state.backend: rocksdb
  state.checkpoints.dir: file:///checkpoint-dir/
  
  # 默认为FileSystemCheckpointStorage
  state.checkpoint-storage: filesystem
  
  ```

  代码配置

  ```java
  StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
  env.setStateBackend(new EmbeddedRocksDBStateBackend());
  env.getCheckpointConfig().setCheckpointStorage("file:///checkpoint-dir");
  
  // 高级 FsStateBackend 配置，例如写缓冲区大小，可以通过手动实例化 FileSystemCheckpointStorage 对象来设置。
  env.getCheckpointConfig().setCheckpointStorage(new FileSystemCheckpointStorage("file:///checkpoint-dir"));
  ```

  

