package com.macro.mall.tiny.nosql.mongodb.repository;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.nosql.mongodb.repository
 * Version: 1.0
 * Created by ljy on 2022-4-21 14:46
 */

import com.macro.mall.tiny.nosql.mongodb.document.MemberReadHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @ClassName MemberReadHistoryRepository
 * @Author: ljy on 2022-4-21 14:46
 * @Version: 1.0
 * @Description:会员商品浏览历史Resposity
 */
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory,String> {
    /**
     * 根据会员id按时间倒序获取浏览记录
     * @param memberId  会员id
     * @return
     */
    List<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId);
}
