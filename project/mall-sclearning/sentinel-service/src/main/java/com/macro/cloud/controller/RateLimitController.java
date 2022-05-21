package com.macro.cloud.controller;/**
 * Project: mall-sclearning
 * Package: com.macro.cloud.controller
 * Version: 1.0
 * Created by ljy on 2022-5-19 16:53
 */

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.macro.cloud.domain.CommonResult;
import com.macro.cloud.handler.CustomBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RateLimitController
 * @Author: ljy on 2022-5-19 16:53
 * @Version: 1.0
 * @Description:
 * 限流功能
 */
@RestController
@RequestMapping("/rateLimit")
public class RateLimitController {

    //按资源名称限流，需要指定限流处理逻辑
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public CommonResult byResource(){
        return  new CommonResult("按资源名称限流",200);
    }

    //按url限流，有默认的限流处理逻辑
    @GetMapping("/byUrl")
    @SentinelResource(value = "byUrl",blockHandler = "handleException")
    public CommonResult byUrl(){
        return new CommonResult("按url限流",200);
    }

    public CommonResult handleException(BlockException exception){
        return new CommonResult(exception.getClass().getCanonicalName(),200);
    }

    //自定义通用的限流处理逻辑
    @GetMapping("/customBlockHandler")
    @SentinelResource(value = "customBlockHandler",blockHandler = "handleException",blockHandlerClass = CustomBlockHandler.class)
    public CommonResult blockHander(){
        return new CommonResult("限流成功",200);
    }
}
