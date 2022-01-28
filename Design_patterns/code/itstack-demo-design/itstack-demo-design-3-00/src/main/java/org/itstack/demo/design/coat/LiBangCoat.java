package org.itstack.demo.design.coat;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.coat
 * Version: 1.0
 * Created by ljy on 2022-1-27 9:46
 */

import org.itstack.demo.design.Matter;

import java.math.BigDecimal;

/**
 * @ClassName LiBangCoat
 * @Author: ljy on 2022-1-27 9:46
 * @Version: 1.0
 * @Description：
 * 立邦涂料
 */
public class LiBangCoat implements Matter {

    public String scene() {
        return "涂料";
    }

    public String brand() {
        return "立邦";
    }

    public String model() {
        return "默认级别";
    }

    public BigDecimal price() {
        return new BigDecimal(650);
    }

    public String desc() {
        return "立邦始终以开发绿色产品、注重高科技、高品质为目标，以技术力量不断推进科研和开发，满足消费者需求。";
    }

}
