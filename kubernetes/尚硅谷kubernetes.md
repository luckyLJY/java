## 前置知识
1. Linux操作先
2. Docker
## 知识结构
1. k8s概念和架构
2. 从零开始搭建K8S集群  
    - 基于客户端端工具kubeadm
    - 基于二进制包方式

3. k8s核心概念
    - Pod  
    - Controller  
    - Service
    - Ingress
    - RABC
    - Helm
    - 持久存储

4. 搭建集群监控平台系统
5. 从零搭建高可用k8s集群
6. 在集群环境部署项目
## 第一部分 k8s基本概念
1. k8s基本概述和相关特征
2. k8s架构组件
3. k8s核心概念
    - pod
    - controller
    - service

#### k8s概述
- k8s是谷歌在2014年开源的容器化集群管理系统
- 使用k8s进行容器化应用部署
- 使用k8s利于应用扩展
- k8s目标实施让部署容器化应用更加简洁和高效
#### k8s 架构组件
![](./img_sgg/k8s%E6%9E%B6%E6%9E%84%E5%9B%BE.PNG)
1. master(主控节点)
    - API server  
    集群统一入口，以restful方式提供请求，交给etcd存储
    - Controller-manager  
    处理集群中常规后台任务，一个资源对应一个控制器
    - scheduler  
    节点调度，选择node节点应用部署
    - etcd   
    存储系统，用于保存集群相关的数据
2. node(工作节点)
    - kubelet   
    master排到node节点代表，管理本机容器
    - kube-proxy  
    提供网络代理，负载均衡等操作

#### k8s基本概念
1. pod  
    - 最小部署单元
    - 一组容器的集合
    - 共享网络的
    - 生命周期是短暂的
2. Controller  
    - 确保预期的pod副本数量
    - 无状态应用部署
    - 有状态的应用部署
    - 确保所有的node运行同一个pod
    - 一次性任务和定时任务
3. service
    - 定义一组pod的访问规则
## 第二部分 集群搭建
1. 平台规划
2. 服务器硬件配置要求
3. k8s搭建方式
#### 平台规划
1. 单master集群
![](./img_sgg/%E5%8D%95Master%E8%8A%82%E7%82%B9.PNG)
2. 多master集群
![](./img_sgg/%E5%A4%9AMaster%E8%8A%82%E7%82%B9.PNG)
#### 硬件要求
1. 测试环境  
master 2核Cpu 内存4G 硬盘20G
node   4核Cpu 内存8G 硬盘40G
2. 生产环境 
更高 
#### k8s集群搭建方式
1. kubeadm  
kubeadm init 和 kubeadm join
    - 安装三台虚拟机，安装操作系统centos7.x
    - 对三个安装之后操作系统进行初始化操作
    - 在三个节点安装docker kubelet kubeadm kubectl
    - 在master节点执行kubead init命令进行初始化
    - 在node节点上执行kubeadm join命令把node节点添加到当前集群里面
    - 配置网络插件
2. 二进制
    - 创建多条虚拟机，安装Linux操作系统
    - 操作系统初始化
    - 为etcd和apiserver自签证书
    - 部署etcd集群
    - 部署master组件
        kube-apiserver,kube-controller-manager,kube-scheduler,etcd
    - 部署node组件
        kubelet,kube-proxy,docker,etcd
    - 部署集群网络
3. 两者的比较
    ![](./img_sgg/07-%E6%90%AD%E5%BB%BAk8s%E9%9B%86%E7%BE%A4%E6%80%BB%E7%BB%93.png)
#### kubectl命令工具
1.  kubectl概述  
kubectl是kubernates集群的命令行工具
2. kubectl命令的语法  
kubectl [command] [TYPE] [NAME] [flags]   
	- command：指定要对资源执行的操作，例如:create、get、describe、和delete
	- TYPE:指定资源类型，资源类型是大小写敏感的，开发者能够以单数、复数和缩略形式。
			kubectl get pod pod1
	- name:指定资源的名称，名称也大小写敏感。kubectl get pods
	- flags:指定可选的参数。-s或者-server参数指定Kubernates API server的地址和端口
