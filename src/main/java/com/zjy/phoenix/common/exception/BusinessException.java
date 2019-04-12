/**
 */
package com.zjy.phoenix.common.exception;


/**
* @Description:  Service层公用的Exception, 从由Spring管理事务的函数中抛出时会触发事务回滚\自定义异常  controller 直接 new  Service层 使用N 类
* @Author: ZhangJianYong
* @Date: 19/4/12
*/
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message);
	}

}
