package org.itstack.demo.design.service;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.service
 * Version: 1.0
 * Created by ljy on 2022-2-9 15:24
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName POPOrderService
 * @Author: ljy on 2022-2-9 15:24
 * @Version: 1.0
 * @Description:第三方接口下单首次接口
 */
public class POPOrderService {
    private Logger logger = LoggerFactory.getLogger(POPOrderService.class);

    public boolean isFirstOrder(String uId) {
        logger.info("POP商家，查询用户的订单是否为首单：{}", uId);
        return true;
    }
}
