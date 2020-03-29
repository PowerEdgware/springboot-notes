package com.study.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorRenderController {

//	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE,value = GlobalErrorController.errotPath)
//	public Map<String, String> errorPojo(HttpServletRequest request, HttpServletResponse response){
//		Map<String, String> map=new HashMap<>();
//		map.put("code", "404");
//		map.put("message", "NOT FOUND");
//		return map;
//	}
}
