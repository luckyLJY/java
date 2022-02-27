package org.itstack.demo.design.pay.mode;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.pay.mode
 * Version: 1.0
 * Created by ljy on 2022-2-15 10:18
 */

/**
 * @ClassName IPayMode
 * @Author: ljy on 2022-2-15 10:18
 * @Version: 1.0
 * @Description:支付接口
 */
public interface IPayMode {
    boolean security(String uId);

}
