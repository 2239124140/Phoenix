package com.zjy.phoenix.module.admin.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@PropertySource(value = {"classpath:conf/version.properties"},encoding = "utf-8")
@ConfigurationProperties(prefix="version")
@Component
public class Version {
	private String name;
	private String version;
	private String desc;
	private String date;
	private String url;
	private Map<String,Version> versionMap = new HashMap<>();
	
	    
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String,Version> getVersionMap() {
		return versionMap;
	}
	public void setVersionMap(Map<String,Version> versionMap) {
		this.versionMap = versionMap;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	
}