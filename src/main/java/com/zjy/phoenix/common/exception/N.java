package com.zjy.phoenix.common.exception;

import java.util.Date;


/** 
* @Description: 用于业务层抛出异常的VO类 
* @Author: ZhangJianYong 
* @Date: 19/4/12 
*/ 
public class N {
	
	private N(){};  //私有化构造方法
	
	public static BusinessException businessException(String message) {
		return new BusinessException(message);
	}

	public static Date date() {
		return new Date();
	}
}
