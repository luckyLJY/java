package org.itstack.demo.design.store.impl;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.store.impl
 * Version: 1.0
 * Created by ljy on 2022-1-10 10:55
 */

import com.alibaba.fastjson.JSON;
import org.itstack.demo.design.coupon.CouponResult;
import org.itstack.demo.design.coupon.CouponService;
import org.itstack.demo.design.store.ICommodity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @ClassName CouponCommodityService
 * @Author: ljy on 2022-1-10 10:55
 * @Version: 1.0
 * @Description:
 * 优惠卷接口实现
 */
public class CouponCommodityService implements ICommodity {

    private Logger logger = LoggerFactory.getLogger(CouponCommodityService.class);
    private CouponService couponService= new CouponService();

    @Override
    public void sendCommodity(String uId, String commodityId, String bizId, Map<String, String> extMap) throws Exception {
        CouponResult couponResult = couponService.sendCoupon(uId, commodityId, bizId);
        logger.info("请求参数[优惠卷]=>uId:{} commodityId:{} bizId:{}extMap:{}",uId,commodityId,bizId,bizId, JSON.toJSON(extMap));
        logger.info("测试结果[优惠卷]:{}",JSON.toJSON(couponResult));
        if (!"0000".equals(couponResult.getCode()))throw new RuntimeException(couponResult.getInfo());
    }
}
