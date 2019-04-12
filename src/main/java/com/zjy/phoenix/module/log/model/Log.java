package com.zjy.phoenix.module.log.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.zjy.phoenix.common.base.BaseModel;


import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志
 */
@TableName("core_log")
public class Log extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	

	private Long modelId;  // 表记录id
	private String tableName;  // 表名
	private String module; // 模块
	private String desc; // 操作描述
	private String type; // 操作类型 新增 删除 修改
	private String paras; // 参数
	private Long uid; // 操作人名称




	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParas() {
		return paras;
	}

	public void setParas(String paras) {
		this.paras = paras;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
}
