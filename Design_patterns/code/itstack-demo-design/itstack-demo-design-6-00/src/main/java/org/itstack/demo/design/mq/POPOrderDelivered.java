package org.itstack.demo.design.mq;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.mq
 * Version: 1.0
 * Created by ljy on 2022-2-9 15:20
 */

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName POPOrderDelivered
 * @Author: ljy on 2022-2-9 15:20
 * @Version: 1.0
 * @Description:第三方订单Mq
 */
public class POPOrderDelivered {
    private String uId;     // 用户ID
    private String orderId; // 订单号
    private Date orderTime; // 下单时间
    private Date sku;       // 商品
    private Date skuName;   // 商品名称
    private BigDecimal decimal; // 金额

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getSku() {
        return sku;
    }

    public void setSku(Date sku) {
        this.sku = sku;
    }

    public Date getSkuName() {
        return skuName;
    }

    public void setSkuName(Date skuName) {
        this.skuName = skuName;
    }

    public BigDecimal getDecimal() {
        return decimal;
    }

    public void setDecimal(BigDecimal decimal) {
        this.decimal = decimal;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
