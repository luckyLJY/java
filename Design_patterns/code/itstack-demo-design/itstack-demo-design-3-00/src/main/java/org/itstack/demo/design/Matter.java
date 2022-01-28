package org.itstack.demo.design;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design
 * Version: 1.0
 * Created by ljy on 2022-1-26 16:53
 */

import java.math.BigDecimal;

/**
 * @ClassName
 * @Author: ljy on 2022-1-26 16:53
 * @Version: 1.0
 * @Description:
 * 无料接口
 */
public interface Matter {
    String scene();//场景：地板、地砖、涂料、吊顶
    String brand();//品牌
    String model();//型号
    BigDecimal price();//价格
    String desc();//描述
}
