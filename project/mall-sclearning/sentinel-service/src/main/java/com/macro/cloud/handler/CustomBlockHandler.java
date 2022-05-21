package com.macro.cloud.handler;/**
 * Project: mall-sclearning
 * Package: com.macro.cloud.handler
 * Version: 1.0
 * Created by ljy on 2022-5-19 17:10
 */

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.macro.cloud.domain.CommonResult;

/**
 * @ClassName CustomBlockHandler
 * @Author: ljy on 2022-5-19 17:10
 * @Version: 1.0
 * @Description:
 * 自定义限流处理逻辑
 */
public class CustomBlockHandler {

    public CommonResult handleException(BlockException exception){
        return new CommonResult("自定义限流信息",200);
    }
}
