package com.macro.mall.common.api;/**
 * Project: mall
 * Package: com.macro.mall.common.api
 * Version: 1.0
 * Created by ljy on 2022-5-9 9:45
 */

/**
 * @ClassName ResultCode
 * @Author: ljy on 2022-5-9 9:45
 * @Version: 1.0
 * @Description:
 */
/**
 * 常用API返回对象
 * Created by macro on 2019/4/19.
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