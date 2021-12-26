package com.tensquare.article;/**
 * Project: tensquare_parent
 * Package: com.tensquare.article
 * Version: 1.0
 * Created by ljy on 2021-12-25 21:58
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @ClassName ArticleApplication
 * @Author: ljy on 2021-12-25 21:58
 * @Version: 1.0
 * @Description:文章springboot启动类
 */
@SpringBootApplication
//配置Mapper包扫描
@MapperScan("com.tensquare.article.dao")
public class ArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class,args);
    }

    @Bean
    public IdWorker createIdWorker(){
        return new IdWorker(1,1);
    }
}
