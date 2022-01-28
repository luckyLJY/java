package org.itstack.demo.design.tile;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.tile
 * Version: 1.0
 * Created by ljy on 2022-1-27 9:52
 */

import org.itstack.demo.design.Matter;

import java.math.BigDecimal;

/**
 * @ClassName MarcoPoloTile
 * @Author: ljy on 2022-1-27 9:52
 * @Version: 1.0
 * @Description:
 * 马可波罗瓷砖
 */
public class MarcoPoloTile implements Matter {

    public String scene() {
        return "地砖";
    }

    public String brand() {
        return "马可波罗(MARCO POLO)";
    }

    public String model() {
        return "缺省";
    }

    public BigDecimal price() {
        return new BigDecimal(140);
    }

    public String desc() {
        return "“马可波罗”品牌诞生于1996年，作为国内最早品牌化的建陶品牌，以“文化陶瓷”占领市场，享有“仿古砖至尊”的美誉。";
    }

}