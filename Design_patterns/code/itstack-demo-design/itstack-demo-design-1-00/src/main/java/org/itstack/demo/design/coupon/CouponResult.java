package org.itstack.demo.design.coupon;/**
 * Project: design_pattern
 * Package: org.itstack.demo.design.coupon
 * Version: 1.0
 * Created by ljy on 2022-1-10 10:12
 */

/**
 * @ClassName CouponResult
 * @Author: ljy on 2022-1-10 10:12
 * @Version: 1.0
 * @Description：
 * 奖品信息实体类
 */
public class CouponResult {
    private String code; // 编码
    private String info; // 描述

    public CouponResult(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
