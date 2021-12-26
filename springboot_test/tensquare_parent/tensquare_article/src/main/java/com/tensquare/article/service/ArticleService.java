package com.tensquare.article.service;/**
 * Project: tensquare_parent
 * Package: com.tensquare.article.service
 * Version: 1.0
 * Created by ljy on 2021-12-25 22:36
 */

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName ArticleService
 * @Author: ljy on 2021-12-25 22:36
 * @Version: 1.0
 * @Description
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private IdWorker idWorker;

    public List<Article> findAll() {
        return articleDao.selectList(null);
    }

    public Article findById(String articleId) {
        return  articleDao.selectById(articleId);
    }

    public void save(Article article) {
        /**
         * 1.使用分布式id生成器生成id
         * 2.初始化文章的浏览量、点赞、评论数
         * 3.使用Mybatis plus的save方法
         */
        String id = idWorker.nextId()+ "";
        article.setId(id);

        article.setVisits(0);
        article.setThumbup(0);
        article.setComment(0);

        articleDao.insert(article);
    }

    public void updateById(Article article) {
        //根据主键id修改
        articleDao.updateById(article);

        //根据修改条件修改
        /*
        EntityWrapper<Article> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("id",article.getId());
        articleDao.update(article,entityWrapper);*/
    }

    public void deletyById(String articleId) {
        articleDao.deleteById(articleId);
    }

    public Page<Article> findByPage(Map<String, Object> map, Integer page, Integer size) {

        EntityWrapper<Article> entityWrapper = new EntityWrapper<>();
        Set<String> keySet = map.keySet();
        for (String s : keySet) {
            entityWrapper.eq(map.get(s)!=null ,s ,map.get(s));
        }
        Page<Article> pageData = new Page<>(page,size);
        List<Article> articles = articleDao.selectPage(pageData, entityWrapper);
        pageData.setRecords(articles);
        return  pageData;

    }


}
