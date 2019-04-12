package com.zjy.phoenix.config.multipledatasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
* @Description:  多数据源配置
* @Author: ZhangJianYong
* @Date: 19/4/12
*/
@Configuration
public class DataSourceConfig {
	
	//动态数据源,默认是db1
	@Primary
	@Bean
	public DataSource multipleDataSource() {
    	DynamicDataSource dynamicDatasource = new DynamicDataSource();
        //默认数据源
		dynamicDatasource.setDefaultTargetDataSource(dataSource1());
        Map<Object,Object> dbMap = new HashMap<>();
        dbMap.put("db1",dataSource1());
        dbMap.put("db2",dataSource2());
        dbMap.put("db3",dataSource3());
        dynamicDatasource.setTargetDataSources(dbMap);
        return dynamicDatasource;
    }
    
	//@Primary
	@Bean(name = "db1")
    @ConfigurationProperties(prefix = "spring.datasource.db1") // application.properteis中对应属性的前缀
    public DataSource dataSource1() {
		return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "db2")
    @ConfigurationProperties(prefix = "spring.datasource.db2") // application.properteis中对应属性的前缀
    public DataSource dataSource2() {
		return DruidDataSourceBuilder.create().build();
    }
    
    @Bean(name = "db3")
    @ConfigurationProperties(prefix = "spring.datasource.db3") // application.properteis中对应属性的前缀
    public DataSource dataSource3() {
		return DruidDataSourceBuilder.create().build();
    }


}
