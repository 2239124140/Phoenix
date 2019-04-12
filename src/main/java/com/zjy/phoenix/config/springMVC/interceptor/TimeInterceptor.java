package com.zjy.phoenix.config.springMVC.interceptor;


import com.zjy.phoenix.common.util.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 打印请求耗时  非必须
 * @author zxl
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {
    
	private final static Logger log = LoggerFactory.getLogger(TimeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String uri = request.getRequestURI();
		if (!uri.contains(".")) {
			if (log.isDebugEnabled()) {
				System.out.println("\n");
			}
			log.debug("========请求开始=========");
			request.setAttribute("tJstartTime", System.currentTimeMillis());
		}
        return true;
    }
    @Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		try {
			String uri = request.getRequestURI();
			if (!uri.contains(".")) {
				Long start = (Long) request.getAttribute("tJstartTime");
				log.debug("耗时:" + (System.currentTimeMillis() - start));
				log.debug("========请求结束=========================================");
			}
		} catch (Exception e) {
			log.warn("时间拦截器 afterCompletion 出错，uri: {}", request.getRequestURI());
			log.error(StrKit.printException(e));
		}

    }

}
