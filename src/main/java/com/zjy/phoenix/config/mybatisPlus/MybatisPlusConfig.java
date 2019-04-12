package com.zjy.phoenix.config.mybatisPlus;

import com.baomidou.mybatisplus.mapper.ISqlInjector;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.plugins.SqlExplainInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;



@Configuration
@MapperScan({"com.zjy.phoenix.module.*.mapper","com.zjy.phoenix.module.*.mapper2","com.zjy.phoenix.module.*.mapper3"})
// 配置mapper扫描,这里扫描之后启动类Application上面就不用再扫描
public class MybatisPlusConfig {

	private final static Logger log = LoggerFactory.getLogger(MybatisPlusConfig.class);


    /**
     * SQL 执行性能分析插件
     * 开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长
     * SQL执行效率插件,显示sql执行时间和sql语句
     *
     * performanceInterceptor.setMaxTime(5);//ms，超过此处设置的ms不执行
     如果执行时间过长，则抛出异常：The SQL execution time is too large,
     */
    @Bean
    @Profile({"dev","test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(100);//ms，超过此处设置的ms则sql不执行
        performanceInterceptor.setFormat(true);
        log.info("添加 performanceInterceptor ...");
        return performanceInterceptor;
    }
    //逻辑删除
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }
    
    /**
     * SQL安全插件,发现全表执行 delete update 停止运行抛出异常，mysql版本应高于5.6.3
     */
    @Bean
    @Profile({"dev","test"})// 设置 dev test 环境开启
    public SqlExplainInterceptor SqlExplainInterceptor() {
    	SqlExplainInterceptor sInterceptor = new SqlExplainInterceptor();
    	sInterceptor.setStopProceed(true);
    	return sInterceptor;
    }
    
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
    
    /**
     * 乐观锁插件
     * 取出记录时，获取当前version
     * 更新时，带上这个version
     * 执行更新时， set version = yourVersion+1 where version = yourVersion
     * 如果version不对，就更新失败
     */
    @Bean
    public OptimisticLockerInterceptor OptimisticLockerInterceptor() {
    	return new OptimisticLockerInterceptor();
    }
    
}
