package org.itstack.demo.design.floor;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.floor
 * Version: 1.0
 * Created by ljy on 2022-1-27 9:47
 */

import org.itstack.demo.design.Matter;

import java.math.BigDecimal;

/**
 * @ClassName DerFloor
 * @Author: ljy on 2022-1-27 9:47
 * @Version: 1.0
 * @Description:
 * 德尔地板
 */
public class DerFloor implements Matter {

    public String scene() {
        return "地板";
    }

    public String brand() {
        return "德尔(Der)";
    }

    public String model() {
        return "A+";
    }

    public BigDecimal price() {
        return new BigDecimal(119);
    }

    public String desc() {
        return "DER德尔集团是全球领先的专业木地板制造商，北京2008年奥运会家装和公装地板供应商";
    }

}