3. 帮助命令kubectl --help 具体查看某个操作 kubectl get --help  
4. 目前使用到的命令
    - 创建nginx pod:kubectl create deployment nginx --image=nginx   
	- 对外暴露端口：kubectl expose deployment nginx --port=80 --type=NodePort
	- 查询当前pod，svc:kubectl get pod,svc
	- 查询状态:kubectl get cs
	- 查看node:kubectl get nodes
#### 资源编排yml
资源清单文件、资源编排;   
1. 语法格式
2. yaml文件组成部分
    - 控制器定义
    - 被控制定义
3. 常用字段含义  
    ![](./img_sgg/%E5%B8%B8%E7%94%A8%E5%AD%97%E6%AE%B5%E5%90%AB%E4%B9%89.PNG)  
4. 编写方式  
    - 第一种 使用kubectl create命令生成yaml文件
      ```yaml
      新建yaml模板
      yaml create deployment web --image=nginx -o yaml --dry-run > m1.yaml
      ```
    - 第二种 使用kubectl get命令导出yaml文件
        ```yaml
        kubectl get deploy nginx -o=yaml --export > m2.yaml
        ```
## 第三部分 核心概念
#### pod
1. Pod基本概念  
    
    - 最小部署单元
    - 包含多个容器(一组容器的集合)
    - 一个pod中容器共享网络命名空间
    - pod是短暂的
2. pod存在意义
    - 创建容器使用docker，一个docker对应是一个容器，一个容器运行一个应用程序。
    - Pod是多进程设计，运行多个应用程序。  
       一个Pod有多个容器，一个容器里面运行一个应用程序。   
    - Pod存在为了亲密性应用  
        两个应用之间进行交互   
        网络之间调用  
        两个应用需要频繁调用
3. Pod的实现机制
    - 共享网络
        ![](./img_sgg/pod%E7%BD%91%E7%BB%9C%E5%85%B1%E4%BA%AB.png)
        通过Pause容器，把其他业务容器加入到Pause容器里面，让所有业务容器在同一个名称空间中，可以实现网络共享。
    - 共享存储
        ![](./img_sgg/pod%E5%85%B1%E4%BA%AB%E5%AD%98%E5%82%A8.png)
        引入数据卷概念Volumn,使用数据卷进行持久化存储。

    ![](./img_sgg/pod%E7%A4%BA%E4%BE%8B.png)
4. Pod镜像拉取策略
    ![](./img_sgg/pod%E9%95%9C%E5%83%8F%E6%8B%89%E5%8E%BB%E7%AD%96%E7%95%A5.png)
5. Pod资源限制
    ![](./img_sgg/pod%E8%B5%84%E6%BA%90%E9%99%90%E5%88%B6.png)
6. Pod重启机制
    ![](./img_sgg/Pod%E9%87%8D%E5%90%AF%E7%AD%96%E7%95%A5.png)
7. Pod健康检查
    ![](./img_sgg/Pod%E5%81%A5%E5%BA%B7%E6%A3%80%E6%9F%A5.png)

    **创建Pod的流程**  
    ![](./img_sgg/%E5%88%9B%E5%BB%BAPod%E7%9A%84%E6%B5%81%E7%A8%8B.png)  
