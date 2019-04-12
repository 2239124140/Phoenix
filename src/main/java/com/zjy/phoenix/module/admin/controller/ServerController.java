package com.zjy.phoenix.module.admin.controller;



import com.zjy.phoenix.config.spring.SpringContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: myproject01
 * @description: 测试服务停止
 * @author: ZhangJianYong
 * @create: 2019-04-12 10:37
 **/
@Controller
@RequestMapping("/server")
public class ServerController {

    @RequestMapping("/webServer")
    @ResponseBody
    public String hello() throws Exception {
        new SpringContextHolder().destroy();
        return "hello";
    }
}
