package org.itstack.demo.design.store.impl;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.store.impl
 * Version: 1.0
 * Created by ljy on 2022-1-10 11:21
 */

import com.alibaba.fastjson.JSON;
import org.itstack.demo.design.card.IQiYiCard;
import org.itstack.demo.design.card.IQiYiCardService;
import org.itstack.demo.design.store.ICommodity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @ClassName CardCommodityService
 * @Author: ljy on 2022-1-10 11:21
 * @Version: 1.0
 * @Description:
 * 第三方兑换开
 */
public class CardCommodityService implements ICommodity {

    private Logger logger = LoggerFactory.getLogger(CardCommodityService.class);
    //模拟注入
    private IQiYiCardService iQiYiCardService = new IQiYiCardService();

    @Override
    public void sendCommodity(String uId, String commodityId, String bizId, Map<String, String> extMap) throws Exception {
        String mobile = queryUserMobile(uId);
        iQiYiCardService.grantToken(mobile,bizId);
        logger.info("请求参数[爱奇艺兑换卡]=>uId:{}commodityId:{}bizId:{}extMap:{}",uId,commodityId,bizId, JSON.toJSON(extMap));
        logger.info("测试结果[爱奇艺兑换卡]:success");
    }

    public String queryUserMobile(String uId){
        return "15200101232";
    }
}
