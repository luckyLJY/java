package com.macro.mall.tiny.service;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.service.impl
 * Version: 1.0
 * Created by ljy on 2022-4-14 9:40
 */

import com.macro.mall.tiny.mbg.model.PmsBrand;

import java.util.List;

/**
 * @ClassName PmsBrandService
 * @Author: ljy on 2022-4-14 9:40
 * @Version: 1.0
 * @Description:
 * PmsBrandService
 */
public interface PmsBrandService {
    List<PmsBrand> listAllBrand();

    int createBrand(PmsBrand pmsBrand);
    int updateBrand(Long id, PmsBrand pmsBrandDto);
    int deleteBrand(Long id);
    List<PmsBrand> listBrand(int pageNum, int pageSize);
    PmsBrand getBrand(Long id);
}
