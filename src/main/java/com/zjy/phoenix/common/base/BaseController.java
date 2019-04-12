package com.zjy.phoenix.common.base;



import com.zjy.phoenix.common.exception.BusinessException;
import com.zjy.phoenix.common.util.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @Description: 基础控制层放置基础的方法  获取用户  用户验证
* @Author: ZhangJianYong
* @Date: 19/4/12
*/
public abstract class BaseController {

	/*controller在注入的request对象是一个代理对象通过jdk的proxy实现
	其中InvocationHandler是AutowireUtils.ObjectFactoryDelegatingInvocationHandler，
	创建这个handler需要传入一个objectFactory，WebApplicationContextUtils.RequestObjectFactory,
	通过这个requestFactory获取的线程相关的RequestAttributes，
	RequestContextHolder.currentRequestAttributes，
	通过RequestAttribute获取request*/
	@Autowired
	private HttpServletRequest req;

	protected final Logger log = LoggerFactory.getLogger(getClass());
	

	protected Long getUserId() {
		String id = getCookie("uid");
		if (id != null) {
			return  Long.parseLong(id);
		} else {
			throw new BusinessException("用户未登录或用户未设置角色");
		}

	}



	protected String getCookie(String name) {
		return CookieUtil.getCookie(req, name);
	}


	// 使用response向浏览器弹出消息
	public void alert(HttpServletResponse response, String msg) {
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			// String msg = "系统异常";
			msg = "<script>alert('" + msg + "')</script>";
			// 点击确定后会关闭当前页面
			// msg = "<script>alert('" + msg + "');window.opener=null; window.open('','_self');window.close();</script>";
			response.getWriter().write(msg);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
