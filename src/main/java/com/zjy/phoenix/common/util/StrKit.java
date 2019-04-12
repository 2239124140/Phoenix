package com.zjy.phoenix.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/** 
* @Description: 字符串工具包 
* @Author: ZhangJianYong 
* @Date: 19/4/12
*/ 
public class StrKit extends org.apache.commons.lang3.StringUtils{
    // 字符串前后添加百分号
    public static String addLike(String str) {
        if (isNotBlank(str)) {
            str = "%" + str + "%";
        }
        return str;
    }

    // 输出异常信息为string
    public static String printException(Exception e) {
        StringWriter trace = new StringWriter();
        e.printStackTrace(new PrintWriter(trace));
        return trace.toString();
    }

}
