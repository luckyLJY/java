## 软件启动命令
1. mysql
    ```bash
    # 启动Mysql服务
    docker run -p 3306:3306 --name mysql \
    -v /mydata/mysql/log:/var/log/mysql \
    -v /mydata/mysql/data:/var/lib/mysql \
    -v /mydata/mysql/conf:/etc/mysql \
    -e MYSQL_ROOT_PASSWORD=root  \
    -d mysql:5.7
   # 进入容器
   docker exec -it mysql /bin/bash
   # 拷贝文件
   dokcer cp /mydata/mall.sql mysql:/
   ```
   
2. redis
    ```bash
    # 启动redis服务
    docker run -p 6379:6379 --name redis \
    -v /mydata/redis/data:/data \
    -d redis:5 redis-server --appendonly yes

   # Redis容器使用redis-cli命令进行连接
   docker exec -it redis redis-cli
   ```
3. nginx
    ```bash
   # 启动nginx
   docker run -p 80:80 --name nginx \
    -v /mydata/nginx/html:/usr/share/nginx/html \
    -v /mydata/nginx/logs:/var/log/nginx  \
    -d nginx:1.10

   ```
4. RabbitMQ
    ```bash
   # 命令启动RabbitMQ服务
   docker run -p 5672:5672 -p 15672:15672 --name rabbitmq \
    -d rabbitmq:3.7.15
   
   # 进入容器并开启管理功能
    docker exec -it rabbitmq /bin/bash
    rabbitmq-plugins enable rabbitmq_management
   ```
5. es
    ```bash
   docker run -p 9200:9200 -p 9300:9300 --name elasticsearch \
    -e "discovery.type=single-node" \
    -e "cluster.name=elasticsearch" \
    -v /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
    -v /mydata/elasticsearch/data:/usr/share/elasticsearch/data \
    -d elasticsearch:7.6.2

   # 进入es 应用
   docker exec -it elasticsearch /bin/bash
   ```
6. logstach
    ```bash
   docker run --name logstash -p 4560:4560 -p 4561:4561 -p 4562:4562 -p 4563:4563 \
    --link elasticsearch:es \
    -v /mydata/logstash/logstash.conf:/usr/share/logstash/pipeline/logstash.conf \
    -d logstash:7.6.2

   ```
7. Kibana安装
    ```bash
    docker run --name kibana -p 5601:5601 \
    --link elasticsearch:es \
    -e "elasticsearch.hosts=http://es:9200" \
    -d kibana:7.6.2

   ```
8. MongoDB安装
    ```bash
   docker run -p 27017:27017 --name mongo \
    -v /mydata/mongo/db:/data/db \
    -d mongo:4.2.5

   ```
9. registry
    ```bash
   docker run -d -p 5000:5000 --name registry registry:2 
   ```
## 问题解决
1.


    ```text
    # 问题
    docker: failed to register layer ... no space left on device
    #解决
    curl -s https://raw.githubusercontent.com/ZZROTDesign/docker-clean/v2.0.4/docker-clean |
    sudo tee /usr/local/bin/docker-clean > /dev/null && \
    sudo chmod +x /usr/local/bin/docker-clean
    docker-clean
    ```
2. 
    

    ```text
    # 问题
    解决/var/lib/docker空间不足问题

    #解决
    1.df -h
    2./var/lib/docker/overlay2把根目录挂载的磁盘全部写满了
    3.停止docker服务
    systemctl stop docker
    4.创建新的docker工作目录
    mkdir -p /root/dockerlib
    5.迁移/var/lib/docker
    6.配置devicemapper.conf
    # 不存在就创建
    sudo mkdir -p /etc/systemd/system/docker.service.d/
    # 不存在就创建
    sudo vi /etc/systemd/system/docker.service.d/devicemapper.conf
    [Service]
    ExecStart=
    ExecStart=/usr/bin/dockerd  --graph=/root/dockerlib
    7.重启docker服务
    systemctl daemon-reload
    systemctl restart docker
    systemctl enable docker
    8.确认是否配置成功
    docker info 
    Docker Root Dir: /root/dockerlib
    9.重新启动所有容器后，确认无误。即可删除/var/lib/docker里面所有文件。
    10.处理完毕后，执行
    ```

3.


   ```text
   es7.6.2与springboot 2.2.0以上版本匹配
   ```


虚拟机：
root: lucky_ljy811834

LJY:  lucky_811834   


开启防火墙  
firewall-cmd --zone=public --add-port=15672/tcp --permanent  
firewall-cmd --reload  

rabbitmq插件
```text
下载地址：https://www.rabbitmq.com/community-plugins.html  
docker cp /root/rabbitmq_delayed_message_exchange-3.9.0.ez rabbit:/plugins
启用插件：rabbitmq-plugins enable rabbitmq_delayed_message_exchange
```