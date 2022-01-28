package org.itstack.demo.design.ceiling;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.ceiling
 * Version: 1.0
 * Created by ljy on 2022-1-26 16:57
 */

import org.itstack.demo.design.Matter;

import java.math.BigDecimal;

/**
 * @ClassName LevelOneCeiling
 * @Author: ljy on 2022-1-26 16:57
 * @Version: 1.0
 * @Description:
 * 一级顶
 */
public class LevelOneCeiling implements Matter {

    @Override
    public String scene() {
        return "吊顶";
    }

    @Override
    public String brand() {
        return "装修公司自带";
    }

    @Override
    public String model() {
        return "一级顶";
    }

    @Override
    public BigDecimal price() {
        return new BigDecimal(260);
    }

    @Override
    public String desc() {
        return "造型只做低一级，只有一个层次的吊顶，一般离顶120-150mm";
    }
}
