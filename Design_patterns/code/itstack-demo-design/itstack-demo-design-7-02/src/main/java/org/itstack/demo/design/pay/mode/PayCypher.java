package org.itstack.demo.design.pay.mode;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.pay.mode
 * Version: 1.0
 * Created by ljy on 2022-2-15 10:19
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName PayCypher
 * @Author: ljy on 2022-2-15 10:19
 * @Version: 1.0
 * @Description:密码支付
 */
public class PayCypher implements IPayMode{

    protected Logger logger = LoggerFactory.getLogger(PayCypher.class);


    @Override
    public boolean security(String uId) {
        logger.info("密码支付，风控校验环境安全");
        return true;
    }
}
