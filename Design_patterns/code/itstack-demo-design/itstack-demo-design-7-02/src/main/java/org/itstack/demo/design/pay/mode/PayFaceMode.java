package org.itstack.demo.design.pay.mode;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.pay.mode
 * Version: 1.0
 * Created by ljy on 2022-2-15 10:23
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName PayFaceMode
 * @Author: ljy on 2022-2-15 10:23
 * @Version: 1.0
 * @Description:人脸支付
 */
public class PayFaceMode implements IPayMode {
    protected Logger logger = LoggerFactory.getLogger(PayFaceMode.class);
    @Override
    public boolean security(String uId) {
        logger.info("人脸支付，风控校验人脸识别");
        return true;
    }
}
