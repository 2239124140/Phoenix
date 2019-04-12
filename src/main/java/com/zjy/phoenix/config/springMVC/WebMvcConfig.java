package com.zjy.phoenix.config.springMVC;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import com.zjy.phoenix.config.springMVC.interceptor.LoginInterceptor;
import com.zjy.phoenix.config.springMVC.interceptor.RequestInterceptor;
import com.zjy.phoenix.config.springMVC.interceptor.TimeInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;


/**
* @Description:   使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
在SpringBoot2.0及Spring 5.0 WebMvcConfigurerAdapter已被废弃
* @Author: ZhangJianYong
* @Date: 19/4/12
*/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private RequestInterceptor requestInterceptor;
    @Autowired
    private TimeInterceptor timeInterceptor;

    @Autowired
    private LoginInterceptor loginInterceptor;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
    * @Description: 配置首页信息
    * @param： ViewControllerRegistry  视图注册器
    * @return:
    * @Author: ZhangJianYong
    * @Date: 19/4/12
    */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // super.addViewControllers(registry);
        //浏览器发送 / 请求来到 首页
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
    }


    /**
    * @Description:  添加拦截器
    * @param： null
    * @return:
    * @Author: ZhangJianYong
    * @Date: 19/4/12
    */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor).addPathPatterns("/**");
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
        log.info("添加 timeInterceptor ...");
        log.info("添加 requestInterceptor ...");
        log.info("添加 loginInterceptor ...");
    }


    /**
    * @Description:  把返回的默认jackjson 换为fastjson
    * @param： null
    * @return:
    * @Author: ZhangJianYong
    * @Date: 19/4/12
    */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        //1.需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2:添加fastJson的配置信息;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        /**
         第一个SerializerFeature.PrettyFormat可以省略，毕竟这会造成额外的内存消耗和流量，第二个是用来指定当属性值为null是是否输出：pro:null
        空值特别处理
         WriteNullListAsEmpty 将Collection类型字段的字段空值输出为[]
         WriteNullStringAsEmpty 将字符串类型字段的空值输出为空字符串 ""
         WriteNullNumberAsZero 将数值类型字段的空值输出为0
         WriteNullBooleanAsFalse 将Boolean类型字段的空值输出为false
         */
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue);
        //3处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //4.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
        return new HttpMessageConverters(converter);
    }



}