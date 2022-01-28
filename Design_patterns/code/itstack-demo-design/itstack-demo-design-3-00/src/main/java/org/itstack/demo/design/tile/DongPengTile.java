package org.itstack.demo.design.tile;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.tile
 * Version: 1.0
 * Created by ljy on 2022-1-27 9:52
 */

import org.itstack.demo.design.Matter;

import java.math.BigDecimal;

/**
 * @ClassName DongPengTile
 * @Author: ljy on 2022-1-27 9:52
 * @Version: 1.0
 * @Description:
 * 东鹏瓷砖
 */
public class DongPengTile implements Matter {

    public String scene() {
        return "地砖";
    }

    public String brand() {
        return "东鹏瓷砖";
    }

    public String model() {
        return "10001";
    }

    public BigDecimal price() {
        return new BigDecimal(102);
    }

    public String desc() {
        return "东鹏瓷砖以品质铸就品牌，科技推动品牌，口碑传播品牌为宗旨，2014年品牌价值132.35亿元，位列建陶行业榜首。";
    }

}