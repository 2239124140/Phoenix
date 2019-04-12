package com.zjy.phoenix.config.error;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
* @Description: 向错误页面传参
* @Author: ZhangJianYong
* @Date: 19/4/12
*/
@Configuration
public class ErrorConfig {
    /**
     * 向4xx.html  和 5xx.html 中添加自己的变量
     */
    @Bean
    public DefaultErrorAttributes errorAttributes() {
        DefaultErrorAttributes defaultErrorAttributes = new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
                Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
                map.put("company","Copyright ©\t\t\t\t超星集团");
                return map;
            }
        };
        return defaultErrorAttributes;
    }

 /*
 //错误页面是默认在 /error 路径下(在ErrorMvcAutoConfiguration.registerErrorPages（）设置 可以在配置文件中配置路径 error.path)的 4xx.HTML 5xx.html（在DefaultErrorViewResolver的默认配置中设置）
 //下面是定制的错误页面
 @Bean
    public ErrorPageRegistrar pageRegistrar() {
        ErrorPageRegistrar errorPageRegistrar = new ErrorPageRegistrar() {
            @Override
            public void registerErrorPages(ErrorPageRegistry registry) {
                ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/error400Page");
                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error401Page");
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error404Page");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error500Page");
                registry.addErrorPages(error400Page,error401Page,error404Page,error500Page);
            }
        };
        return errorPageRegistrar;
    }*/

}
