package com.zjy.phoenix.config.mybatisPlus;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 通用方法添加和编辑时自动维护的属性，要配合注解使用
 * @author zxl
 */
//@Component  不填加到容器中需要在配置文件中配置
public class CommonFieldHandler extends MetaObjectHandler {
	 
    //添加时自动填充createDate, version, creatorId字段
    @Override
    public void insertFill(MetaObject metaObject) {

        Object createTime = getFieldValByName("createTime", metaObject);
		Object updateTime = getFieldValByName("updateTime", metaObject);
        Object version = getFieldValByName("version", metaObject);
        this.setFieldValByName("isDel", 0, metaObject);
        //没有这个属性或属性值为空则自动填充(不为空则使用用户输入的)
		Date date = new Date();
        if (createTime == null) {
			setFieldValByName("createTime", date, metaObject);
        }
		if (updateTime == null) {
			setFieldValByName("updateTime", date, metaObject);
		}
        if (version == null) {
            setFieldValByName("version", 1, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //更新填充
        Object updateTime = getFieldValByName("updateTime", metaObject);
        //没有这个属性或属性值为空则自动填充(不为空则使用用户输入的)
        if (updateTime == null) {
	        setFieldValByName("updateTime", new Date(), metaObject);
	    }
    }
}
