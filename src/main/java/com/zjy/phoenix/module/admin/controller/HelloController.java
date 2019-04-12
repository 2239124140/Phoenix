package com.zjy.phoenix.module.admin.controller;

import com.alibaba.fastjson.JSONObject;

import com.zjy.phoenix.common.exception.BusinessException;
import com.zjy.phoenix.config.result.Result;
import com.zjy.phoenix.module.admin.model.JSONVO;
import com.zjy.phoenix.module.admin.model.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/** 
* @Description:
* @Author: ZhangJianYong 
* @Date: 19/4/11
*/ 
@Controller
public class HelloController {

    //测试配置文件中 值注入
    @Value("${client.ipAddress}")
    private  String client;



    //测试外部配置文件导入配置
    @Autowired
    private Version version;


    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
       return "hello";
    }

    //测试json 是否转为fastjson
    @RequestMapping("/json")
    @ResponseBody
    public JSONVO json(){
        JSONVO jsonvo = new JSONVO();
        jsonvo.setAge(12);
        jsonvo.setBoy(true);
        jsonvo.setName("张三");
        return jsonvo;
    }

    @RequestMapping("/version")
    @ResponseBody
    public JSONObject version(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("client",client);
        //jsonObject.put("proxy",proxy);
        jsonObject.put("version",version);
        return jsonObject;
    }


    // 系统异常
    // http://localhost:81/test/error1
    @RequestMapping("error1")
    @ResponseBody
    public Result error1(Map<String,Object> map) {
        throw new RuntimeException();
    }


    // 自定义异常
    // http://localhost:81/test/error2
    @RequestMapping("error2")
    @ResponseBody
    public Result error2(Map<String,Object> map) {
        int a = 1;
        a = a+1;
        throw new BusinessException("自定义异常");
    }
}

