package org.itstack.demo.design.cuisine;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design
 * Version: 1.0
 * Created by ljy on 2022-2-9 15:28
 */

import java.util.Date;

/**
 * @ClassName RebateInfo
 * @Author: ljy on 2022-2-9 15:28
 * @Version: 1.0
 * @Description:统一MQ消息体
 */
public class RebateInfo {
    private String userId;  // 用户ID
    private String bizId;   // 业务ID
    private Date bizTime;   // 业务时间
    private String desc;    // 业务描述

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public Date getBizTime() {
        return bizTime;
    }

    public void setBizTime(Date bizTime) {
        this.bizTime = bizTime;
    }

    public void setBizTime(String bizTime) {
        this.bizTime = new Date(Long.parseLong("1591077840669"));
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "RebateInfo{" +
                "userId='" + userId + '\'' +
                ", bizId='" + bizId + '\'' +
                ", bizTime=" + bizTime +
                ", desc='" + desc + '\'' +
                '}';
    }
}
