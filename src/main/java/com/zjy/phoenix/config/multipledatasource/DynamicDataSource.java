package com.zjy.phoenix.config.multipledatasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源（数据源切换）
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSource = getDataSource();
		// log.debug("mapper获取连接：使用{} 数据源...", dataSource);
		return dataSource;
    }

    /**
     * 设置数据源
     */
    public static void setDataSource(DataSourceEnum dataSourceEnum) {
        CONTEXT_HOLDER.set(dataSourceEnum.getValue());
    }

    /**
     * 获取数据源
     */
    public static String getDataSource() {
        String dataSource = CONTEXT_HOLDER.get();
        // 如果没有指定数据源，使用默认数据源
        if (null == dataSource) {
			DynamicDataSource.setDataSource(DataSourceEnum.ONE);
        }
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }

}
