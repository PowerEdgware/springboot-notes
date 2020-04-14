package com.study.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.study.service.BusinessService;
import com.study.service.WhiteUserService;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
public class StaticProxyController {

	BusinessService businessSrv;
	WhiteUserService userService;
	
	HttpServletResponse response;
	HttpServletRequest request;
	
	
	@GetMapping("/loadtime")
	public String doTestLoadTimeWeaver(HttpServletRequest request,String param) {
		//RequestContextHolder
//		AutowireUtils
//		WebApplicationContextUtils
		return businessSrv.doSomething(param);
	}
	
	@GetMapping("/selfInovke")
	public String doTestSelfInvoke(HttpServletRequest request,String param) {
		return businessSrv.doSomething(param);
	}
}
