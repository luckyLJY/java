package com.macro.mall.common.api;/**
 * Project: mall-master1
 * Package: com.macro.mall.common.api
 * Version: 1.0
 * Created by ljy on 2022-6-18 15:31
 */

/**
 * @ClassName IErrorCode
 * @Author: ljy on 2022-6-18 15:31
 * @Version: 1.0
 * @Description:常用API返回对象接口
 */

public interface IErrorCode {
    //返回码
    long getCode();
    //返回信息
    String getMessage();
}
