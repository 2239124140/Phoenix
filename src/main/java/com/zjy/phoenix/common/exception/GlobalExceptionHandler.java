package com.zjy.phoenix.common.exception;


import com.zjy.phoenix.common.util.StrKit;

import com.zjy.phoenix.config.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.PrintWriter;


/**
* @Description:  全局异常处理 对于自定义的servlet filter listener 异常捕获不到 用到默认的5xx.html
 * @Author: ZhangJianYong
* @Date: 19/4/12
*/
//标记类为aop切面@ControllerAdvice用于拦截Controller的接口，比如当接口抛出异常时，可以被拦截，然后返回指定的报文（如错误信息、错误码），使用如下：
@ControllerAdvice
public class GlobalExceptionHandler {

	private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// 全局异常处理，ajax和非ajax，声明要捕获的异常,方法名可以随便取
	// 表示处理异常的aop   Exception.class  表示处理那些异常 先处理小范围异常
	@ExceptionHandler(Exception.class)
	public Object excepitonHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
		// 判断是否ajax请求
		if ((request.getHeader("accept") != null && request.getHeader("accept").indexOf("application/json") > -1) || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)) {
			// 如果是ajax请求，JSON格式返回
			try {
				response.setContentType("application/json;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				Result result = dealException(e);
				writer.write(result.toJSONString());
				writer.flush();
				writer.close();
			} catch (IOException exception) {
				log.error(StrKit.printException(exception));
			}
		} else {
			// 如果不是ajax，html格式返回
			// 为安全起见，只有业务异常我们对前端可见，否则否则统一归为系统异常
			Result result = dealException(e);
			// 对于非ajax请求，我们都统一跳转到error.jsp页面
			return new ModelAndView("/error", result);
		}
		return null;
	}
	
	private Result dealException(Exception e){
		Result result = new Result();
		// 为安全起见，只有我们处理了的异常对前端可见，否则否则统一归为系统异常
		if (e instanceof BusinessException) {  // 自定义的异常
			BusinessException ex = (BusinessException) e;
			// result.setCode(ex.getCode());
			result.error(ex.getMessage());
		} else if (e instanceof BindException) {
			// hibernateValidator
			// model验证不通过时抛出的异常
			// (其他情况比如Integer传入过长也会产生这种异常)
			BindException ex = (BindException) e;
			result.error(ex.getAllErrors().get(0).getDefaultMessage());
		} else if (e instanceof ConstraintViolationException) {
			// hibernateValidator 方法参数验证不通过时抛出的异常
			ConstraintViolationException ex = (ConstraintViolationException) e;
			// result.setCode(Error.H_PARAM.getCode());
			result.error(ex.getConstraintViolations().iterator().next().getMessage());
		} else if (e instanceof MissingServletRequestParameterException) {
			// springboot controller方法中被 @RequestParam标注的参数未提供时抛出的异常
			MissingServletRequestParameterException ex = (MissingServletRequestParameterException) e;
			result.error("缺少必要参数,参数名称为" + ex.getParameterName());
		} else if (e instanceof MethodArgumentTypeMismatchException) {
			// MethodArgumentTypeMismatchException继承了TypeMismatchException
			MethodArgumentTypeMismatchException ex = (MethodArgumentTypeMismatchException) e;
			result.error("参数类型不匹配,参数" + ex.getName() + "类型应该为" + ex.getRequiredType().getSimpleName());
		}else {
			log.error(StrKit.printException(e));
			result.error("系统异常");
		}
		return result;
	}
}