8. Pod调度  
    影响调用的属性   
    - Pod资源限制对Pod调用影响：根据request找到足够node节点进行调度
    - 节点选择器标签Pod调度:nodeSelector,env_role:dev;（环境隔离）--首先对节点创建别名：(kubectl label node node1 env_role=prod)查看打印的标签：kubectl get nodes k8snode1 --show-labels
    - 节点亲和性
      ![](./img_sgg/%E4%BA%B2%E5%92%8C%E6%80%A7.png)
      **反亲和性**：not in 
    - 污点和污点容忍  
        1. 基本介绍  
        nodeSelector和nodeAffinity:Pod调度到某些节点上，Pod属性，调度时候实现  
        Taint污点：节点不做普通分配调度，是节点属性
        2. 场景  
            - 专用节点
            - 配置特点硬件节点
            - 基于Taint驱逐
        3. 具体演示
            - 查看节点污点情况：kubectl describe node k8smaster | grep Taint  
            **污点中的三个值**：  
            NoSchedule:一定不被调度  
            PreferNoSchdule：尽量不被调度  
            NoExecute：不会调度，并且还会驱逐Node已有的Pod
            - 为节点添加污点  
            kubectl taint node [node] key=value:污点的三个值    
            创建多个pod副本：kubectl scale deployment web --replicas=5  
            - 删除污点：kubectl taint node [node] key:value-
            - 污点容忍
              ```yaml
              spec: 
                tolerations:
                - key: "key"
                  operator: "Equal"
                  value: "value"
                  effect: "NoSchedule"

              containers:
              - name: webdemo
                image: nginx
              ```
#### controller
1. 什么是controller  
在集群上管理和运行容器的对象；  
2. Pod和Controller关系  
Pod是通过Controller实现应用的运维比如伸缩，滚动升级等等  
Pod和Controller之间是通过label标签建立关系:selector  
3. Deployment控制器应用场景  
部署无状态应用  
管理Pod和ReplicaSet  
部署，滚动升级等功能  
**应用在web和微服务等场景**
4. yaml文件字段说明  

5. Deployment控制器部署应用  
创建yaml文件：kubectl create deployment web --image=nginx --dry-run -o yaml > web.yaml  
创建pod应用：kubectl apply -f web.yaml
查看pod应用: kubectl get pods
对外暴露端口号生成yaml文件: kubectl expose deployment web --port=80 --type=NodePort --target=80 --name=web1 > web1.yaml  
发布应用：kubectl apply -f web1.yaml  
6. 升级回滚  
    - 修改web.yaml的版本为1.14,副本为2
    - 下载镜像:kubectl apply -f web.yml
    - 查看镜像：kubectl get pods  

    **升级**：   
    - 应用升级：kubectl set image deployment web nginx=nginx:1.15  
    - 查看升级状态：kubectl rollout status deployment web  

    **回滚**：  
    - 查看历史版本：kubectl rollout history deployment web
    - 回滚到上一个版本： kubectl rollout undo deployment web
    - 回滚到指定版本：kubectl rollout undo deployment web --to-revision=2
7. 弹性伸缩
- 创建多个副本：kubectl scale deployment --replicas=5 
#### service
1. servise存在意义
    - 防止Pod失连(服务发现)
    - 定义一组Pod的访问策略(负载均衡)
2. Pod和Service关系  
    根据label和selector标签建立关联的
3. 常见service的类型
    - ClusterIP:集群内部使用
    - NodePort:对外访问
    - LoadBalancer:对外访问应用使用，公有云  

    导出service文件：kubectl expose deployment web --port=80 --target-port=80 --dry-run -o yaml > service.yaml   
    添加 type: ClusterIP  修改类别  
    执行：kubectl apply -f service.yaml

    node内网部署应用，外网一般不能访问到的   
    - 找到一台可以进行外网访问机器，安装nginx，反向代理   
    - 手动把可以访问节点添加到nginx里面  

    LoandBalancer:公有云，把负载均衡，控制器
#### 其他controller
1. 无状态和有状态的区别  
    - 无状态：  
        1. 认为Pod都是一样的，查看pod:kubectl get pods -o wide
        2. 没有顺序要求
        3. 不用考虑在那个node运行
        4. 随意进行伸缩和扩展
    - 有状态
        1. 上面因素都需要考虑到
        2. 让每个Pod都是独立的，保持pod启动顺序和唯一性(唯一网络表示符，持久存储；有序，比如mysql主从)
