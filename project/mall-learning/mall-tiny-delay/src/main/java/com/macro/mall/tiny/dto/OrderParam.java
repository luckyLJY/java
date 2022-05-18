package com.macro.mall.tiny.dto;/**
 * Project: mall-tiny-delay
 * Package: com.macro.mall.tiny.dto
 * Version: 1.0
 * Created by ljy on 2022-4-27 8:51
 */

/**
 * @ClassName OrderParam
 * @Author: ljy on 2022-4-27 8:51
 * @Version: 1.0
 * @Description:生成订单时传入的参数
 */
public class OrderParam {
    //收货地址id
    private Long memberReceiveAddressId;
    //优惠劵id
    private Long couponId;
    //使用积分数
    private Integer useIntegration;
    //支付方式
    private Integer payType;
    public Long getMemberReceiveAddressId() {
        return memberReceiveAddressId;
    }

    public void setMemberReceiveAddressId(Long memberReceiveAddressId) {
        this.memberReceiveAddressId = memberReceiveAddressId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getUseIntegration() {
        return useIntegration;
    }

    public void setUseIntegration(Integer useIntegration) {
        this.useIntegration = useIntegration;
    }
}

