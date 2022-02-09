package org.itstack.demo.design.service;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.service
 * Version: 1.0
 * Created by ljy on 2022-2-9 15:22
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName OrderService
 * @Author: ljy on 2022-2-9 15:22
 * @Version: 1.0
 * @Description:查询用户内部下单数量接口
 */
public class OrderService {
    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    public long queryUserOrderCount(String userId){
        logger.info("自营商家，查询用户的订单是否为首单：{}", userId);
        return 10L;
    }
}
