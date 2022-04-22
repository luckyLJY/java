package com.macro.mall.tiny.common.api;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.common
 * Version: 1.0
 * Created by ljy on 2022-4-14 10:09
 */

/**
 * @ClassName ResultCode
 * @Author: ljy on 2022-4-14 10:09
 * @Version: 1.0
 * @Description:
 * 枚举了一些常用API操作码
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
