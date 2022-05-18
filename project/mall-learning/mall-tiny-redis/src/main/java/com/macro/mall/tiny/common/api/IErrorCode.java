package com.macro.mall.tiny.common.api;/**
 * Project: mall-tiny-redis
 * Package: com.macro.mall.tiny.common.api
 * Version: 1.0
 * Created by ljy on 2022-4-27 10:28
 */

/**
 * 封装API的错误码
 * Created by macro on 2019/4/19.
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
