package com.macro.mall.tiny.nosql.elasticsearch.repository;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.nosql.elasticsearch.repository
 * Version: 1.0
 * Created by ljy on 2022-4-21 9:43
 */

import com.macro.mall.tiny.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @ClassName EsProductRepository
 * @Author: ljy on 2022-4-21 9:43
 * @Version: 1.0
 * @Description:商品es操作类
 */
public interface EsProductRepository extends ElasticsearchRepository<EsProduct,Long> {
    /**
     * 搜索查询
     *
     * @param name              商品名称
     * @param subTitle          商品标题
     * @param keywords          商品关键字
     * @param page              分页信息
     * @return
     */
    Page<EsProduct> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords, Pageable page);
}
