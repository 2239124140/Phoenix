package com.zjy.phoenix.config.multipledatasource;

/**
 * 多数据源枚举
 */
public enum DataSourceEnum {

	// 主要的数据源
	ONE("db1"),
	// 第二个数据源
	TWO("db2"),
	// 第三个数据源
	THREE("db3");

	private String value;

	DataSourceEnum(String value) {
	    this.value = value;
	}

	public String getValue() {
	    return value;
	}

}
