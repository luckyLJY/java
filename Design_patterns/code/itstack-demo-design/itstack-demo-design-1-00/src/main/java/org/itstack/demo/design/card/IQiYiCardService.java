package org.itstack.demo.design.card;/**
 * Project: design_pattern
 * Package: org.itstack.demo.design.card
 * Version: 1.0
 * Created by ljy on 2022-1-10 10:09
 */

/**
 * @ClassName IQiYiCardService
 * @Author: ljy on 2022-1-10 10:09
 * @Version: 1.0
 * @Description:
 * 模拟爱奇艺会员卡服务
 */
public class IQiYiCardService {
    public void grantToken(String bindMobileNumber, String cardId) {
        System.out.println("模拟发放爱奇艺会员卡一张：" + bindMobileNumber + "，" + cardId);
    }
}
