package com.zjy.phoenix.module.admin.controller;

import com.alibaba.fastjson.JSONObject;

import com.zjy.phoenix.config.result.Result;
import com.zjy.phoenix.module.admin.model.EchartsVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * @program: phoenix
 * @description: 测试页面，echarts异步请求数据  返回数据类型处理方法
 * @author: ZhangJianYong
 * @create: 2019-04-11 16:41
 **/
@Controller
@RequestMapping("/echarts")
public class EchartsController {


    @RequestMapping("list")
    public String list() {
        return "echarts/list";
    }

    @RequestMapping("getAll")
    @ResponseBody
    public Result list(long id) {
        Result result = new Result();
        JSONObject jsonObject = new JSONObject();
        ArrayList<EchartsVO> echartsVOS = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            EchartsVO a = new EchartsVO("a"+i, 11);
            echartsVOS.add(a);
        }
        jsonObject.put("list",echartsVOS);
        jsonObject.put("all",191919);
        result.setData(jsonObject);
        return result;
    }
}
