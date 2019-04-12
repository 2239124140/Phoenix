package com.zjy.phoenix.common.web;

import com.zjy.phoenix.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Description: 自定义servlet
 * @Author: ZhangJianYong
 * @Date: 19/4/12
 */
public class MyServlet extends HttpServlet {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private static final long serialVersionUID = 1L;
   //获取get方法
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
  //由于访问的地址是一个servlet请求   我们的异常没有办法被拦截 所以建立一个5xx.html
  // @ControllerAdvice用于拦截Controller的接口，比如当接口抛出异常时，可以被拦截，然后返回指定的报文（如错误信息、错误码），使用如下：
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = request.getParameter("method");
        try {
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this, request, response);
        } catch (Exception e) {
           // e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }
    //http://localhost/myServlet?method=helloServlet
    protected void helloServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("方法调用helloServlet。。。。。。。。。。。。");
        response.getWriter().write("helloServlet");
        //response.sendRedirect(request.getContextPath() + "/success.jsp");
    }

}
