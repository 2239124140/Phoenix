package com.zjy.phoenix.module.admin.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import com.zjy.phoenix.common.base.Mapper;
import com.zjy.phoenix.module.admin.model.WFile;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface WFileMapper  extends Mapper<WFile> {

    @Select("<script>" + "SELECT u.* FROM t_file u "
            + "WHERE isDel=0 "
            + "<if test=\"fileName!=null and fileName!=''\">"
            + "AND u.fileName like #{fileName} "
            + "</if>"
            + "ORDER BY u.createTime DESC "
            + "</script>")
    List<WFile> getPage(Pagination page, @Param("fileName") String fileName);
}
