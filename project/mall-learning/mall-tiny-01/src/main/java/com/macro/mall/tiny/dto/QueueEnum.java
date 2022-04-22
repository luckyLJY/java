package com.macro.mall.tiny.dto;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.dto
 * Version: 1.0
 * Created by ljy on 2022-4-22 10:05
 */

import lombok.Getter;

/**
 * @ClassName QueueEnum
 * @Author: ljy on 2022-4-22 10:05
 * @Version: 1.0
 * @Description:消息队列枚举配置
 */
@Getter
public enum QueueEnum {

    //消息通知队列
    QUEUE_ORDER_CANCEL("mall.order.direct", "mall.order.cancel", "mall.order.cancel"),

    //消息通知tt1队列
    QUEUE_TTL_ORDER_CANCEL("mall.order.direct.ttl","mall.order.cancel.ttl","mall.order.cancel.ttl");

    //交换名称
    private String exchange;

    //队列名称
    private String name;

    //路由键
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}
