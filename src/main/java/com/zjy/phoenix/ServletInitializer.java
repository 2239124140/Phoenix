package com.zjy.phoenix;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
/** 
* @Description: war 包启动类 
* @Author: ZhangJianYong 
* @Date: 19/4/12 
*/ 
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        //此处的Application.class为带有@SpringBootApplication注解的启动类
        return builder.sources(PhoenixApplication.class);
    }

}