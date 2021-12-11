package com.itheima.controller;/**
 * Project: dubbo-demo
 * Package: com.itheima.controller
 * Version: 1.0
 * Created by ljy on 2021-12-11 20:41
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.HelloService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName HelloController
 * @Author: ljy on 2021-12-11 20:41
 * @Version: 1.0
 * @Description
 */
@Controller
@RequestMapping("/demo")
public class HelloController {

    //在服务消费者一方配置负载均衡策略
    @Reference(check = false,loadbalance = "random")
    private HelloService helloService;

    @RequestMapping("/hello")
    @ResponseBody
    public String getName(String name){
        String result = helloService.sayHello(name);
        System.out.println(result);
        return result;
    }
}
