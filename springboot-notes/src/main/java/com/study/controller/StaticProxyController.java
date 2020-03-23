package com.study.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.service.BusinessService;


@RestController
public class StaticProxyController {

	@Autowired
	BusinessService businessSrv;
	
	@GetMapping("/loadtimeTest")
	public String doTestLoadTimeWeaver(HttpServletRequest request,String param) {
		return businessSrv.doSomething(param);
	}
}
