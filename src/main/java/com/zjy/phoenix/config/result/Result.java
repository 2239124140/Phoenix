package com.zjy.phoenix.config.result;

import com.alibaba.fastjson.JSONObject;

/**
* @Description:  统一返回结果
* @Author: ZhangJianYong
* @Date: 19/4/12
*/
public class Result extends JSONObject {
//	private boolean success = true;// 是否成功
//	private String msg = "";// 提示信息
//	private Object data;// 其他信息
//  private Integer code;  // 错误码，用来代替错误消息，目前默认0为自定义消息
	
	private static final long serialVersionUID = 1L;

	public Result(){
		put("success", true);  // 默认为true
		put("msg", "操作成功");
	}
	

	
	public Result success(String msg) { // 请求成功的提示信息
		put("success", true);  // 默认为true
		put("msg", msg);
		return this;
	}
	
	public Result error(String msg) { // 请求失败的提示信息
		put("success", false);  
		// put("code", Error.CUSTOM.getCode());
		put("msg", msg);
		return this;
	}
	

	
	public void setData(Object data) {
		put("data", data);
	}
	
	public String getMsg() {
		return getString("msg");
	}

	public Object getData() {
		return get("data");
	}

	public boolean isSuccess() {
		return 	getBooleanValue("success");
	}



	
}
