package com.zjy.phoenix.module.admin.controller;


import com.zjy.phoenix.common.util.LoginUtil;

import com.zjy.phoenix.module.admin.model.User;
import com.zjy.phoenix.module.admin.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @Description: 登录验证
* @Author: ZhangJianYong
* @Date: 19/4/12
*/
@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("loginUI")
	public String loginUI() {

		return "login";
	}
	
	/**
	 * 退出登录
	 * 
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpServletResponse response, Model model) {
		LoginUtil.logoutUser(response);
		return "redirect:/login/loginUI";
	}


	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model, String userName,
                        String password) {
		model.addAttribute("userName", userName);

		// 参数验证
		if (StringUtils.isBlank(userName)) {
			model.addAttribute("errorMsgOne", "请输入用户名");
			return "login";
		}
		if (StringUtils.isBlank(password)) {
			model.addAttribute("errorMsgOne", "请输入密码");
			return "login";
		}

		User user = userService.get(userName, password);
		if (user != null) {
			LoginUtil.loginUser(request, response, user);
			return "redirect:/index";
		} else {
			model.addAttribute("errorMsgOne", "用户不存在");
			return "forward:/login/loginUI";
		}
	}

}
