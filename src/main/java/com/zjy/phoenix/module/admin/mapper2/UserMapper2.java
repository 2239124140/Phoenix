package com.zjy.phoenix.module.admin.mapper2;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import com.zjy.phoenix.common.base.Mapper;
import com.zjy.phoenix.module.admin.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface UserMapper2 extends Mapper<User> {

	@Select("<script>" + "SELECT u.* FROM t_user u " 
	        + "WHERE isDel=0 " 
			+ "<if test=\"realName!=null and realName!=''\">"
			+ "AND u.realName like #{realName} " 
			+ "</if>" 
			+ "ORDER BY u.createTime DESC " 
			+ "</script>")
	List<User> getPage(Pagination page, @Param("realName") String realName);
}
