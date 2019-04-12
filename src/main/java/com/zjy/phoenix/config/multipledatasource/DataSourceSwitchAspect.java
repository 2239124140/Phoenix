package com.zjy.phoenix.config.multipledatasource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 数据源动态切换
 * @author zxl
 */
@Component
@Aspect
// @Order(-100) // 这是为了保证AOP在事务注解之前生效,Order的值越小,优先级越高(疑问，设置成10000貌似也正常)
public class DataSourceSwitchAspect {

	private static final Logger log = LoggerFactory.getLogger(DataSourceSwitchAspect.class);

	//四个*分别表示  任意返回类型  任意包名  任意类名  任意方法名
	//第一个..表示本包和子包  第二个..表示任意参数
	@Pointcut("execution(* com.zjy.phoenix.module.*.mapper..*.*(..))")
    private void db1Aspect() {}

	@Pointcut("execution(* com.zjy.phoenix.module.*.mapper2..*.*(..))")
    private void db2Aspect() {}
	
	@Pointcut("execution(* com.zjy.phoenix.module.*.mapper3..*.*(..))")
    private void db3Aspect() {}


	@Before("db1Aspect()")
    public void db1() {
        log.debug("mapper拦截：切换到db1 数据源...");
        DynamicDataSource.setDataSource(DataSourceEnum.ONE);    
    }

	@Before("db2Aspect()")
    public void db2 () {
		 log.debug("mapper拦截：切换到db2 数据源...");
        DynamicDataSource.setDataSource(DataSourceEnum.TWO);    
    }
	
	@Before("db3Aspect()")
    public void db3 () {
		 log.debug("mapper拦截：切换到db3 数据源...");
        DynamicDataSource.setDataSource(DataSourceEnum.THREE);    
    }
}
