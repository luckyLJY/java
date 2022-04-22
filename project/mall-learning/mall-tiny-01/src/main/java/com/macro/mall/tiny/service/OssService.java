package com.macro.mall.tiny.service;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.service
 * Version: 1.0
 * Created by ljy on 2022-4-22 11:38
 */

import com.macro.mall.tiny.dto.OssCallbackParam;
import com.macro.mall.tiny.dto.OssCallbackResult;
import com.macro.mall.tiny.dto.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName OssService
 * @Author: ljy on 2022-4-22 11:38
 * @Version: 1.0
 * @Description:oss上传管理Service
 */
public interface OssService {

    //oss上传策略生成
    OssPolicyResult policy();

    //oss上传成功回调
    OssCallbackResult callback(HttpServletRequest request);
}
