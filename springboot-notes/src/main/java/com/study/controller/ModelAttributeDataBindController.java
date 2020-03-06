package com.study.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.pojo.DemoReq;

@Controller
public class ModelAttributeDataBindController {

	@ModelAttribute//可以设置@ModelAttribute#bind属性为false，不进行请求参数绑定
	public DemoReq preBind(String reqId) {
		DemoReq demoReq=new DemoReq();
		demoReq.setAge(19);
		demoReq.setReqId(reqId+"_");
		return demoReq;
	}
	
	@GetMapping("/modelBind")
	@ResponseBody//请求参数绑定到req 会覆盖上面的preBind。可以设置@ModelAttribute#bind属性为false，不进行请求参数绑定
	public DemoReq modelBind(@Valid @ModelAttribute(binding = false) DemoReq req) {
		return req;
	}
}
