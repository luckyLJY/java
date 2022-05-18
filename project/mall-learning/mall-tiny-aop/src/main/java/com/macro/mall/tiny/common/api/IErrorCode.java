package com.macro.mall.tiny.common.api;/**
 * Project: mall-learning
 * Package: com.macro.mall.tiny.common.api
 * Version: 1.0
 * Created by ljy on 2022-4-24 15:22
 */

/**
 * @ClassName IErrorCode
 * @Author: ljy on 2022-4-24 15:22
 * @Version: 1.0
 * @Description:
 * 封装API的错误码
 */
public interface IErrorCode {
    long getCode();
    String getMessage();
}
