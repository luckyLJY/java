package com.tensquare.article.controller;/**
 * Project: tensquare_parent
 * Package: com.tensquare.article.controller
 * Version: 1.0
 * Created by ljy on 2021-12-26 14:44
 */

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName BaseExceptionHandler
 * @Author: ljy on 2021-12-26 14:44
 * @Version: 1.0
 * @Description
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handler(Exception e) {
        System.out.println("处理异常");

        return new Result(false, StatusCode.ERROR, e.getMessage());
    }

}