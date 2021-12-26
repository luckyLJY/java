package com.tensquare.article.controller;/**
 * Project: tensquare_parent
 * Package: com.tensquare.article.controller
 * Version: 1.0
 * Created by ljy on 2021-12-25 22:37
 */

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.ArticleApplication;
import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ArticleController
 * @Author: ljy on 2021-12-25 22:37
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //GET /article 查询全部文章列表
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Article> list = articleService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    //GET /article/{articleId} 根据id查询
    @RequestMapping(value = "{articleId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String articleId){
       Article article =  articleService.findById(articleId);
       return new Result(true,StatusCode.OK,"查询成功",article);
    }

    // POST /article 增加文章
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article) {
        articleService.save(article);
        return new Result(true, StatusCode.OK, "新增成功");
    }

    // PUT /article/{articleId} 修改文章
    @RequestMapping(value = "{articleId}",method = RequestMethod.PUT)
    public Result updateById(@PathVariable String articleId,
                             @RequestBody Article article){
        article.setId(articleId);

        articleService.updateById(article);
        return  new Result(true, StatusCode.OK,"修改成功");
    }

    //DELETE /article/{articleId} 删除文章
    @RequestMapping(value = "{articleId}",method = RequestMethod.DELETE)
    public  Result deleteById(@PathVariable String articleId ){
        articleService.deletyById(articleId);
        return  new Result(true,StatusCode.OK,"删除成功");
    }

    //POST /article/search/{page}/{size} 分页查询
    @RequestMapping(value = "search/{page}/{size}",method = RequestMethod.POST)
    public Result findByPage(@PathVariable Integer page,
                             @PathVariable Integer size,
                             @RequestBody Map<String, Object> map){
        /**
         *
         * 1.根据条件查询
         * 2.封装分页对象查询返回对象
         * 3.返回数据
         */
        Page<Article> pageData = articleService.findByPage(map,page,size);
        PageResult<Article> pageResult = new PageResult<>(pageData.getTotal(), pageData.getRecords());
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

    //测试公共异常处理
    @RequestMapping(value = "exception", method = RequestMethod.GET)
    public Result test() {
        int a = 1 / 0;

        return null;
    }
}
