package com.macro.mall.tiny.service;/**
 * Project: mall-tiny-redis
 * Package: com.macro.mall.tiny.service.impl
 * Version: 1.0
 * Created by ljy on 2022-4-27 10:31
 */

import com.macro.mall.tiny.mbg.model.PmsBrand;

import java.util.List;

/**
 * PmsBrandService
 * Created by macro on 2019/4/19.
 */
public interface PmsBrandService {

    int create(PmsBrand brand);

    int update(Long id, PmsBrand brand);

    int delete(Long id);

    PmsBrand getItem(Long id);

    List<PmsBrand> list(Integer pageNum, Integer pageSize);

    List<PmsBrand> ListAll();
}

