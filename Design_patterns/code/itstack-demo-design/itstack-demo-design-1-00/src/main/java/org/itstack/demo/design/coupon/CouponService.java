package org.itstack.demo.design.coupon;/**
 * Project: design_pattern
 * Package: org.itstack.demo.design.coupon
 * Version: 1.0
 * Created by ljy on 2022-1-10 10:15
 */

/**
 * @ClassName CouponService
 * @Author: ljy on 2022-1-10 10:15
 * @Version: 1.0
 * @Description：
 * 模拟优惠卷服务
 */
public class CouponService {
    public CouponResult sendCoupon(String uId, String couponNumber, String uuid) {
        System.out.println("模拟发放优惠券一张：" + uId + "," + couponNumber + "," + uuid);
        return new CouponResult("0000", "发放成功");
    }
}
