package com.zjy.phoenix.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Description: 自定义监听器  监听web启动过程
 * @Author: ZhangJianYong
 * @Date: 19/4/12
 */

public class MyListener implements ServletContextListener {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        log.info("contextInitialized 。。web 应用启动");
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("contextDestroyed 。。web 应用关闭");
    }
}
