package org.itstack.demo.design.cuisine.impl;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.cuisine.impl
 * Version: 1.0
 * Created by ljy on 2022-2-9 18:21
 */

import org.itstack.demo.design.cuisine.OrderAdapterService;
import org.itstack.demo.design.service.POPOrderService;

/**
 * @ClassName POPOrderAdapterServiceImpl
 * @Author: ljy on 2022-2-9 18:21
 * @Version: 1.0
 * @Description:
 */
public class POPOrderAdapterServiceImpl implements OrderAdapterService {

    private POPOrderService popOrderService = new POPOrderService();

    public boolean isFirst(String uId) {
        return popOrderService.isFirstOrder(uId);
    }

}
