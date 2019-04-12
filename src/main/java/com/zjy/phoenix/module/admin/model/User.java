package com.zjy.phoenix.module.admin.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.zjy.phoenix.common.base.BaseModel;


/**
 * 用户类
 */
@TableName("t_user")
public class User extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8605480639568721129L;
	private String realName;// 真是姓名
	private Integer aid;// 单位id
	private String userName;// 用户名
	private String password;// 密码
	private Integer status;// 账号状态 0-正常 1-禁用

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
