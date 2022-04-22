package com.macro.mall.tiny.dao;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.dao
 * Version: 1.0
 * Created by ljy on 2022-4-21 9:53
 */

import com.macro.mall.tiny.nosql.elasticsearch.document.EsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName EsProductDao
 * @Author: ljy on 2022-4-21 9:53
 * @Version: 1.0
 * @Description:搜素系统中的商品管理自定义Dao
 */
public interface EsProductDao {
    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
