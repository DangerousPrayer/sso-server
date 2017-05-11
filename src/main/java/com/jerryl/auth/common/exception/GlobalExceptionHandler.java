package com.jerryl.auth.common.exception;

import com.jerryl.auth.Config;
import com.jerryl.auth.common.Status;
import com.jerryl.auth.common.ToWeb;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

/**
 * Created by liuruijie on 2016/12/28.
 * 全局异常处理，捕获所有Controller中抛出的异常。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	//处理自定义的异常
	@ExceptionHandler(BaseException.class)
	@ResponseBody
	public Object customHandler(BaseException e){
//		e.printStackTrace();
		return ToWeb.buildResult().status(e.getCode()).msg(e.getMessage());
	}

	@ExceptionHandler(BadSqlGrammarException.class)
	@ResponseBody
	public Object tableNotFound(BadSqlGrammarException e){
		Exception e1 = (Exception) e.getCause();
		if(e1 instanceof MySQLSyntaxErrorException){
			MySQLSyntaxErrorException ex = (MySQLSyntaxErrorException) e1;
			if(ex.getErrorCode() == 1146){
				return ToWeb.buildResult().status(Status.TABLE_MISSNG);
			}
		}
		return ToWeb.buildResult().status(Status.FAIL).msg("系统错误");
	}

	//其他未处理的异常
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object exceptionHandler(Exception e){
		e.printStackTrace();
		return ToWeb.buildResult().status(Status.FAIL).msg("系统错误");
	}
}
