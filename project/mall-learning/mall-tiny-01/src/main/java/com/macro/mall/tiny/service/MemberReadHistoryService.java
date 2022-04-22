package com.macro.mall.tiny.service;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.service
 * Version: 1.0
 * Created by ljy on 2022-4-21 14:59
 */

import com.macro.mall.tiny.nosql.mongodb.document.MemberReadHistory;

import java.util.List;

/**
 * @ClassName MemberReadHistoryService
 * @Author: ljy on 2022-4-21 14:59
 * @Version: 1.0
 * @Description:会员浏览记录管理Service
 */
public interface MemberReadHistoryService {
    //生成浏览记录
    int create(MemberReadHistory memberReadHistory);

    //批量删除浏览记录
    int delete(List<String> ids);

    //获取用户浏览记录
    List<MemberReadHistory> list(Long memberId);
}
