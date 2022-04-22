package com.macro.mall.tiny.service;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.service
 * Version: 1.0
 * Created by ljy on 2022-4-21 9:47
 */

import com.macro.mall.tiny.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @ClassName EsProductService
 * @Author: ljy on 2022-4-21 9:47
 * @Version: 1.0
 * @Description:商品搜素管理Service
 */
public interface EsProductService {
    //从数据库中导入所有商品到ES
    int importAll();

    //根据id删除商品
    void delete(Long id);

    //根据id创建商品
    EsProduct create(Long id);

    //批量删除商品
    void delete(List<Long> ids);

    //根据关键字搜素名称或者副标题
    Page<EsProduct> search(String keyword,Integer pageNum,Integer pageSize);
}
