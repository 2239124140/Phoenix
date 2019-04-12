package com.zjy.phoenix.config.spring.postprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;


/**
* @Description:  启动时打印spring所有bean 做日志
* @Author: ZhangJianYong
* @Date: 19/4/12
*/
@Component
public class MyPostProcessor implements BeanPostProcessor {
	
	private final static Logger log = LoggerFactory.getLogger(MyPostProcessor.class);

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean; // we could potentially return any object reference here...
    }
    //所有bean初始化之后都会执行这个方法
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    	if(bean.toString().startsWith("com.chaoxing")){
			log.info("Bean '" + beanName + "' created : " + bean.toString());
    	}
        return bean;
    }

}
