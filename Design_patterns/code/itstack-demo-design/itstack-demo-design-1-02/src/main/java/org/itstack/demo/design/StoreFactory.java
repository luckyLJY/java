package org.itstack.demo.design;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.store
 * Version: 1.0
 * Created by ljy on 2022-1-10 11:29
 */

import org.itstack.demo.design.store.ICommodity;
import org.itstack.demo.design.store.impl.CardCommodityService;
import org.itstack.demo.design.store.impl.CouponCommodityService;
import org.itstack.demo.design.store.impl.GoodsCommodityService;

/**
 * @ClassName StoreFactory
 * @Author: ljy on 2022-1-10 11:29
 * @Version: 1.0
 * @Description:
 * 避免创建者与具体的产品逻辑耦合、满足单一职责，每一个业务逻辑实现都在所属自己的类中完成、
 * 满足开闭原则，无需更改使用调用方法就可以引入新的产品类型。
 */
public class StoreFactory {
    public ICommodity getICommodityService(Integer commodityType){
        if (null == commodityType) return null;
        if (1 == commodityType) return new CouponCommodityService();
        if (2 == commodityType) return new GoodsCommodityService();
        if (3 == commodityType) return new CardCommodityService();
        throw new RuntimeException("不存在的商品服务类型");
    }
}
