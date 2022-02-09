package org.itstack.demo.design.mq;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.mq
 * Version: 1.0
 * Created by ljy on 2022-2-9 15:18
 */

import com.alibaba.fastjson.JSON;

import java.util.Date;

/**
 * @ClassName CreateAccount
 * @Author: ljy on 2022-2-9 15:18
 * @Version: 1.0
 * @Description:开户注册Mq
 */
public class CreateAccount {
    private String number;      // 开户编号
    private String address;     // 开户地
    private Date accountDate;   // 开户时间
    private String desc;        // 开户描述

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public  String toString() {
        return JSON.toJSONString(this);
    }
}
