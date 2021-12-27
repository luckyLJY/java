package com.tensquare.article.config;/**
 * Project: tensquare_parent
 * Package: com.tensquare.article.config
 * Version: 1.0
 * Created by ljy on 2021-12-26 15:45
 */

/**
 * @ClassName DruidStatFilter
 * @Author: ljy on 2021-12-26 15:45
 * @Version: 1.0
 * @Description
 */
import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(filterName="druidWebStatFilter",
        urlPatterns="/*",
        initParams={
                @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"),// 忽略资源
        })
public class DruidStatFilter extends WebStatFilter {

}