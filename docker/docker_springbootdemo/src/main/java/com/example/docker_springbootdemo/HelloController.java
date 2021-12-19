package com.example.docker_springbootdemo;/**
 * Project: docker_springbootdemo
 * Package: com.example.docker_springbootdemo
 * Version: 1.0
 * Created by ljy on 2021-12-18 21:41
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Author: ljy on 2021-12-18 21:41
 * @Version: 1.0
 * @Description
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello springboot for docker";
    }
}
