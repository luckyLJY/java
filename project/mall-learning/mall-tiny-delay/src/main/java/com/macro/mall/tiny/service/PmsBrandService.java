package com.macro.mall.tiny.service;/**
 * Project: mall-tiny-delay
 * Package: com.macro.mall.tiny.service.impl
 * Version: 1.0
 * Created by ljy on 2022-4-26 15:55
 */

/**
 * @ClassName PmsBrandService
 * @Author: ljy on 2022-4-26 15:55
 * @Version: 1.0
 * @Description:
 */

import com.macro.mall.tiny.mbg.model.PmsBrand;

import java.util.List;

/**
 * PmsBrandService
 * Created by macro on 2019/4/19.
 */
public interface PmsBrandService {
    List<PmsBrand> listAllBrand();

    int createBrand(PmsBrand brand);

    int updateBrand(Long id, PmsBrand brand);

    int deleteBrand(Long id);

    List<PmsBrand> listBrand(int pageNum, int pageSize);

    PmsBrand getBrand(Long id);
}
