package org.itstack.demo.design.cuisine.impl;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.cuisine.impl
 * Version: 1.0
 * Created by ljy on 2022-2-9 18:12
 */


import org.itstack.demo.design.cuisine.OrderAdapterService;
import org.itstack.demo.design.service.OrderService;

/**
 * @ClassName InsideOrderService
 * @Author: ljy on 2022-2-9 18:12
 * @Version: 1.0
 * @Description:
 */
public class InsideOrderService implements OrderAdapterService {

    private OrderService orderService = new OrderService();

    @Override
    public boolean isFirst(String uId) {
        return false;
    }
}