2. 部署有状态应用
    - 无头service  
    ClusterIP:none(配置)
    - SatefulSet部署有状态应用(配置)  
    执行yaml文件：kubectl apply -f sts.yml  
    查看pod:有三个pod,每个都是唯一名称  
    查看无头service：kubectl get svc  
    deployment和statefueset区别：有身份的（唯一标识的）  
    根据主机名 +  按照一定规则生成域名：每个pod有唯一主机名；唯一域名；格式：主机名称。service名称.svc.cluster.local;例如：nginx-statefulset-0.nginx.default.svc.cluster.local  
3. 确保所有node运行同一个pod（部署守护进程）Daemonset   
    例子：在么个node节点安装数据采集工具  
    ![](./img_sgg/%E6%89%80%E6%9C%89%E8%8A%82%E7%82%B9%E9%83%A8%E7%BD%B2%E5%90%8C%E4%B8%80%E4%B8%AAPod%E9%87%87%E9%9B%86%E5%B7%A5%E5%85%B7yaml.png)  
    执行yaml:kubectl apply -f ds.yaml  
    查看pod应用：kubectl get pods  
    进入Pod应用：kubectl exec -it ds-test-cbk6v bash  
4. job（一次性任务）
    ![](./img_sgg/%E4%B8%80%E6%AC%A1%E6%80%A7yaml.png)
    执行yaml文件：kubectl create -f job.yaml
    查看pod：kubectl get pods -o wide  
    日志查看：kubectl logs pi-qpqff  
    删除任务：kubectl delete -f job.yaml
5. cronjob（定时任务）   
    数据备份  
    ![](./img_sgg/%E5%AE%9A%E6%97%B6%E4%BB%BB%E5%8A%A1yaml.png)  
    执行yaml：kubectl apply -f cronjob.yaml  
    查看pod:kubectl get pods  
    查看任务：kubectl get cronjobs   
    查看日志：kubectl logs hello-id   
#### Secret
作用：加密数据存在etcd里面，让Pod容器以挂载Volume方式进行访问  
场景：凭证  
1. 创建secret加密数据
```yaml
apiVersion: v1
kind: Secret
metadata:
  name: mysecret
type: opaque
data:
  username: YWRtaW4=
  password: MWYyZDFMmU2N2Rm
```
创建yaml文件:kubectl create -f secret.yaml   
2. 以变量形式挂载到pod容器中  
![](./img_sgg/secret%E7%9A%84yaml%E6%96%87%E4%BB%B6.png)
创建yaml文件：kubectl apply -f secret-val.yaml  
进入容器查看：kubectl exec -it mypod bash  
3. 通过volume数据卷形式挂载到pod容器中  
![](./img_sgg/volume%E6%8C%82%E7%9D%80yaml.png)  
kubetl apply -f secret-vol.yaml  
kubectl get pods  
kubectl exec -it mypod bash  
#### 配置管理ConfigMap 
作用：存储不机密数据到etcd，让pod以变量或者Volume挂载到容器中  
场景：配置文件  
1. 创建过程
    - 创建配置文件  
        vi redis.properties
        ```properties
        redis.host=127.0.0.1
        redis.post=6379
        redis.password=123456
        ```

    -  创建configmap
    kubectl create configmap redis-config --from-file=redis.propertis
    -  查看
    kubectl get cm
    -  查看详细信息
    kubectl describe cm redis-config
2. 以变量形式挂载到pod容器中  
    - 创建yaml，声明变量信息cofigmap创建 
        vim myconfig.yaml 
        ![](./img_sgg/%E4%BB%A5%E5%8F%98%E9%87%8F%E5%BD%A2%E5%BC%8F%E6%8C%82%E8%BD%BDconfigmap.png) 
    - 创建容pod:kubectl apply -f myconfig.yaml
    - 查看容pod:kubectl get cm
    - 已变量挂载  
        vi var.yaml  
        ![](./img_sgg/%E4%BB%A5%E5%8F%98%E9%87%8F%E5%BD%A2%E5%BC%8F%E6%8C%82%E8%BD%BDconfigmap.png)  
    - 创建容器pod:kubectl appyly -f config-val.yaml
    - 查看;kubectl get cm
    - 日志查看：kubectl logs mypod

