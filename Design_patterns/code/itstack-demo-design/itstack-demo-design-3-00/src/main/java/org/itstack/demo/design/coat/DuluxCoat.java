package org.itstack.demo.design.coat;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.coat
 * Version: 1.0
 * Created by ljy on 2022-1-27 9:44
 */

import org.itstack.demo.design.Matter;

import java.math.BigDecimal;

/**
 * @ClassName DuluxCoat
 * @Author: ljy on 2022-1-27 9:44
 * @Version: 1.0
 * @Description:
 * 多乐士涂料
 */
public class DuluxCoat  implements Matter {

    public String scene() {
        return "涂料";
    }

    public String brand() {
        return "多乐士(Dulux)";
    }

    public String model() {
        return "第二代";
    }

    public BigDecimal price() {
        return new BigDecimal(719);
    }

    public String desc() {
        return "多乐士是阿克苏诺贝尔旗下的著名建筑装饰油漆品牌，产品畅销于全球100个国家，每年全球有5000万户家庭使用多乐士油漆。";
    }

}
