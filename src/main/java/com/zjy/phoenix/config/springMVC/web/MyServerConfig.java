package com.zjy.phoenix.config.springMVC.web;


import com.zjy.phoenix.common.web.MyFilter;
import com.zjy.phoenix.common.web.MyListener;
import com.zjy.phoenix.common.web.MyServlet;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


/**
* @Description: tomcat配置
* @Author: ZhangJianYong
* @Date: 19/4/12
*/
@Configuration
public class MyServerConfig {


    /**
    * @Description:  一定要将这个定制器加入到容器中   优先定制器的配置 其次配置文件中的配置
    * @param： null
    * @return:
    * @Author: ZhangJianYong
    * @Date: 19/4/12
    */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> embeddedServletContainerCustomizer() {

           return  new WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>(){

               @Override
               public void customize(ConfigurableServletWebServerFactory factory) {
                   TomcatServletWebServerFactory tomcatFactory = (TomcatServletWebServerFactory) factory;
                   //tomcatFactory.setPort(81);
                   tomcatFactory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
                       @Override
                       public void customize(Connector connector) {
                           Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
                           protocol.setMaxConnections(200);
                           protocol.setMaxThreads(200);
                           protocol.setSelectorTimeout(3000);
                           protocol.setSessionTimeout(3000);
                           protocol.setConnectionTimeout(3000);
                       }
                   });
               }
           };

    }


    //注册三大组件
    @Bean
    public ServletRegistrationBean myServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new MyServlet(), "/myServlet");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MyFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/hello", "/myServlet"));
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean myListener() {
        ServletListenerRegistrationBean<MyListener> registrationBean = new
                ServletListenerRegistrationBean<>(new MyListener());
        return registrationBean;
    }
}
