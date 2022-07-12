package com.macro.mall.security.annotation;/**
 * Project: mall-master1
 * Package: com.macro.mall.security.config.annotation
 * Version: 1.0
 * Created by ljy on 2022-6-18 16:37
 */

import java.lang.annotation.*;

/**
 * @ClassName CacheException
 * @Author: ljy on 2022-6-18 16:37
 * @Version: 1.0
 * @Description:自定义注解，有该注解的缓存方法会抛出异常
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}