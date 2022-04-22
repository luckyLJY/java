package com.macro.mall.tiny.controller;/**
 * Project: mall-tiny-01
 * Package: com.macro.mall.tiny.controller
 * Version: 1.0
 * Created by ljy on 2022-4-21 10:08
 */

import com.macro.mall.tiny.common.api.CommonPage;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.nosql.elasticsearch.document.EsProduct;
import com.macro.mall.tiny.service.EsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.tokens.CommentToken;

import java.util.List;

/**
 * @ClassName EsProductController
 * @Author: ljy on 2022-4-21 10:08
 * @Version: 1.0
 * @Description:搜素商品管理Controller
 */
@Controller
@Api(tags = "EsProductController",description = "搜素商品管理")
@RequestMapping("/esProduct")
public class EsProductController {
    @Autowired
    private EsProductService esProductService;

    @ApiOperation(value = "导入所有数据库中商品ES")
    @RequestMapping(value = "/importAll",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> importAllList(){
        int count = esProductService.importAll();
        return CommonResult.success(count);
    }

    @ApiOperation(value = "根据id删除商品")
    @RequestMapping(value ="/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Object> delete(@PathVariable Long id){
        esProductService.delete(id);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id批量删除商品")
    @RequestMapping(value = "/delete/batch",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> delete(@RequestParam("ids")List<Long> ids){
        esProductService.delete(ids);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id创建商品")
    @RequestMapping(value = "/create/{id}",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<EsProduct> create(@PathVariable Long id){
        EsProduct esProduct = esProductService.create(id);
        if (esProduct != null){
            return CommonResult.success(esProduct);
        }else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "简单搜素")
    @RequestMapping(value = "/search/simple",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }
}
