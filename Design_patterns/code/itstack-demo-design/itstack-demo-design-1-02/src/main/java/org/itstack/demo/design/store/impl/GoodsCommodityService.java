package org.itstack.demo.design.store.impl;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.store.impl
 * Version: 1.0
 * Created by ljy on 2022-1-10 11:11
 */

import com.alibaba.fastjson.JSON;
import org.itstack.demo.design.goods.DeliverReq;
import org.itstack.demo.design.goods.GoodsService;
import org.itstack.demo.design.store.ICommodity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @ClassName GoodsCommodityService
 * @Author: ljy on 2022-1-10 11:11
 * @Version: 1.0
 * @Description:
 * 实物商品接口
 */
public class GoodsCommodityService implements ICommodity {
    private Logger logger = LoggerFactory.getLogger(GoodsCommodityService.class);
    private GoodsService goodsService = new GoodsService();


    @Override
    public void sendCommodity(String uId, String commodityId, String bizId, Map<String, String> extMap) throws Exception {
        DeliverReq deliverReq = new DeliverReq();
        deliverReq.setUserName(queryUserName(uId));
        deliverReq.setUserPhone(queryUserPhoneNumber(uId));
        deliverReq.setSku(commodityId);
        deliverReq.setOrderId(bizId);
        deliverReq.setConsigneeUserName(extMap.get("consigneeUserName"));
        deliverReq.setConsigneeUserPhone(extMap.get("consigneeUserPhone"));
        deliverReq.setConsigneeUserPhone(extMap.get("consigneeUserAddress"));

        Boolean isSuccess = goodsService.deliverGoods(deliverReq);
        logger.info("请求参数[优惠券]=>uId:{}commodityId:{}bizId:{}extMap:{}",uId,commodityId,bizId, JSON.toJSON(extMap));
        if (!isSuccess)throw new RuntimeException("实物商品发送失败");

    }

    public String queryUserName(String uId){
        return "花花";
    }

    public String queryUserPhoneNumber(String uId){
        return "15200101232";
    }
}
