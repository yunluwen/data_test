package com.zyh.house_scala.concurrent;

import com.zyh.house_scala.concurrent.threadLocal.RequestHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//自定义过滤器，通过 @WebFilter 注解来配置
@Component
@WebFilter(urlPatterns = "/threadLocal/*", filterName = "authFilter")
public class HttpFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        System.out.println("do Filter:"+Thread.currentThread().getId()+
                " "+request.getServletPath());
        RequestHolder.add(Thread.currentThread().getId());
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
