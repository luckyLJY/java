# hbase集群搭建

## 1.1下载HBASE

地址https://hbase.apache.org/downloads.html

使用上传安装包到/export/soft

使用命令解压

```shell
tar -zvxf hbase-2.4.6-bin.tar.gz -C ../servers/
```

## 1.2修改配置

conf/hbase-env.sh

```sh
export JAVA_HOME=/export/servers/jdk1.8.0_141#Java的环境
注释以下两个
# export HBASE_MASTER_POTS
# export HBASE_REGIONSERVER_OPTS  
export HBASE_CLASSPATH=/export/servers/hbase-2.4.6/conf   #hadoop配置文件的地址
export HBASE_MANAGES_ZK=false # 此配置信息，设置由独立的zk集群管理，故为false
export HBASE_LOG_DIR=/opt/hbase/logs #Hbase日志目录
```

conf/hbase-site.xml

```xml
<configuration>
    <!--******核心配置，必须配置********-->
    <property>
        <name>hbase.rootdir</name>
        <value>hdfs://node01:9000/hbase</value>
        <description>指定Region服务器共享的目录，用来持久存储HBase的数据，URL必须完全正确，其中包含了文件系统的schema。默认值"${hbase.tmp.dir}/hbase"</description>
    </property>

  <!--HBase的运行模式，false是单机模式，true是分布式模式，若为false则hbase和zk回运行在一个jvm中-->
    <property>
        <name>hbase.cluster.distributed</name>
        <value>true</value>
    </property>

  <!--本地文件的存放目录-->
    <property>
        <name>hbase.tmp.dir</name>
        <value>./tmp</value>
    </property>

    <property>
        <name>hbase.unsafe.stream.capability.enforce</name>
        <value>false</value>
    </property>

    <property>
         <name>hbase.zookeeper.quorum</name>
         <value>node01:2181,node02:2181,node03:2181</value>
         <description>配置zookeeper集群地址,不要指定znode路径,HBase会默认将元数据放在根znode</description>
    </property>
   <!--指定Zookeeper数据存储目录-->
    <property>
       <name>hbase.zookeeper.property.dataDir</name>
       <value>/export/servers/zookeeper-3.4.9/zkdatas</value>
    </property>
    <!-- ********** HMaster相关配置 ********** -->
    <property>
      <name>hbase.master.info.bindAddress</name>
      <value>hadoop102</value>
      <description>HBase Master 的 Web UI绑定的地址,默认值为"0.0.0.0"</description>
    </property>

    <property>
      <name>hbase.master.port</name>
      <value>16000</value>
      <description>HBase Master绑定端口</description>
    </property>

    <property>
       <name>hbase.master.info.port</name>
       <value>16010</value>
       <description>HBase Master的Web UI端口，默认值为:"16010",如果不想启动UI实例，则可以将当前参数设置为-1</description>
    </property>

       <!-- ********** HRegionServer相关配置 ********** -->
     <property>
        <name>hbase.regionserver.port</name>
        <value>16020</value>
        <description>HBase RegionServer绑定的端口，默认值为:"16020".</description>
     </property>

     <property>
        <name>hbase.regionserver.info.port</name>
        <value>16030</value>
        <description>HBase RegionServer的Web UI端口，默认值为:"16030"设置为-1可以禁用HBase RegionServer的Web UI。</description>
        </property>

     <property>
         <name>hbase.regionserver.info.bindAddress</name>
         <value>0.0.0.0</value>
         <description>HBase RegionServer的Web UI地址,默认值为"0.0.0.0"</description>
      </property>

</configuration>


```

conf/regionserrvers

```
node01
node02
node03

```

建立软连接

```shell
ln -s /export/servers/hadoop-3.1.1/etc/hadoop/core-site.xml  /export/servers/hbase-2.3.6/conf/core-site.xml
ln -s /export/servers/hadoop-3.1.1/etc/hadoop/hdfs-site.xml  /export/servers/hbase-2.3.6/conf/hdfs-site.xml
```

分发Hbase

```sh
xsync hbase-2.4.6/
scp hbase-2.4.6/ node02:$PWD
```

启动集群

```sh
删除cmd
rm -rf *.cmd*
单节点启动
hbase-daemon.sh
hbase-daemons.sh
集群启动
start-hbase.sh
stop-hbase.sh
使用shell
hbase
```

