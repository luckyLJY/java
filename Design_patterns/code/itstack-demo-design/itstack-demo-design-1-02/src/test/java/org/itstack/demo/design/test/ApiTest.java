package org.itstack.demo.design.test;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.test
 * Version: 1.0
 * Created by ljy on 2022-1-10 11:35
 */

import org.itstack.demo.design.StoreFactory;
import org.itstack.demo.design.store.ICommodity;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ApiTest
 * @Author: ljy on 2022-1-10 11:35
 * @Version: 1.0
 * @Description:
 * 测试工厂类的接口实现
 */
public class ApiTest {

    @Test
    public void test_commodity() throws Exception{
        StoreFactory storeFactory = new StoreFactory();

        //1.优惠卷
        ICommodity iCommodityService = storeFactory.getICommodityService(1);
        iCommodityService.sendCommodity("10001", "EGM1023938910232121323432", "791098764902132", null);

        //2.实物商品
        ICommodity iCommodityService1 = storeFactory.getICommodityService(2);
        Map<String,String> extMap = new HashMap<String,String>();
        extMap.put("consigneeUserName", "谢飞机");
        extMap.put("consigneeUserPhone", "15200292123");
        extMap.put("consigneeUserAddress", "吉林省.长春市.双阳区.XX街道.檀溪苑小区.#18-2109");

        iCommodityService1.sendCommodity("10001","9820198721311","1023000020112221113",new HashMap<String, String>() {{
            put("consigneeUserName", "谢飞机");
            put("consigneeUserPhone", "15200292123");
            put("consigneeUserAddress", "吉林省.长春市.双阳区.XX街道.檀溪苑小区.#18-2109");
        }});

        //3.第三方兑换卡(爱奇艺)
        ICommodity iCommodityService2 = storeFactory.getICommodityService(3);
        iCommodityService2.sendCommodity("10001","AQY1xjkUodl8LO975GdfrYUio",null,null);
    }
}
