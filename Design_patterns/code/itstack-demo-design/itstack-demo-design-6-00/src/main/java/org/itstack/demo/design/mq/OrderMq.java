package org.itstack.demo.design.mq;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.mq
 * Version: 1.0
 * Created by ljy on 2022-2-9 15:17
 */

import com.alibaba.fastjson.JSON;

import java.util.Date;

/**
 * @ClassName OrderMq
 * @Author: ljy on 2022-2-9 15:17
 * @Version: 1.0
 * @Description:内部订单Mq
 */
public class OrderMq {

    private String uid;           // 用户ID
    private String sku;           // 商品
    private String orderId;       // 订单ID
    private Date createOrderTime; // 下单时间

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreateOrderTime() {
        return createOrderTime;
    }

    public void setCreateOrderTime(Date createOrderTime) {
        this.createOrderTime = createOrderTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
