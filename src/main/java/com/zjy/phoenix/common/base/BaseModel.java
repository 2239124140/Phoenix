package com.zjy.phoenix.common.base;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.FieldFill;

import java.io.Serializable;
import java.util.Date;


/**
* @Description: 自动填充  自动更新 逻辑删除   乐观锁
* @Author: ZhangJianYong
* @Date: 19/4/12
*/
public abstract class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Long id;
	@TableField(fill = FieldFill.INSERT)
	protected Date createTime; // 创建日期
	@TableField(fill = FieldFill.INSERT_UPDATE)
	protected Date updateTime; // 最后更新日期

	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	private Integer isDel;// 是否删除 0未删除 1已删除


	 @TableField(fill = FieldFill.INSERT)
	 @Version
	 protected Integer version ; //需要乐观锁的表就在子类中加上这个字段

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
