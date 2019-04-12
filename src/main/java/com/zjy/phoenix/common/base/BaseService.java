package com.zjy.phoenix.common.base;


import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.MapUtils;

import com.zjy.phoenix.common.exception.N;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *
 * </p>
 *
 *
 */
/**
* @Description:  IService 实现类（ 泛型：M 是 mapper 对象，T 是实体 ， PK 是主键泛型 ）去掉了mybatisplus内置的批处理和事物
 *
 * 重新加上了IService  表示我们使用的接口实现方法 如果我们自定了先用我们的  没有使用serviceimpl 系统提供的
* @Author: ZhangJianYong
* @Date: 19/4/12
*/
@SuppressWarnings("unchecked")
public abstract class BaseService<M extends BaseMapper<T>, T>
        extends ServiceImpl<M , T> implements IService<T> {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
    protected M baseMapper;

    /**
     * <p>
     * 判断数据库操作是否成功
     * </p>
     * <p>
     * 注意！！ 该方法为 Integer 判断，不可传入 int 基本类型
     * </p>
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected static boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    @Override
	public boolean insert(T entity) {
        return retBool(baseMapper.insert(entity));
    }
    @Override
    public boolean insertAllColumn(T entity) {
        return retBool(baseMapper.insertAllColumn(entity));
    }

    @Override
    public boolean deleteById(Serializable id) {
        return SqlHelper.delBool(baseMapper.deleteById(id));
    }
    @Override
    public boolean deleteByMap(Map<String, Object> columnMap) {
        if (MapUtils.isEmpty(columnMap)) {
            throw new MybatisPlusException("deleteByMap columnMap is empty.");
        }
        return SqlHelper.delBool(baseMapper.deleteByMap(columnMap));
    }
    @Override
    public boolean delete(Wrapper<T> wrapper) {
        return SqlHelper.delBool(baseMapper.delete(wrapper));
    }
    @Override
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        return SqlHelper.delBool(baseMapper.deleteBatchIds(idList));
    }
    @Override
    public boolean updateById(T entity) {
        return retBool(baseMapper.updateById(entity));
    }
    @Override
    public boolean updateAllColumnById(T entity) {
        return retBool(baseMapper.updateAllColumnById(entity));
    }
    @Override
    public boolean update(T entity, Wrapper<T> wrapper) {
        return retBool(baseMapper.update(entity, wrapper));
    }
    @Override
    public boolean updateForSet(String setStr, Wrapper<T> wrapper) {
        return retBool(baseMapper.updateForSet(setStr, wrapper));
    }
    @Override
    public T selectById(Serializable id) {
        return baseMapper.selectById(id);
    }
    @Override
    public List<T> selectBatchIds(Collection<? extends Serializable> idList) {
        return baseMapper.selectBatchIds(idList);
    }
    @Override
    public List<T> selectByMap(Map<String, Object> columnMap) {
        return baseMapper.selectByMap(columnMap);
    }

	/*
	 * public T selectOne(Wrapper<T> wrapper) { return
	 * SqlHelper.getObject(baseMapper.selectList(wrapper)); }
	 */
    @Override
    public Map<String, Object> selectMap(Wrapper<T> wrapper) {
        return SqlHelper.getObject(baseMapper.selectMaps(wrapper));
    }
    @Override
    public Object selectObj(Wrapper<T> wrapper) {
        return SqlHelper.getObject(baseMapper.selectObjs(wrapper));
    }
    @Override
    public int selectCount(Wrapper<T> wrapper) {
        return SqlHelper.retCount(baseMapper.selectCount(wrapper));
    }
    @Override
    public List<T> selectList(Wrapper<T> wrapper) {
        return baseMapper.selectList(wrapper);
    }
    @Override
	public Page<T> selectPage(Page<T> page) {
        return selectPage(page, Condition.EMPTY);
    }
    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<T> wrapper) {
        return baseMapper.selectMaps(wrapper);
    }
    @Override
    public List<Object> selectObjs(Wrapper<T> wrapper) {
        return baseMapper.selectObjs(wrapper);
    }

    @Override
	public Page<Map<String, Object>> selectMapsPage(Page page, Wrapper<T> wrapper) {
        wrapper = (Wrapper<T>) SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.selectMapsPage(page, wrapper));
        return page;
    }
    @Override
	public Page<T> selectPage(Page<T> page, Wrapper<T> wrapper) {
        wrapper = (Wrapper<T>) SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.selectPage(page, wrapper));
        return page;
    }

	// 扩展方法,供子类使用  zxl
	protected Wrapper<T> eq(String column, Object params) {
		return Condition.create().eq(column, params);
	}

	// zxl
	public List<T> selectAll() {
		return baseMapper.selectList(null);
	}

	// zxl
	protected Wrapper<T> like(String column, String value) {
		return Condition.create().like(column, value);
	}

	// zxl
	protected Wrapper<T> orderBy(String column) {
		return Condition.create().orderBy(column);
	}

	// zxl
	protected Condition cond() {
		return Condition.create();
	}
    @Override
	// 抛异常版的 selectOne,查出多条抛出异常
	public T selectOne(Wrapper<T> wrapper) {
		List<T> list = baseMapper.selectList(wrapper);
		if (CollectionUtils.isNotEmpty(list)) {
			int size = list.size();
			if (size > 1) {
				log.error("期望查出一条记录，实际查出{}条记录", size);
				throw N.businessException("查出多条记录");
			}
			return list.get(0);
		}
		return null;
	}
}
