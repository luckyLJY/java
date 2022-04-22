package com.macro.mall.tiny.service.impl;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.service.impl
 * Version: 1.0
 * Created by ljy on 2022-4-22 10:59
 */

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.component.CancelOrderSender;
import com.macro.mall.tiny.dto.OrderParam;
import com.macro.mall.tiny.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName OmsPortalOrderServiceImpl
 * @Author: ljy on 2022-4-22 10:59
 * @Version: 1.0
 * @Description:前台订单管理
 */
@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
    private static Logger LOGGER = LoggerFactory.getLogger(OmsPortalOrderServiceImpl.class);
    @Autowired
    private CancelOrderSender cancelOrderSender;

    @Override
    public CommonResult generateOrder(OrderParam orderParam) {
        //执行一系列下单操作，具体参考mall项目
        LOGGER.info("process generateOrder");
        //下单完成后开启一个延迟消息，用于当用户没有付款时取消订单(orderId应该在下单后生成)

        return null;
    }

    @Override
    public void cancelOrder(Long orderId) {
        //执行一系列取消订单操作，具体参考mall项目
        LOGGER.info("process cancelOrder orderId:{}",orderId);
    }

    private void sendDelayMessageCancelOrder(Long orderId){
        //获取订单超时时间，假设60分钟(测试用30秒)
        long delayTimes = 30*1000;
        //发送延迟消息
        cancelOrderSender.sendMessage(orderId,delayTimes);
    }
}
