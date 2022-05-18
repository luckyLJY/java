package com.macro.mall.common.api;/**
 * Project: mall
 * Package: com.macro.mall.common.api
 * Version: 1.0
 * Created by ljy on 2022-5-9 9:44
 */

/**
 * @ClassName IErrorCode
 * @Author: ljy on 2022-5-9 9:44
 * @Version: 1.0
 * @Description:
 */

/**
 * 常用API返回对象接口
 * Created by macro on 2019/4/19.
 */
public interface IErrorCode {
    /**
     * 返回码
     */
    long getCode();

    /**
     * 返回信息
     */
    String getMessage();
}
