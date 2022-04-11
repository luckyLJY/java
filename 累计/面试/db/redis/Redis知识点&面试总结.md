## 简单介绍一下Redis
简单来说 Redis 就是一个使用 C 语言开发的数据库，不过与传统数据库不同的是 Redis 的数据是存在内存中的 ，也就是它是内存数据库，所以读写速度非常快，因此 Redis 被广泛应用于缓存方向。

另外，Redis 除了做缓存之外，也经常用来做分布式锁，甚至是消息队列。

Redis 提供了多种数据类型来支持不同的业务场景。Redis 还支持事务 、持久化、Lua 脚本、多种集群方案。
## 缓存数据的处理流程是怎样的？
1. 如果用户请求的数据在缓存中就直接返回。
2. 缓存中不存在的话就看数据库中是否存在。
3. 数据库中不存在的话就更新缓存中的数据。
4. 数据库中不存在的话就返回空数据。
## 为什么要用Redis/为什么要用缓存？
### 高性能
热点数据存储在缓存中容易使用。
### 高并发
直接操作缓存能够承受的数据库请求数量是远远大于直接访问数据库的，所以我们可以考虑把数据库中的部分数据转移到缓存中去，这样用户的一部分请求会直接到缓存这里而不用经过数据库。进而，我们也就提高了系统整体的并发。
## Redis除了缓存，还能做什么？
- 分布式锁:通过Redis来做分布式锁是一种比较场景的方式。通常情况下，我们都是基于 Redisson 来实现分布式锁。相关阅读：[《分布式锁中的王者方案 - Redisson》](https://mp.weixin.qq.com/s/CbnPRfvq4m1sqo2uKI6qQw)
- 限流:一般是通过 Redis + Lua 脚本的方式来实现限流。相关阅读：[《我司用了 6 年的 Redis 分布式限流器，可以说是非常厉害了！》](https://mp.weixin.qq.com/s/kyFAWH3mVNJvurQDt4vchA)
- 消息队列:Redis 自带的 list 数据结构可以作为一个简单的队列使用。Redis5.0 中增加的 Stream 类型的数据结构更加适合用来做消息队列。它比较类似于 Kafka，有主题和消费组的概念，支持消息持久化以及 ACK 机制。
- 复杂业务场景：通过 Redis 以及 Redis 扩展（比如 Redisson）提供的数据结构，我们可以很方便地完成很多复杂的业务场景比如通过 bitmap 统计活跃用户、通过 sorted set 维护排行榜。
## Redis常见数据结构以及使用场景分析
### String
1. 介绍：string 数据结构是简单的 key-value 类型。虽然 Redis 是用 C 语言写的，但是 Redis 并没有使用 C 的字符串表示，而是自己构建了一种 简单动态字符串（simple dynamic string，SDS）。相比于 C 的原生字符串，Redis 的 SDS 不光可以保存文本数据还可以保存二进制数据，并且获取字符串长度复杂度为 O(1)（C 字符串为 O(N)）,除此之外，Redis 的 SDS API 是安全的，不会造成缓冲区溢出。  
2. 常用命令：set,get,strlen,exists,descr,incr,setex等等。  
3. 应用场景： 一般常用在需要计数的场景，比如用户的访问次数、热点文章的点赞转发数量等等。

- 普通字符串的基本操作
    ```sh
    127.0.0.1:6379> set key value #设置 key-value 类型的值
    OK
    127.0.0.1:6379> get key # 根据 key 获得对应的 value
    "value"
    127.0.0.1:6379> exists key  # 判断某个 key 是否存在
    (integer) 1
    127.0.0.1:6379> strlen key # 返回 key 所储存的字符串值的长度。
    (integer) 5
    127.0.0.1:6379> del key # 删除某个 key 对应的值
    (integer) 1
    127.0.0.1:6379> get key
    (nil)
    ```
- 批量设置
    ```sh
    127.0.0.1:6379> mset key1 value1 key2 value2 # 批量设置 key-value 类型的值
    OK
    127.0.0.1:6379> mget key1 key2 # 批量获取多个 key 对应的 value
    1) "value1"
    2) "value2"
    ```
- 计数器(字符串的内容为整数的时候可以使用)
    ```sh
    127.0.0.1:6379> set number 1
    OK
    127.0.0.1:6379> incr number # 将 key 中储存的数字值增一
    (integer) 2
    127.0.0.1:6379> get number
    "2"
    127.0.0.1:6379> decr number # 将 key 中储存的数字值减一
    (integer) 1
    127.0.0.1:6379> get number
    "1"
    ```
- 过期(默认为永不过期)
    ```sh
    127.0.0.1:6379> expire key  60 # 数据在 60s 后过期
    (integer) 1
    127.0.0.1:6379> setex key 60 value # 数据在 60s 后过期 (setex:[set] + [ex]pire)
    OK
    127.0.0.1:6379> ttl key # 查看数据还有多久过期
    (integer) 56
    ```
### list
1. 介绍 ：list 即是 链表。链表是一种非常常见的数据结构，特点是易于数据元素的插入和删除并且可以灵活调整链表长度，但是链表的随机访问困难。许多高级编程语言都内置了链表的实现比如 Java 中的 LinkedList，但是 C 语言并没有实现链表，所以 Redis 实现了自己的链表数据结构。Redis 的 list 的实现为一个 双向链表，即可以支持反向查找和遍历，更方便操作，不过带来了部分额外的内存开销。
2. 常用命令：rpush,lpop,lpush,rpop,lrange,llen等。
3. 应用场景: 发布与订阅或者说消息队列、慢查询。

- 通过rpush/lpop实现队列
    ```sh
    127.0.0.1:6379> rpush myList value1 # 向 list 的头部（右边）添加元素
    (integer) 1
    127.0.0.1:6379> rpush myList value2 value3 # 向list的头部（最右边）添加多个元素
    (integer) 3
    127.0.0.1:6379> lpop myList # 将 list的尾部(最左边)元素取出
    "value1"
    127.0.0.1:6379> lrange myList 0 1 # 查看对应下标的list列表， 0 为 start,1为 end
    1) "value2"
    2) "value3"
    127.0.0.1:6379> lrange myList 0 -1 # 查看列表中的所有元素，-1表示倒数第一
    1) "value2"
    2) "value3"
    ```
- 通过rpush/rpop实现栈
    ```sh
    127.0.0.1:6379> rpush myList2 value1 value2 value3
    (integer) 3
    127.0.0.1:6379> rpop myList2 # 将 list的头部(最右边)元素取出
    "value3"
    ```
- 通过lrange查看对应下标范围的列表元素
    ```sh
    127.0.0.1:6379> rpush myList value1 value2 value3
    (integer) 3
    127.0.0.1:6379> lrange myList 0 1 # 查看对应下标的list列表， 0 为 start,1为 end
    1) "value1"
    2) "value2"
    127.0.0.1:6379> lrange myList 0 -1 # 查看列表中的所有元素，-1表示倒数第一
    1) "value1"
    2) "value2"
    3) "value3"
    ```
通过lrange命令，你可以基于list实现分页查询，性能非常高。
- 通过llen查看链表长度
    ```sh
    127.0.0.1:6379> llen myList
    (integer) 3
    ```
### hash
1. 介绍：hash类似于JDK1.8前HashMap,内部实现也差不多(数组+链表)。不过，Redis的hash做了更多优化。另外，hash是一个String类型的field和value的映射表，特别适合用于存储对象，后续操作的时候，你可以直接仅仅修改这个对象中的某个字段的值。比如我们可以hash数据结构来存储用用户信息，商品信息等等。  
2. 常用命令：hset,hmset,hexists,hget,hgetall,hkeys,hvals等。
3. 应用场景：系统中的对象数据的存储。
- 使用
    ```sh
    127.0.0.1:6379> hmset userInfoKey name "guide" description "dev" age "24"
    OK
    127.0.0.1:6379> hexists userInfoKey name # 查看 key 对应的 value中指定的字段是否存在。
    (integer) 1
    127.0.0.1:6379> hget userInfoKey name # 获取存储在哈希表中指定字段的值。
    "guide"
    127.0.0.1:6379> hget userInfoKey age
    "24"
    127.0.0.1:6379> hgetall userInfoKey # 获取在哈希表中指定 key 的所有字段和值
    1) "name"
    2) "guide"
    3) "description"
    4) "dev"
    5) "age"
    6) "24"
    127.0.0.1:6379> hkeys userInfoKey # 获取 key 列表
    1) "name"
    2) "description"
    3) "age"
    127.0.0.1:6379> hvals userInfoKey # 获取 value 列表
    1) "guide"
    2) "dev"
    3) "24"
    127.0.0.1:6379> hset userInfoKey name "GuideGeGe" # 修改某个字段对应的值
    127.0.0.1:6379> hget userInfoKey name
    "GuideGeGe"
    ```
### set
1. 介绍：set 类似于 Java 中的 HashSet 。Redis 中的 set 类型是一种无序集合，集合中的元素没有先后顺序。当你需要存储一个列表数据，又不希望出现重复数据时，set 是一个很好的选择，并且 set 提供了判断某个成员是否在一个 set 集合内的重要接口，这个也是 list 所不能提供的。可以基于 set 轻易实现交集、并集、差集的操作。比如：你可以将一个用户所有的关注人存在一个集合中，将其所有粉丝存在一个集合。Redis 可以非常方便的实现如共同关注、共同粉丝、共同喜好等功能。这个过程也就是求交集的过程。
2. sadd,spop,smembers.sismember,scard,sinterstore,sunion等。
3. 应用场景：需要存放的数据不能重复以及需要获取多个数据源交集和并集等场景。  
- 使用
    ```sh
    127.0.0.1:6379> sadd mySet value1 value2 # 添加元素进去
    (integer) 2
    127.0.0.1:6379> sadd mySet value1 # 不允许有重复元素
    (integer) 0
    127.0.0.1:6379> smembers mySet # 查看 set 中所有的元素
    1) "value1"
    2) "value2"
    127.0.0.1:6379> scard mySet # 查看 set 的长度
    (integer) 2
    127.0.0.1:6379> sismember mySet value1 # 检查某个元素是否存在set 中，只能接收单个元素
    (integer) 1
    127.0.0.1:6379> sadd mySet2 value2 value3
    (integer) 2
    127.0.0.1:6379> sinterstore mySet3 mySet mySet2 # 获取 mySet 和 mySet2 的交集并存放在 mySet3 中
    (integer) 1
    127.0.0.1:6379> smembers mySet3
    1) "value2"
    ```
### sorted set
1. 介绍：和 set 相比，sorted set 增加了一个权重参数 score，使得集合中的元素能够按 score 进行有序排列，还可以通过 score 的范围来获取元素的列表。有点像是 Java 中 HashMap 和 TreeSet 的结合体。
2. 常用命令：zadd,zcard,zscore,zrange,zrevrange,zrem 等。
3. 应用场景：需要对数据根据某个权重进行排序的场景。比如在直播系统中，实时排行信息包含直播间在线用户列表，各种礼物排行榜，弹幕消息（可以理解为按消息维度的消息排行榜）等信息。
- 使用
    ```sh
    127.0.0.1:6379> zadd myZset 3.0 value1 # 添加元素到 sorted set 中 3.0 为权重
    (integer) 1
    127.0.0.1:6379> zadd myZset 2.0 value2 1.0 value3 # 一次添加多个元素
    (integer) 2
    127.0.0.1:6379> zcard myZset # 查看 sorted set 中的元素数量
    (integer) 3
    127.0.0.1:6379> zscore myZset value1 # 查看某个 value 的权重
    "3"
    127.0.0.1:6379> zrange  myZset 0 -1 # 顺序输出某个范围区间的元素，0 -1 表示输出所有元素
    1) "value3"
    2) "value2"
    3) "value1"
    127.0.0.1:6379> zrange  myZset 0 1 # 顺序输出某个范围区间的元素，0 为 start  1 为 stop
    1) "value3"
    2) "value2"
    127.0.0.1:6379> zrevrange  myZset 0 1 # 逆序输出某个范围区间的元素，0 为 start  1 为 stop
    1) "value1"
    2) "value2"
    ```
### bitmap
1. 介绍：bitmap 存储的是连续的二进制数字（0 和 1），通过 bitmap, 只需要一个 bit 位来表示某个元素对应的值或者状态，key 就是对应元素本身 。我们知道 8 个 bit 可以组成一个 byte，所以 bitmap 本身会极大的节省储存空间。
2. 常用命令：setbit、getbit、bitcount、bittop
3. 应用场景：适合需要保存状态信息（比如是否签到、是否登录...）并需要进一步对这些信息进行分析的场景。比如用户签到情况、活跃用户情况、用户行为统计（比如是否点赞过某个视频）。
- 使用
    ```sh
    # SETBIT 会返回之前位的值（默认是 0）这里会生成 7 个位
    127.0.0.1:6379> setbit mykey 7 1
    (integer) 0
    127.0.0.1:6379> setbit mykey 7 0
    (integer) 1
    127.0.0.1:6379> getbit mykey 7
    (integer) 0
    127.0.0.1:6379> setbit mykey 6 1
    (integer) 0
    127.0.0.1:6379> setbit mykey 8 1
    (integer) 0
    # 通过 bitcount 统计被被设置为 1 的位的数量。
    127.0.0.1:6379> bitcount mykey
    (integer) 2
    ```
### 场景分析
- 使用场景一：用户行为分析很多网站为了分析你的喜好，需要研究你点赞过的内容。
    ```sh
    # 记录你喜欢过 001 号小姐姐
    127.0.0.1:6379> setbit beauty_girl_001 uid 1
    ```

- 使用场景二：统计活跃用户   
使用时间作为key，然后用户ID为offset，如果当日活跃过就设置为1   
那么我该如何计算某几天/月/年的活跃用户呢(暂且约定，统计时间内只要有一天在线就称为活跃)   
    ```sh
    # 对一个或多个保存二进制位的字符串 key 进行位元操作，并将结果保存到 destkey 上。
    # BITOP 命令支持 AND 、 OR 、 NOT 、 XOR 这四种操作中的任意一种参数
    BITOP operation destkey key [key ...]
    ```

    初始化数据：  
    ```sh
    127.0.0.1:6379> setbit 20210308 1 1
    (integer) 0
    127.0.0.1:6379> setbit 20210308 2 1
    (integer) 0
    127.0.0.1:6379> setbit 20210309 1 1
    (integer) 0
    ```

    统计 20210308~20210309 总活跃用户数: 1   
    ```sh
    127.0.0.1:6379> bitop and desk1 20210308 20210309
    (integer) 1
    127.0.0.1:6379> bitcount desk1
    (integer) 1
    ```
    统计 20210308~20210309 在线活跃用户数: 2  
    ```sh
    127.0.0.1:6379> bitop or desk2 20210308 20210309
    (integer) 1
    127.0.0.1:6379> bitcount desk2
    (integer) 2
    ```
- 使用场景三：用户在线状态  
对于获取或者统计用户在线状态，使用bitmap时一个节约空间且效率又高的一种方法。  
只需要一个key，用户的ID为offset,如果在线就设置为1，不在线就设置为0。  

## Redis单线程模型详解
Redis基于Reactor模式来设计开发了自己的一套高效的事件处理模型(Netty的线程模型也是基于Reactor模式，Reactor不愧是高性能IO的基石)，这套事件处理模型对应的是Redis中的文件事件处理器(file event handler)。由于文件处理器是单线程运行的，所以一般说Redis是单线程模型。  
单线程采用IO多路复用程序来监听来自客户端的大量连接(或者说监听多个socket),它会将感兴趣的事件及类型(读、写)注册到内核并监听每个事件的发生。  

这样的好处非常明显：I/O多路复用技术的使用不需要让Redis额外创建多余的线程来监听客户端大量的连接，降低了资源的消耗(和NIO中的Selector组件很像)。

另外，Redis服务器是一个事件处理程序，服务器需要处理两类事件：文件事件；时间事件。  
《Redis 设计与实现》  
>Redis 基于 Reactor 模式开发了自己的网络事件处理器：这个处理器被称为文件事件处理器（file event handler）。文件事件处理器使用 I/O 多路复用（multiplexing）程序来同时监听多个套接字，并根据套接字目前执行的任务来为套接字关联不同的事件处理器。
>当被监听的套接字准备好执行连接应答（accept）、读取（read）、写入（write）、关 闭（close）等操作时，与操作相对应的文件事件就会产生，这时文件事件处理器就会调用套接字之前关联好的事件处理器来处理这些事件。
>虽然文件事件处理器以单线程方式运行，但通过使用 I/O 多路复用程序来监听多个套接字，文件事件处理器既实现了高性能的网络通信模型，又可以很好地与 Redis 服务器中其他同样以单线程方式运行的模块进行对接，这保持了 Redis 内部单线程设计的简单性

文件事件处理器包含以下4个部分：   
- 多个socket(客户端连接)
- IO多路复用程序(支持多个客户端连接的关键)
- 文件事件分派器(将socket关联到相应的文件处理器)
- 事件处理器(连接应答处理器、命令请求处理器、命令恢复处理器)

![](./img_redis/redis%E5%A4%84%E7%90%86%E5%99%A8.PNG)
## Redis6.0引入多线程
Redis6.0引入多线程主要是为了提高网络IO读写性能。

Redis6.0的多线程是禁用的，只使用主线程。如需开启需要修改 redis 配置文件 redis.conf ：  
```sh
io-threads-do-reads yes
```
开启多线程后，还需要设置线程数，否则是不生效的。同样需要修改 redis 配置文件 redis.conf :  
```sh
io-threads 4 #官网建议4核的机器建议设置为2或3个线程，8核的建议设置为6个线程
```
## Redis给缓存数据设置过期时间的作用
因为内存有限，不断的缓存数据，容易导致out of memory。   
Redis自带有给缓存数据设置过期时间的功能：    
```sh
127.0.0.1:6379> exp key 60 # 数据在 60s 后过期
(integer) 1
127.0.0.1:6379> setex key 60 value # 数据在 60s 后过期 (setex:[set] + [ex]pire)
OK
127.0.0.1:6379> ttl key # 查看数据还有多久过期
(integer) 56
```
注意：Redis中除了字符串类型有自己独有设置过期时间的命令setex外，其他方法都需要依靠expire命令来设置过期时间。另外，persist名可以移除一个键的过期时间。   
很多时候，我们的业务场景就是需要某个数据只在某一时间段内存在，比如我们的短信验证码可能只在 1 分钟内有效，用户登录的 token 可能只在 1 天内有效。
## Redis如何判断数据是否过期
Redis通过一个叫做过期字典(可以看作是hash表)来保存数据的过期时间。过期字典的键指向Redis数据库中的某个key(键)，过期字典的值是一个long long类型的整数，这个整数保存了key所指向的数据库键的过期时间（毫秒精度的 UNIX 时间戳）。

![](./img_redis/redis%E8%BF%87%E6%9C%9F.PNG)
过期字典是存储在redisDb这个结构里的：
```c
typedef struct redisDb {
    ...

    dict *dict;     //数据库键空间,保存着数据库中所有键值对
    dict *expires   // 过期字典,保存着键的过期时间
    ...
} redisDb;
```
## 过期数据的删除策略

常用过期数据删除策略：
- 惰性删除：只会对取出key的时候才对数据进行过期检查。   
- 定期删除：每个一段时间抽取一批Key执行删除过期key操作。

redis采用内存淘汰机制。

## Redis内存淘汰机制

Reids6种数据淘汰策略：  

    1. volatile-lru (least recently used):从已设置过期时间的数据集(server.db[i].expires)中挑选最少使用的数据淘汰。    
    2. volatile-ttl:从已设置过期时间的数据集(server.db[i].expires)中挑选将要过期的数据淘汰。
    3. volatile-random:从已设置过期时间的数据集(server.db[i].expires)中挑选任意数据淘汰。
    4. allkeys-lru(least recently used):当内存不足以容纳新写入数据时，在键空间中，移除最近最少使用的key(这个最长使用)。
    5. allkeys-random:从数据集(server.db[i].expires)中任意选择数据淘汰。
    6. no-eviction:禁止驱逐数据，也就是说当内存不足以容纳新写入的数据时，新写入操作会报错。

4.0版本后增加一下两种：  

    7. volative-lfu(least frequently used):从已设置过期时间的数据集(server.db[i].expires)中挑选最不经常使用的数据淘汰。
    8. allkeys-lfu(least frequently used):当内存不足以容纳新写入数据时，在键空间中，移除最不常使用的key。

## Redis持久化机制
1. 快照(RDB)   
    redis可以通过创建快照来获得存储在内存里面的数据在某个时间点上的副本。redis创建快照之后，可以对快照进行备份，可以将快照副本复制到其他服务器从而创建具有相同数据的服务器副本(Redis主从结构，主要用来提高Redis性能)，还可以将快照留在原地以便重启服务器的时候使用。

    快照持久化是Redis的默认采用的持久化方式，在Redis.conf配置文件中默认有以下配置：
    ```yml
    save 900 1           #在900秒(15分钟)之后，如果至少有1个key发生变化，Redis就会自动触发BGSAVE命令创建快照。

    save 300 10          #在300秒(5分钟)之后，如果至少有10个key发生变化，Redis就会自动触发BGSAVE命令创建快照。

    save 60 10000        #在60秒(1分钟)之后，如果至少有10000个key发生变化，Redis就会自动触发BGSAVE命令创建快照。
    ```
    
2. 追加文件(AOF)
    与快照持久化相比，AOF持久化的实时性更好，已成为主流的持久化方案。默认情况下Redis没有开启AOF方式的持久化，可以通过appendonly参数开启：  
    ```yml
    appendonly yes
    ```

    开启AOF持久化后每执行一条会更改Redis中数据的命令，Redis就会将该命令写入到内存缓存server.aof_buf中，然后再根据appendfsync配置来决定何时将其同步到硬盘中的AOF文件

    AOF保存文件的位置和RDB保存文件的位置相同，都是通过dir参数设置的，默认的文件名为appendonly.aof 。

    在Redis的配置文件中存在三种不同的AOF持久化方式，它们分别是：
    ```yml
    appendfsync always    #每次有数据修改发生时都会写入AOF文件,这样会严重降低Redis的速度
    appendfsync everysec  #每秒钟同步一次，显式地将多个写命令同步到硬盘
    appendfsync no        #让操作系统决定何时进行同步
    ```
    为了兼顾数据和写入性能，用户可以考虑 appendfsync everysec 选项 ，让 Redis 每秒同步一次 AOF 文件，Redis 性能几乎没受到任何影响。而且这样即使出现系统崩溃，用户最多只会丢失一秒之内产生的数据。当硬盘忙于执行写入操作的时候，Redis 还会优雅的放慢自己的速度以便适应硬盘的最大写入速度。为了兼顾数据和写入性能，用户可以考虑 appendfsync everysec 选项 ，让 Redis 每秒同步一次 AOF 文件，Redis 性能几乎没受到任何影响。而且这样即使出现系统崩溃，用户最多只会丢失一秒之内产生的数据。当硬盘忙于执行写入操作的时候，Redis 还会优雅的放慢自己的速度以便适应硬盘的最大写入速度。

    AOF重写   
    AOF 重写可以产生一个新的 AOF 文件，这个新的 AOF 文件和原有的 AOF 文件所保存的数据库状态一样，但体积更小。

    AOF 重写是一个有歧义的名字，该功能是通过读取数据库中的键值对来实现的，程序无须对现有 AOF 文件进行任何读入、分析或者写入操作。

    在执行 BGREWRITEAOF 命令时，Redis 服务器会维护一个 AOF 重写缓冲区，该缓冲区会在子进程创建新 AOF 文件期间，记录服务器执行的所有写命令。当子进程完成创建新 AOF 文件的工作之后，服务器会将重写缓冲区中的所有内容追加到新 AOF 文件的末尾，使得新的 AOF 文件保存的数据库状态与现有的数据库状态一致。最后，服务器用新的 AOF 文件替换旧的 AOF 文件，以此来完成 AOF 文件重写操作。

3. Redis 4.0 对于持久化机制的优化
    Redis 4.0 开始支持 RDB 和 AOF 的混合持久化（默认关闭，可以通过配置项 aof-use-rdb-preamble 开启）。

    如果把混合持久化打开，AOF 重写的时候就直接把 RDB 的内容写到 AOF 文件开头。这样做的好处是可以结合 RDB 和 AOF 的优点, 快速加载同时避免丢失过多的数据。当然缺点也是有的， AOF 里面的 RDB 部分是压缩格式不再是 AOF 格式，可读性较差。

## Redis bigkey
什么是 bigkey？  
简单来说，如果一个 key 对应的 value 所占用的内存比较大，那这个 key 就可以看作是 bigkey。具体多大才算大呢？有一个不是特别精确的参考标准：string 类型的 value 超过 10 kb，复合类型的 value 包含的元素超过 5000 个（对于复合类型的 value 来说，不一定包含的元素越多，占用的内存就越多）

bigkey 有什么危害？  
除了会消耗更多的内存空间，bigkey 对性能也会有比较大的影响。

如何发现bigkey?  
1. 使用Redis自带的--bigkeys参数来查找    
```sh
# redis-cli -p 6379 --bigkeys

# Scanning the entire keyspace to find biggest keys as well as
# average sizes per key type.  You can use -i 0.1 to sleep 0.1 sec
# per 100 SCAN commands (not usually needed).

[00.00%] Biggest string found so far '"ballcat:oauth:refresh_auth:f6cdb384-9a9d-4f2f-af01-dc3f28057c20"' with 4437 bytes
[00.00%] Biggest list   found so far '"my-list"' with 17 items

-------- summary -------

Sampled 5 keys in the keyspace!
Total key length in bytes is 264 (avg len 52.80)

Biggest   list found '"my-list"' has 17 items
Biggest string found '"ballcat:oauth:refresh_auth:f6cdb384-9a9d-4f2f-af01-dc3f28057c20"' has 4437 bytes

1 lists with 17 items (20.00% of keys, avg size 17.00)
0 hashs with 0 fields (00.00% of keys, avg size 0.00)
4 strings with 4831 bytes (80.00% of keys, avg size 1207.75)
0 streams with 0 entries (00.00% of keys, avg size 0.00)
0 sets with 0 members (00.00% of keys, avg size 0.00)
0 zsets with 0 members (00.00% of keys, avg size 0.00
```
从这个命令的运行结果，我们可以看出：这个命令会扫描(Scan) Redis 中的所有 key ，会对 Redis 的性能有一点影响。并且，这种方式只能找出每种数据结构 top 1 bigkey（占用内存最大的 string 数据类型，包含元素最多的复合数据类型）。
2. 分析RDB文件  
网上有现成的代码/工具可以直接拿来使用：

- redis-rdb-tools ：Python 语言写的用来分析 Redis 的 RDB 快照文件用的工具
- rdb_bigkeys : Go 语言写的用来分析 Redis 的 RDB 快照文件用的工具，性能更好。

## Redis事务
Redis 可以通过 MULTI，EXEC，DISCARD 和 WATCH 等命令来实现事务(transaction)功能。  
```sh
> MULTI
OK
> SET USER "Guide哥"
QUEUED
> GET USER
QUEUED
> EXEC
1) OK
2) "Guide哥"
```
使用 MULTI 命令后可以输入多个命令。Redis 不会立即执行这些命令，而是将它们放到队列，当调用了 EXEC 命令将执行所有命令。

这个过程是这样的：

1. 开始事务（MULTI）。
2. 命令入队(批量操作 Redis 的命令，先进先出（FIFO）的顺序执行)。
3. 执行事务(EXEC)

可以同DISCARD 命令取消一个事务，它会清空事务队列中保存的所有命令。   
```sh
> MULTI
OK
> SET USER "Guide哥"
QUEUED
> GET USER
QUEUED
> DISCARD
O
```

WATCH 命令用于监听指定的键，当调用 EXEC 命令执行事务时，如果一个被 WATCH 命令监视的键被修改的话，整个事务都不会执行，直接返回失败。
```sh
> WATCH USER
OK
> MULTI
> SET USER "Guide哥"
OK
> GET USER
Guide哥
> EXEC
ERR EXEC without MULTI
```

## Redis事务
Redis 事务提供了一种将多个命令请求打包的功能。然后，再按顺序执行打包的所有命令，并且不会被中途打断。   
Redis 是不支持 roll back 的，因而不满足原子性的（而且不满足持久性）。

## 缓存穿透
什么是缓存穿透？  
缓存穿透说简单点就是大量请求的 key 根本不存在于缓存中，导致请求直接到了数据库上，根本没有经过缓存这一层。举个例子：某个黑客故意制造我们缓存中不存在的 key 发起大量请求，导致大量请求落到数据库。

解决方法：  
最基本的就是首先做好参数校验，一些不合法的参数请求直接抛出异常信息返回给客户端。比如查询的数据库 id 不能小于 0、传入的邮箱格式不对的时候直接返回错误消息给客户端等等。   
1. 缓存无效key   
    如果缓存和数据库都查不到某个 key 的数据就写一个到 Redis 中去并设置过期时间，具体命令如下： SET key value EX 10086 。这种方式可以解决请求的 key 变化不频繁的情况，如果黑客恶意攻击，每次构建不同的请求 key，会导致 Redis 中缓存大量无效的 key 。很明显，这种方案并不能从根本上解决此问题。如果非要用这种方式来解决穿透问题的话，尽量将无效的 key 的过期时间设置短一点比如 1 分钟。

    另外，这里多说一嘴，一般情况下我们是这样设计 key 的： 表名:列名:主键名:主键值 。

    如果用 Java 代码展示的话，差不多是下面这样的：
    ```java
        public Object getObjectInclNullById(Integer id) {
        // 从缓存中获取数据
        Object cacheValue = cache.get(id);
        // 缓存为空
        if (cacheValue == null) {
            // 从数据库中获取
            Object storageValue = storage.get(key);
            // 缓存空对象
            cache.set(key, storageValue);
            // 如果存储数据为空，需要设置一个过期时间(300秒)
            if (storageValue == null) {
                // 必须设置过期时间，否则有被攻击的风险
                cache.expire(key, 60 * 5);
            }
            return storageValue;
        }
        return cacheValue;
    }
    ```

2. 布隆过滤器
    布隆过滤器是一个非常神奇的数据结构，通过它我们可以非常方便地判断一个给定数据是否存在于海量数据中。我们需要的就是判断 key 是否合法，有没有感觉布隆过滤器就是我们想要找的那个“人”。

    具体是这样做的：把所有可能存在的请求的值都存放在布隆过滤器中，当用户请求过来，先判断用户发来的请求的值是否存在于布隆过滤器中。不存在的话，直接返回请求参数错误信息给客户端，存在的话才会走下面的流程。

    加入布隆过滤器之后的缓存处理流程图如下。   
    ![](./img_redis/%E5%B8%83%E9%9A%86%E8%BF%87%E6%BB%A4%E5%99%A8.PNG)

    总结来说就是： 布隆过滤器说某个元素存在，小概率会误判。布隆过滤器说某个元素不在，那么这个元素一定不在。

    **当一个元素加入布隆过滤器中的时候，会进行哪些操作：**
    1. 使用布隆过滤器中的哈希函数对元素值进行计算，得到哈希值（有几个哈希函数得到几个哈希值）。
    2. 根据得到的哈希值，在位数组中把对应下标的值置为 1。

    **当我们需要判断一个元素是否存在于布隆过滤器的时候，会进行哪些操作：**
    1. 对给定元素再次进行相同的哈希计算；
    2. 得到值之后判断位数组中的每个元素是否都为 1，如果值都为 1，那么说明这个值在布隆过滤器中，如果存在一个值不为 1，说明该元素不在布隆过滤器中。

## 缓存雪崩
什么是缓存雪崩？  
- 缓存在同一时间大面积的失效，后面的请求都直接落到了数据库上，造成数据库短时间内承受大量请求。
- 有一些被大量访问数据（热点缓存）在某一时刻大面积失效，导致对应的请求直接落到了数据库上。 

有哪些解决办法？
- 针对 Redis 服务不可用的情况：
    1. 采用 Redis 集群，避免单机出现问题整个缓存服务都没办法使用。
    2. 限流，避免同时处理大量的请求

- 针对热点缓存失效的情况：
    1. 设置不同的失效时间比如随机设置缓存的失效时间。
    2. 缓存永不失效

## 如何保证缓存和数据库数据的一致性？
Cache Aside Pattern 中遇到写请求是这样的：更新 DB，然后直接删除 cache 。

如果更新数据库成功，而删除缓存这一步失败的情况的话，简单说两个解决方案：

1. 缓存失效时间变短（不推荐，治标不治本） ：我们让缓存数据的过期时间变短，这样的话缓存就会从数据库中加载数据。另外，这种解决办法对于先操作缓存后操作数据库的场景不适用。
2. 增加 cache 更新重试机制（常用）： 如果 cache 服务当前不可用导致缓存删除失败的话，我们就隔一段时间进行重试，重试次数可以自己定。如果多次重试还是失败的话，我们可以把当前更新失败的 key 存入队列中，等缓存服务可用之后，再将缓存中对应的 key 删除即可。