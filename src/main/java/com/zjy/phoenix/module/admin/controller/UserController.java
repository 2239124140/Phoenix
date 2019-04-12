package com.zjy.phoenix.module.admin.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zjy.phoenix.common.annotation.LoginRequired;
import com.zjy.phoenix.common.base.BaseController;
import com.zjy.phoenix.config.result.Result;
import com.zjy.phoenix.module.admin.model.User;
import com.zjy.phoenix.module.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Map;

/**
* @Description: 用户模块
* @Author: ZhangJianYong
* @Date: 19/4/12
*/
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	UserService userService;

    @LoginRequired
	@RequestMapping("list")
	public String list() {
		return "user/list";
	}
	
	
	@ResponseBody
	@RequestMapping("/getData")
	public Result getData(Map<String, Object> map, @RequestParam(defaultValue = "1") Integer pageNum,
						  @RequestParam(defaultValue = "15") Integer pageSize, String name) {
		Result result = new Result();

		Page<User> page = new Page<User>(pageNum, pageSize);
		try {
			page = userService.getPage(page, name);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.setData(page);
		return result;
	}

	@ResponseBody
	@RequestMapping("/findById")
	public Result findById(Long id) {
		Result result = new Result();
		User model = userService.selectById(id);
		result.setData(model);
		return result;
	}

	@ResponseBody
	@RequestMapping("/addoredit")
	public Result addOrEdit(Map<String, Object> map, User user, HttpServletRequest request) {
		Result result = new Result();
		userService.addOrEdit(user);
		return result;
	}

	@ResponseBody
	@RequestMapping("/del")
	public Result del(Map<String, Object> map, Long id) {
		Result result = new Result();
		userService.del(id);
		return result;
	}
}
