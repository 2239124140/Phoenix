package com.zjy.phoenix.config.springMVC.interceptor;



import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 打印请求url和参数  非必须
 * @author zxl
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {

	private final static Logger log = LoggerFactory.getLogger(TimeInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).setAttribute("request", request,
				RequestAttributes.SCOPE_REQUEST);

		String uri = request.getRequestURI().toString();
		// if (url.contains("/meeting/")) {
		if (!uri.contains(".")) {
			// System.out.println(request.getRequestURL());
			log.debug("url：" + request.getRequestURL().toString());
			log.debug("类型：" + request.getMethod());
			Enumeration<String> paraNames = request.getParameterNames();
			String params = "";
			for (Enumeration<String> e = paraNames; e.hasMoreElements();) {
				String thisName = e.nextElement().toString();
				String thisValue = request.getParameter(thisName);
				if (StringUtils.isNotBlank(thisValue) && thisValue.length() > 150) {
					thisValue = thisValue.substring(0, 150);
				}
				params = params + "&" + thisName + "=" + thisValue;
			}
			if (!"".equals(params)) {
				log.debug("参数：" + params.substring(1));
			}
		}
		return true;
	}
}