3. 以Volume挂载到pod容器中
    - 创建yaml文件  
        vi cm.yaml  
        ![](./img_sgg/volum%E6%8C%82%E8%BD%BDconfigmap.png)
    - 创建pod:kubectl apply -f cm.yaml
    - 查看pod:kubectl get pod
    - 查看日志：kubectl logs mypod  
#### k8s集群的安全机制
1. 概述
    - 访问k8s集群时候，需要经过三个步骤完成具体操作  
        1. 认证  
            - 传输安全：对外不暴露8080端口，只能内部访问，对外使用端口6443  
            - 认证：  
                客户端身份认证常用方式  
                https证书认证，基于ca证书  
                http token认证，通过token识别用户
                http基本认证，用户名+密码认证
        2. 鉴权  
        基于RBAC进行鉴权操作  
        基于角色访问控制  
        3. 准入控制  
        就是准入控制器的列表，如果列表中有请求内容通过，没有拒绝  
    - 进行访问的时候，过程中都需要经过apiservere，apiserver做统一协调。在访问过程中需要证书、token或者用户名+密码。如果访问pod需要serviceAccount  
2. RBAC  
基于角色的访问控制  
- 角色  
    role:特定命名空间访问权限  
    clusterRole:所有命名空间访问权限  
- 角色绑定  
    roleBinding:角色绑定到主体  
    ClusterRoleBinding:集群角色绑定到主体  
- 主体   
    user:用户  
    group:用户组  
    serviceAccount:服务账号 
- 具体操作步骤(二进制搭建的集群中)  
    1. 创建命名空间  
    kubectl create ns roledemo  
    kubectl get ns  
    2. 在新创建的命名空间创建pod
    kubectl run nginx --image=nginx -n  roledemo  
    kubcctl get pods -n roledemo  
    3. 创建角色  
    vi rbac-role.yaml  
    kubectl apply -f rbac-role.yaml  
    kubectl get role -n roledemo  
    4. 创建角色绑定  
    vi rbac-rolebinding.yaml  
    kubectl apply -f rbac-rolebinding.yaml  
    kubectl get role,rolebinding -n roledemo  
    5. 使用证书识别身份  
    mkdir mary  
    vi rabc-user.sh  
    cp TLS/k8s下的证书文件ca*  
    bash rabc-user.sh  
    6. 测试
    kubectl get pods -n roledemo  
#### Ingress  
1. 把端口对外暴露，通过ip+端口号进行访问  
    使用Service里面的NodePort实现  
2. NodePort缺陷  
    - 在每个节点都会起到端口，在访问时候通过任何节点，通过节点ip+暴露端口号实现访问  
    - 意味每个端口只能使用一次，一个端口对应一个应用  
    - 实际访问中都是用域名，根据不同域名跳转到不同端口服务中 
3. Ingres和Pod关系  
    - Pod和Ingress通过service关联的  
    ingress作为同意入口，由service关联一组Pod  
4. Ingress工作流程  
    ![](./img_sgg/ingress%E4%BD%BF%E7%94%A8%E6%B5%81%E7%A8%8B.png)  
    需要单独部署
5. 使用步骤
    - 部署Ingress controller(nginx控制器)  
    - 创建Ingress规则  
6. 实际操作
    - 创建ngnix的应用，对外暴露端口使用NodePort  
    kubectl create deployment web --image=nginx  
    kubectl get pods  
    kubectl get deploy  
    kubectl expose deployment web --port=80  --target-port=80 --type=NodePort  
    kubectl get svc  
    - 部署Ingress controller  
    vi ingress-controller.yaml  
    kubectl appyly -f ingress-controller.yaml 
    查看ingress-controller状态：kubectl get pods -n ingress-nginx  
    - 创建ingress规则  
    vi ingress01.yaml  
    kubectl apply -f ingress01.yaml  
    kubectl get pods -n ingress-nginx -o wide  
    - 在windows系统hosts文件中添加域名访问的规则  
    ip 域名  
