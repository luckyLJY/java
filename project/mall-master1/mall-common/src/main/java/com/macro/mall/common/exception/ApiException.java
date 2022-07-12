package com.macro.mall.common.exception;/**
 * Project: mall-master1
 * Package: com.macro.mall.common.exception
 * Version: 1.0
 * Created by ljy on 2022-6-18 16:23
 */

import com.macro.mall.common.api.IErrorCode;

/**
 * @ClassName ApiException
 * @Author: ljy on 2022-6-18 16:23
 * @Version: 1.0
 * @Description:自定义API异常
 */
public class ApiException extends RuntimeException {
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
