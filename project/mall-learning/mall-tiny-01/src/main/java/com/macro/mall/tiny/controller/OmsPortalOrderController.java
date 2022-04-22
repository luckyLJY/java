package com.macro.mall.tiny.controller;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.controller
 * Version: 1.0
 * Created by ljy on 2022-4-22 11:14
 */

import com.macro.mall.tiny.dto.OrderParam;
import com.macro.mall.tiny.service.OmsPortalOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName OmsPortalOrderController
 * @Author: ljy on 2022-4-22 11:14
 * @Version: 1.0
 * @Description:订单管理Controller
 */
@Controller
@Api(tags = "OmsPortalOrderController",description = "订单管理")
@RequestMapping("/order")
public class OmsPortalOrderController {

    @Autowired
    private OmsPortalOrderService portalOrderService;

    @ApiOperation("根据购物车信息生成订单")
    @RequestMapping(value = "/generateOder",method = RequestMethod.POST)
    @ResponseBody
    public Object generateOder(@RequestBody OrderParam orderParam){
        return portalOrderService.generateOrder(orderParam);
    }
}
