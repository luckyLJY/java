package org.itstack.demo.design.goods;/**
 * Project: design_pattern
 * Package: org.itstack.demo.design.goods
 * Version: 1.0
 * Created by ljy on 2022-1-10 10:18
 */

import com.alibaba.fastjson.JSON;
/**
 * @ClassName GoodsService
 * @Author: ljy on 2022-1-10 10:18
 * @Version: 1.0
 * @Description：
 * 模拟实体商品服务
 */
public class GoodsService {
    public Boolean deliverGoods(DeliverReq req) {
        System.out.println("模拟发货实物商品一个：" + JSON.toJSONString(req));
        return true;
    }
}
