package com.macro.mall.tiny.service;/**
 * Project: mall-tiny-delay
 * Package: com.macro.mall.tiny.service
 * Version: 1.0
 * Created by ljy on 2022-4-27 8:47
 */

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.dto.OrderParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName OmsPortalOrderService
 * @Author: ljy on 2022-4-27 8:47
 * @Version: 1.0
 * @Description:前台订单管理service
 */
public interface OmsPortalOrderService {
    //根据提交信息生成订单
    @Transactional
    CommonResult generateOrder(OrderParam orderParam);

    //取消单个超时订单
    @Transactional
    void cancelOrder(Long orderId);
}
