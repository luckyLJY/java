package com.macro.cloud.filter;/**
 * Project: mall-sclearning
 * Package: com.macro.cloud.filter
 * Version: 1.0
 * Created by ljy on 2022-5-11 10:23
 */


import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @ClassName HystrixRequestContextFilter
 * @Author: ljy on 2022-5-11 10:23
 * @Version: 1.0
 * @Description:
 */
@Component
@WebFilter(urlPatterns = "/*",asyncSupported = true)
public class HystrixRequestContextFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            filterChain.doFilter(servletRequest,servletResponse);
        }finally {
            context.close();
        }
    }
}
