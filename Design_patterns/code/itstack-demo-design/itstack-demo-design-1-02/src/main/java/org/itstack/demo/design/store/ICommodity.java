package org.itstack.demo.design.store;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.store
 * Version: 1.0
 * Created by ljy on 2022-1-10 10:41
 */

import java.util.Map;

/**
 * @ClassName ICommodity
 * @Author: ljy on 2022-1-10 10:41
 * @Version: 1.0
 * @Description:
 * 接口的共同父类
 */
public interface ICommodity {
    void sendCommodity(String uId, String commodityId, String bizId,
                       Map<String, String> extMap ) throws Exception;
}
