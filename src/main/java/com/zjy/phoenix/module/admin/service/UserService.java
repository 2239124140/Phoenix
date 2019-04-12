package com.zjy.phoenix.module.admin.service;

import com.baomidou.mybatisplus.plugins.Page;

import com.zjy.phoenix.common.base.BaseService;
import com.zjy.phoenix.common.util.StrKit;
import com.zjy.phoenix.module.admin.mapper3.UserMapper3;
import com.zjy.phoenix.module.admin.model.User;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;


@Service
public class UserService extends BaseService<UserMapper3, User> {
	
	public User get(String userName, String password) {
		return this.selectOne(eq("userName", userName).eq("password", password).eq("isDel", 0).eq("status", 0));
	}

	public List<User> getList(){
		return this.selectList(eq("isDel", 0).orderBy("id", true));
	}
	
	public Page<User> getPage(Page<User> page, String name) throws ParseException {
	    name = StrKit.addLike(name);
		List<User> list = baseMapper.getPage(page, name);		
		return page.setRecords(list);
	}

	public void addOrEdit(User user){
		Long id =user.getId();
		if(id!=null){
			//编辑
			User user1=baseMapper.selectById(id);
			if(user1!=null){
				baseMapper.updateById(user);
			}
		}else{
			//新增
			baseMapper.insert(user);
		}
		
	}
	public void del(Long id){
		/*User user=baseMapper.selectById(id);
		if(user!=null){
			user.setIsDel(1);
			baseMapper.updateById(user);
		}*/
		int result = baseMapper.deleteById(id);
		System.out.println(result);
	}
}