#### HELM
1. 引入  
    - 之前方式部署应用基本过程
        1. 编写ymal文件  
         编写deployment  
         暴露service  
         Ingress  
        2. 优缺点  
        优点：如果使用之前方式部署单一应用，少数服务应用，比较合适  
        缺点：比如微服务项目，可能有几十个服务，每个服务都有一条yaml文件，需要维护大量yaml文件，版本管理特别不方便 
        3. 可以解决的问题  
            - 使用helm可以把这些yaml作为一个整体管理  
            - 实现yaml实现高效复用    
            - 使用helm应用级别的版本管理   
      
2. helm介绍   
    - helm是k8s的包管理工具，类似于yum  
    - helm三个重要概念  
        1. helm是一个命令行客户端工具，主要用于chart的创建、打包、发布和管理   
        2. chart把yaml打包，是yaml集合  
        3. release基于chart部署实体，应用级别的版本管理  
    - 2019年发布了helm的v3版本，与之前那比较有以下变化  
        1. Tiller在V3中删除掉了，
        2. release可以在不同命名空间重用  
        3. 将chart推送到docker仓库中  
    - helm 架构  
        ![](./img_sgg/helmv3%E6%9E%B6%E6%9E%84.png)
3. helm的安装
    - 下载helm安装压缩文件，上传到liunx系统中
    - 戒烟helm压缩文件，把解压之后helm目录复制到usr/bin目录下  
4. 配置helm仓库  
    - 添加仓库  
        helm repo add 仓库名称 仓库地址 
    - 查看仓库  
        查看helm repo list  
    - 更新仓库  
        更新：helm repo update  
    - 删除仓库  
        删除：helm repo remove 仓库名称  
5. 使用helm快速部署应用  
    - 使用命令搜素应用  
        1. helm search repo 名称(weave)
        2. 根据搜素到的内容选择进行安装helm install 安装之后的名称 搜索之后的名称  
        3. 查看安装状态  
            helm list  
            helm status 安装之后名称  
    - helm search repo weave  
    - helm install ui stable/weave-scope  
    - helm list  
    - helm status ui  
    - kubectl get pods 
    - kubectl get svc没有暴露端口  
    - 修改service的yaml文件，type修改为NodePort  
        kubctl edit svc service的名称  
6. 如何自定义Chart完成部署
    - 使用命令创建chart:helm create chart名称
      Chart.yaml：当前chart属性配置信息  
      templates:编写yaml文件放到这个目录中  
      values.yaml:yaml文件可以使用全局变量  
    - 在templates文件夹中创建两个yaml文件  
      deployment.yaml:kubectl create deployment  web1 --image=nginx --dry-run -o yaml > deployment.yaml  
      kubectl create deployment  web1  
      service:kubectl expose deployment web1 --port=80 
      --target-port=80 --type=NodePort --dry-run -o yaml > service.yaml  
    - 安装mychart helm install web1 mychart/
    - 应用升级：helm upgrade chart名称  目录   
    helm upgrade web1 mychart/
7. 实现yaml高效复用  
通过传递参数，动态渲染模板，yaml内容动态传入参数生成  
在chart有values.yaml文件，yaml文件全局变量  
实现步骤：
    - 在values.yaml定义变量和值  
    - 在具体yaml文件，获取定义变量值  
    在templates的yaml文件中通过表达式形式使用全局变量  
    {{.Values.变量名称}}  
    {{.Release.Name}}.deploy
    - 尝试执行helm install --dry-run web2 mychart/
    - 运行kubectl install web2 mychart/
    - 查看kubectl get pods   
    
    yaml文件中大体有以下地方不同的  
     - image
     - tag
     - label
     - port  
     - replicas  
#### 持久化存储  
1. nfs网络存储  
pod重启，数据依然存在  
2. 实现步骤  
    第一步
    - 找一台服务器nfs服务端，安装nfs  
        yum install -y nfs-utils
    - 挂载路径需要创建出来  
        mkdir /data/nfs  
    - 设置挂载路径  
        vi /etc/exports  
        /data/nfs *(rw,no_root_squash)  

    第二步  
    - 在node的节点安装nfs，后自动挂载nfs
    
    第三步 在nfs服务器启动nfs服务      
    - systemctl start nfs  
    
    第四步 在k8s集群中部署应用使用nfs的持久网络存储    
    - vi nfs-nginx.yaml  
    - kubectl apply -f nfs-nginx.yaml  
    - kubectl describe pod podname 
    - kubectl exec -it podname bash  
