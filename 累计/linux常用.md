### 文件中有多少条数据

```powershell
cat a.tct | wc -l
```

### 资源管理

#### 查看目录下文件大小

```powershell
ls -lht  ;du -sh *
df -hl 查看磁盘剩余空间
df -h 查看每个根路径的分区大小
du -sh [目录名] 返回该目录的大小
du -sm [文件夹] 返回该文件夹总M数
du -h [目录名] 查看指定文件夹下的所有文件大小（包含子文件夹）
```

#### 系统资源

##### 进行查看

##### top系统当前的进程情况

```powershell
top 命令用来显示系统当前的进程状况。
格式：top [选项]
主要选项如下。
d：指定更新的间隔，以秒计算。
q：没有任何延迟的更新。如果使用者有超级用户，则 top 命令将会以最高的优先序执行。
c：显示进程完整的路径与名称。
S：累积模式，会将已完成或消失的子进程的 CPU 时间累积起来。
s：安全模式。
i：不显示任何闲置（Idle）或无用（Zombie）的进程。
n：显示更新的次数，完成后将会退出 top。
```

##### free内存监控

```powershell
free -m
```

#### 网络配置

```powershell
1.vmare中"编辑"->虚拟网络编辑器->NAT8->更改设置->NAT8->修改子网->nat网关.2
2.wind10网络 外部dns为8.8.8.8
3.vim /etc/sysconfig/network-scripts/ifcfg-ens33
TYPE="Ethernet"
PROXY_METHOD="none"
BROWSER_ONLY="no"
BOOTPROTO="static"
DEFROUTE="yes"
IPV4_FAILURE_FATAL="no"
IPV6INIT="yes"
IPV6_AUTOCONF="yes"
IPV6_DEFROUTE="yes"
IPV6_FAILURE_FATAL="no"
IPV6_ADDR_GEN_MODE="stable-privacy"
NAME="ens33"
UUID="1ae3a9d0-0d49-4fbe-9d8e-07d61e056ebc"
DEVICE="ens33"
ONBOOT="yes"
#IP
IPADDR=192.168.75.200
GATEWAY=192.168.75.2
DNS1=192.168.75.2
4.vim /etc/hostname
5.vim /etc/hosts
6.关闭防火墙：
    systemctl stop firewalld
    systemctl disable firewalld.service
```

#### 远程连接

```powershell
配置本级host文件
192.168.75.200 hadoop100
192.168.75.201 hadoop101
192.168.75.202 hadoop102
192.168.75.203 hadoop103
192.168.75.204 hadoop104
192.168.75.205 hadoop105
192.168.75.206 hadoop106
192.168.75.207 hadoop107
192.168.75.208 hadoop108
```

#### scp与xsync

```powershell
scp:安全拷贝
rsync -r $pdir/$fname $user@host:$pdir/$fname
rsync:镜像同步
rsync -av $pdir/$fname $user@host:$pdir/$fname
xsync集群分发脚本

```

#### 删除原始java文件

```powershell
#删除原始java文件
rpm -qa|grep -i java |xargs -n1 rpm -e --nodeps
#查看原始java文件
rpm -qa|grep -i java
```

#### 日志查看

```txt
命令有tail、head、cat、less等等
    cat a.log #显示a.log的整个文件内容,内容较少
    more 1.log #查看内容较多的文件，使用空格键来翻页
    less 1.log #查看内容较多的文件，使用上下键翻页
    head  #查看日志文件的前n行内容
    tail -f 1.log#查看1.log日志文件的后10行文件内容

tail -n 1000 文件 重定向到其他文件

cat file| grep -E "条件" >> file.log
```

#### 查找文件

```powershell
# 全局查找abc.log的文件路径
find / -name abc.log
locate abc.log
```

#### vi文件中部分字符替换

```powershell
:%/要替换的字符/替换成的字符
```

#### 系统任务调度crontab

```powershell
/etc/crontab文件包括下面几行：

SHELL=/bin/bash
PATH=/sbin:/bin:/usr/sbin:/usr/bin
MAILTO=""HOME=/
#有些需要执行的命令的环境变量

# run-parts
51 * * * * root run-parts /etc/cron.hourly
24 7 * * * root run-parts /etc/cron.daily
22 4 * * 0 root run-parts /etc/cron.weekly
42 4 1 * * root run-parts /etc/cron.monthly

minute   hour   day   month   week   command     顺序：分 时 日 月 周
```

#### 查看软件安装目录：which mysqld

#### 先检查下mysql是否在自启动列表

```powershell
chkconfig --list mysqld
```

#### 将mysql加入自启动列表

```powershell
chkconfig --level 345 mysqld on
```

#### 远程复制命令

```powershell
scp -r hbase-2.4.6/ root@node03:/export/servers/
```

#### 新建文件

```powershell
vim FileName,cat >> FileName,touch FileName
```

#### 端口管理

```powershell
netstat -anp |grep [端口号] 查看端口占用
nc -lk [端口号] 监听端口号
```

