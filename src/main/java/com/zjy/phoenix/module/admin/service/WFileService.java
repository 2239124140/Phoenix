package com.zjy.phoenix.module.admin.service;

import com.baomidou.mybatisplus.plugins.Page;

import com.zjy.phoenix.common.base.BaseService;
import com.zjy.phoenix.common.util.StrKit;
import com.zjy.phoenix.module.admin.mapper.WFileMapper;
import com.zjy.phoenix.module.admin.model.WFile;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class WFileService   extends BaseService<WFileMapper, WFile> {


    public List<WFile> getList(){
        return this.selectList(eq("isDel", 0).orderBy("id", true));
    }

    public Page<WFile> getPage(Page<WFile> page, String name) throws ParseException {
        name = StrKit.addLike(name);
        List<WFile> list = baseMapper.getPage(page, name);
        return page.setRecords(list);
    }

    public void addOrEdit(WFile model){
        Long id =model.getId();
        if(id!=null){
            //编辑
            WFile model1=baseMapper.selectById(id);
            if(model1!=null){
                baseMapper.updateById(model);
            }
        }else{
            //新增
            baseMapper.insert(model);
        }

    }
    public void del(Long id){
        int result = baseMapper.deleteById(id);
        System.out.println(result);
    }

}