3. PV和PVC  
PV：持久化存储，对存储的资源进行抽象，对外提供调用的地方(生成者)；  
PVC:用户调用，不需要关系内部实现细节(消费者)；  
实现流程：  
![](./img_sgg/pv%E5%92%8Cpvc%E5%AE%9E%E7%8E%B0%E6%AD%A5%E9%AA%A4.png)  
    - vi pvc.yaml  
    - kubectl apply -f pvc.yaml  
    - kubectl get pods  
    - vi pv.yaml  
    - kubectl apply -f pv.yaml
    - kubectl get pv,pvc   
    - kubectl get pods   
    - kubectl exec -it podname bash  
## 第四部分 集群资源监控
#### 监控指标
集群监控  
1. 节点资源利用率
2. 节点数
3. 运行pods  

Pod监控  
1. 容器指标  
2. 应用程序  

#### 监控平台
监控平台搭建方案promethes+Grafana  
promethes:一套开源的、监控、报警、数据库，以HTTP协议周期性抓取被监控组件的状态；不需要复杂的集成过程，使用http接口接入就可以了   
Grafana:开源的数据分析和可视化工具；支持多种数据源   
搭建过程：  
1. 部署promethus
- node-exporter.yaml  
kubectl create -f node-exporter.yaml  
- rbac-setup.yaml 
kubectl create -f - rbac-setup.yaml  
- configmap.yaml  
kubectl create -f configmap.yaml
- prometheus.deploy.yaml
kubectl create -f prometheus.deploy.yaml  
- prometheus.svc.yaml   
kubectl create -f prometheus.svc.yaml  
- kubectl get pods -n kubectl-system 
2. 部署Grafana  
- grafana-deploy.yaml
kubectl create -f grafana-deploy.yaml
- grafana-ing.yaml
kubectl create -f grafana-ing.yaml
- grafana-svc.yaml
kubectl create -f grafana-svc.yaml
- kubectl get pods -n kubectl-system
3. 打开Grafana配置数据源，导入显示模板  
kubectl get svc -n kube-system -o wide   
![](./img_sgg/%E9%85%8D%E7%BD%AE%E6%95%B0%E6%8D%AE%E6%BA%90.png)  
![](./img_sgg/%E6%95%B0%E6%8D%AE%E6%A8%A1%E6%9D%BF.png)

## 第四部分 高可用集群搭建
## 第五部分 实际部署项目
#### 容器交付流程
1. 开发代码阶段  
    编写代码->测试->编写dockerfile
2. 持续交付   
    代码编译打包->制作镜像->上传镜像仓库  
3. 项目部署  
    环境准备->创建pod->创建Service->创建Ingress  
4. 运维  
    监控->故障排查->升级优化
5. 细节过程  
制作镜像dockerfile->推送到镜像仓库->控制器部署镜像(DEployment)->对外暴露应用(Service nodePort,ingress)->运维(升级，优化)
#### java部署流程
1. 准备java项目，把java包打成(jar包或者war包)  
    环境:jdk,maven    
    把java项目打成jar包 ：mvn clean package  
    编写dockerfile文件  
2. 镜像制作  
    docker build -t java-demo-01:latest .
    测试本地启动制作好的镜像，是否可以访问：docker run -d -p 8111 java-demo-01:latest -t  
3. 上传镜像到镜像服务器中(阿里云)  
    ![](./img_sgg/%E4%B8%8A%E4%BC%A0%E9%95%9C%E5%83%8F.png) 
4. 部署镜像  
    ![](./img_sgg/%E9%83%A8%E7%BD%B2%E9%95%9C%E5%83%8F.png)
    

