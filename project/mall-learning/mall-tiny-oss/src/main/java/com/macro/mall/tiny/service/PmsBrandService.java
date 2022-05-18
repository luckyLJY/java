package com.macro.mall.tiny.service;/**
 * Project: mall-tiny-oss
 * Package: com.macro.mall.tiny.service
 * Version: 1.0
 * Created by ljy on 2022-4-26 9:45
 */

import com.macro.mall.tiny.mbg.model.PmsBrand;

import java.util.List;

/**
 * @ClassName PmsBrandService
 * @Author: ljy on 2022-4-26 9:45
 * @Version: 1.0
 * @Description:
 */
public interface PmsBrandService {
    List<PmsBrand> listAllBrand();

    int createBrand(PmsBrand brand);

    int updateBrand(Long id, PmsBrand brand);

    int deleteBrand(Long id);

    List<PmsBrand> listBrand(int pageNum, int pageSize);

    PmsBrand getBrand(Long id);
}

