package com.macro.mall.tiny.service;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.service
 * Version: 1.0
 * Created by ljy on 2022-4-14 17:04
 */

import com.macro.mall.tiny.common.api.CommonResult;

/**
 * @ClassName UmsMemberService
 * @Author: ljy on 2022-4-14 17:04
 * @Version: 1.0
 * @Description:会员管理Service
 */
public interface UmsMemberService {

    /**
     * 生成码验证
     * @param telephone
     * @return
     */
    CommonResult generateAuthCode(String telephone);

    //判断验证码和手机号码是否匹配
    CommonResult verifyAuthCode(String telephone,String authCode);
}
