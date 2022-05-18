package com.macro.mall.tiny.mbg;/**
 * Project: mall-learning
 * Package: com.macro.mall.tiny.mbg
 * Version: 1.0
 * Created by ljy on 2022-4-24 10:31
 */

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

/**
 * @ClassName CommentGenerator
 * @Author: ljy on 2022-4-24 10:31
 * @Version: 1.0
 * @Description:自定义注释生成器
 * 1. 设置用户配置参数：addConfigurationProperties
 * 2. 给字段添加注释：addFieldComment
 * 3. 给model的字段添加注释：addFieldJavaDoc
 */
public class CommentGenerator extends DefaultCommentGenerator {
    private boolean addRemarkComments = false;

    //设置用户配置参数
    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
    }

    //给字段添加注释
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        //根据参数和备注信息判断是否添加添加备注信息
        if (addRemarkComments && StringUtility.stringHasValue(remarks)){
            addFieldJavaDoc(field,remarks);
        }
    }

    //给model的字段添加注释
    private void addFieldJavaDoc(Field field,String remarks){
        //文档注释开始
        field.addJavaDocLine("/**");
        //获取数据库段的备注信息
        String[] remarkLines = remarks.split(System.getProperty("line.separator"));
        for (String remarkLine:remarkLines){
            field.addJavaDocLine("*"+remarkLine);
        }
        addJavadocTag(field,false);
        field.addJavaDocLine("*/");
    }
}
