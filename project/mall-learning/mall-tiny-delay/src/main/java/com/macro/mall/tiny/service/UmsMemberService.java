package com.macro.mall.tiny.service;/**
 * Project: mall-tiny-delay
 * Package: com.macro.mall.tiny.service
 * Version: 1.0
 * Created by ljy on 2022-4-26 16:28
 */

/**
 * @ClassName UmsMemberService
 * @Author: ljy on 2022-4-26 16:28
 * @Version: 1.0
 * @Description:
 */

import com.macro.mall.tiny.common.api.CommonResult;

/**
 * 会员管理Service
 * Created by macro on 2018/8/3.
 */
public interface UmsMemberService {

    /**
     * 生成验证码
     */
    CommonResult generateAuthCode(String telephone);

    /**
     * 判断验证码和手机号码是否匹配
     */
    CommonResult verifyAuthCode(String telephone, String authCode);

}
