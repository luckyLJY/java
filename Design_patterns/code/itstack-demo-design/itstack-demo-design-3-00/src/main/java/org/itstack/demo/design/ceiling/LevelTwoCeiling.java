package org.itstack.demo.design.ceiling;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.ceiling
 * Version: 1.0
 * Created by ljy on 2022-1-26 17:02
 */

import org.itstack.demo.design.Matter;

import java.math.BigDecimal;

/**
 * @ClassName LevelTwoCeiling
 * @Author: ljy on 2022-1-26 17:02
 * @Version: 1.0
 * @Description：
 * 二级顶
 */
public class LevelTwoCeiling implements Matter {

    public String scene() {
        return "吊顶";
    }

    public String brand() {
        return "装修公司自带";
    }

    public String model() {
        return "二级顶";
    }

    public BigDecimal price() {
        return new BigDecimal(850);
    }

    public String desc() {
        return "两个层次的吊顶，二级吊顶高度一般就往下吊20cm，要是层高很高，也可增加每级的厚度";
    }
}
