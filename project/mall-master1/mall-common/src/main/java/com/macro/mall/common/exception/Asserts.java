package com.macro.mall.common.exception;/**
 * Project: mall-master1
 * Package: com.macro.mall.common.exception
 * Version: 1.0
 * Created by ljy on 2022-6-18 16:24
 */

import com.macro.mall.common.api.IErrorCode;

/**
 * @ClassName Asserts
 * @Author: ljy on 2022-6-18 16:24
 * @Version: 1.0
 * @Description:断言处理类，用于抛出各种API异常
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
