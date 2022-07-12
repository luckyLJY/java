package com.tensquare.article.repository;/**
 * Project: tensquare_parent
 * Package: com.tensquare.article.repository
 * Version: 1.0
 * Created by ljy on 2022-6-25 16:32
 */

import com.tensquare.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * @ClassName CommentRepository
 * @Author: ljy on 2022-6-25 16:32
 * @Version: 1.0
 * @Description:
 */
public interface CommentRepository extends MongoRepository<Comment,String> {

    //SpringDataMongoDB,支持通过方法名进行查询定义的方式
    //根据文章id查询文章评论数据
    List<Comment> findByArticleid(String articleId);

    //根据发布时间和点赞查询
    //List<Comment> findByPublishdateAndThumbup(Date date, Integer thumbup);

    //根据用户id查询，并且根据发布时间倒序排序
    //List<Comment> findByUseridOrderbOrderByPublishdateDesc(String userid);
}
