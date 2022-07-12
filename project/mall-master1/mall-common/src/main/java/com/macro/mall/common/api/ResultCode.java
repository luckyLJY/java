package com.macro.mall.common.api;/**
 * Project: mall-master1
 * Package: com.macro.mall.common.api
 * Version: 1.0
 * Created by ljy on 2022-6-18 15:33
 */

/**
 * @ClassName ResultCode
 * @Author: ljy on 2022-6-18 15:33
 * @Version: 1.0
 * @Description:常用API返回对象
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");
    private long code;
    private String message;

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }
}
