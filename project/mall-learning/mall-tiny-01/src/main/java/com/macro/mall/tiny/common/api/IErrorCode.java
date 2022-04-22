package com.macro.mall.tiny.common.api;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.common
 * Version: 1.0
 * Created by ljy on 2022-4-14 10:08
 */

/**
 * @ClassName IErrorCode
 * @Author: ljy on 2022-4-14 10:08
 * @Version: 1.0
 * @Description:
 * 封装API的错误吗
 */
public interface IErrorCode {
    long getCode();
    String getMessage();
}
