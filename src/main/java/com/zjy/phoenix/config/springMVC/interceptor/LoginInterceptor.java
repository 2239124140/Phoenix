
package com.zjy.phoenix.config.springMVC.interceptor;


import com.zjy.phoenix.common.annotation.LoginRequired;
import com.zjy.phoenix.common.util.LoginUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/** 
* @Description: 登录验证拦截器 
* @Author: ZhangJianYong 
* @Date: 19/4/12 
*/ 
@Component
public class LoginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
		LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
		LoginRequired classAnnotation = handlerMethod.getBeanType().getAnnotation(LoginRequired.class);
		// 类或方法上有AdminLoginRequired注解则进行处理
		if (methodAnnotation != null || classAnnotation != null) {
			return deal(request, response);
		}
		return true;
	}

	private boolean deal(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> currUser = LoginUtil.currentUser(request, response);
		if (currUser != null) {
			request.setAttribute("currUser", currUser);
			return true;
		} else {
			return toLoginUI(response);
		}
	}

	private boolean toLoginUI(HttpServletResponse response) throws IOException {
		response.sendRedirect("/login/loginUI");
		return false;
	}
}

