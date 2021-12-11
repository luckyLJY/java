package com.itheima.service.impl;/**
 * Project: dubbo-demo
 * Package: com.itheima.service.impl
 * Version: 1.0
 * Created by ljy on 2021-12-11 20:21
 */

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.service.HelloService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName HelloServiceImpl
 * @Author: ljy on 2021-12-11 20:21
 * @Version: 1.0
 * @Description:实现Service接口
 */
//协议配置、负载均衡配置
@Service(interfaceClass = HelloService.class,protocol = "dubbo",loadbalance = "random")
@Transactional
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
