package com.zjy.phoenix.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @Description: 自定义过滤器  清除页面缓存  设置编码集
* @Author: ZhangJianYong
* @Date: 19/4/12
*/
public class MyFilter extends HttpFilter {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //清除页面缓存
        response.setDateHeader("Expires",-1);
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
       //主要是设置自定义servlet的字符编码
        request.setCharacterEncoding("UTF-8");

        log.info("已经过滤了请求 :"+request.getRequestURI());
        chain.doFilter(request, response);
    }


}
