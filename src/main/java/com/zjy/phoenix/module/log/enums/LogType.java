package com.zjy.phoenix.module.log.enums;

// 日志类型
public enum LogType {
	add("新增"), update("修改"), del("删除"),;

	private String value;

	LogType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
