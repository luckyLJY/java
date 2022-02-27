package org.itstack.demo.design;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design
 * Version: 1.0
 * Created by ljy on 2022-2-15 9:28
 */

/**
 * @ClassName PayController
 * @Author: ljy on 2022-2-15 9:28
 * @Version: 1.0
 * @Description:支付Controller
 */
public class PayController {
    private Logger logger = LoggerFactory.getLogger(PayController.class);

    public boolean doPay(String uId,String tradeId,BigDecimal amount,int channelType,int modeType){
        //微信支付
        if(1==channelType){
            logger.info("模拟微信渠道支付划账开始。uId:{} tradeId:{} amount:{}",uId,tradeId,amount);
            if (1== modeType){
                logger.info("密码支付，风控校验环境安全");
            }else if (2==modeType){
                logger.info("人脸支付，风险校验脸部识别");
            }else if (3==modeType){
                logger.info("指纹支付，风险校验指纹信息");
            }
        }
        //支付宝支付
        else if (2 == channelType){
            logger.info("模拟支付宝渠道支付划账开始。uId:{} tradeId:{} amount:{}",uId,tradeId,amount);
            if (1 == modeType){
                logger.info("密码支付，风险校验环境安全");
            }else if (2 == modeType){
                logger.info("人脸支付，风险校验脸部识别");
            }else  if (3 == modeType){
                logger.info("指纹支付，风控校验指纹信息");
            }
        }
        return true;
    }
}













