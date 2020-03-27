package com.study.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class GlobalErrorController /**extends BasicErrorController*/ implements ErrorController {

	 static final String errotPath="/error";//必须是/error
	@Override
	public String getErrorPath() {
		return errotPath;
	}
	

//	public GlobalErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
//		super(errorAttributes, errorProperties);
//	}

//DispatcherServlet  
	// BasicErrorController ErrorViewResolver
	// ErrorMvcAutoConfiguration
	
	//StandardHostValve DirectJDKLog TemplateEngine
	//RequestDispatcher.ERROR_EXCEPTION
	//DispatcherServlet.EXCEPTION_ATTRIBUTE
}
