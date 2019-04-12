/**
 * Copyright &copy; 2017-2027 <a href="http://www.cnony.com">JeeHook</a> All rights reserved.
 */
package com.zjy.phoenix.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Cookie工具类
 * @author zxl
 * @date 2018年9月18日
 */
public class CookieUtil {

	/**
	 * 设置 Cookie（浏览器关闭时失效）
	 * @param name 名称
	 * @param value 值
	 */
	public static void setCookie(HttpServletResponse response, String name, String value) {
		// setCookie(response, name, value, 60*60*24);
		setCookie(response, name, value, -1);
	}
	
	/**
	 * 设置 Cookie
	 * @param name 名称
	 * @param value 值
	 * @param path 路径
	 *             maxAge 生存时间（单位秒）
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path) {
		setCookie(response, name, value, path, 60*60*24);
	}
	
	/**
	 * 设置 Cookie
	 * @param name 名称
	 * @param value 值
	 * @param maxAge 生存时间（单位秒）
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
		setCookie(response, name, value, "/", maxAge);
	}
	
	/**
	 * 设置 Cookie
	 * @param name 名称
	 * @param value 值
	 * @param maxAge 生存时间（单位秒）
	 * @param path 路径
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
		Cookie cookie = new Cookie(name, null);
		if(path == null){
			cookie.setPath("/");
		}else{
			cookie.setPath(path);
		}
		cookie.setMaxAge(maxAge); // maxAge为负数代表关闭浏览器时失效
        if(value !=null){
			try {
				cookie.setValue(URLEncoder.encode(value, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		cookie.setHttpOnly(true); // 设置后在浏览器的document对象中就看不到cookie了
		response.addCookie(cookie);
	}
	
	/**
	 * 获得指定Cookie的值
	 * @param name 名称
	 * @return 值
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		return getCookie(request, null, name, false);
	}



	/**
	 * 获得指定Cookie的值
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param name 名字
	 * @param isRemove 是否移除
	 * @return 值
	 */
	public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemove) {
		String value = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					try {
						value = URLDecoder.decode(cookie.getValue(), "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (isRemove) {
						// cookie.setMaxAge(0);
						// response.addCookie(cookie);
						delCookie(response, cookie);
					}
				}
			}
		}
		return value;
	}

	/**
	 * 清除 某个指定的cookie
	 */
	public static void removeCookie(HttpServletResponse response, String name) {
		setCookie(response, name, null, 0);
	}


	// 删除cookie zxl
	public static void delCookie(HttpServletResponse response, Cookie cookie) {
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	// 删除全部cookie
	public static void deleteAllCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				delCookie(response, cookies[i]);
			}
		}
	}
	
	// 查询cookie并转换为Integer  zxl  
	public Integer getCookieToInt(HttpServletRequest request, String name) {
		String result = getCookie(request, name);
		return result != null ? Integer.parseInt(result) : null;
	}
	
	// 查询cookie并转换为Long  zxl  
	public Long getCookieToLong(HttpServletRequest request, String name) {
		String result = getCookie(request, name);
		return result != null ? Long.parseLong(result) : null;
	}
}
