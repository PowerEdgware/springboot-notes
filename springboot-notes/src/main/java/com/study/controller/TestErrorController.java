package com.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestErrorController {

	@GetMapping("noneview")
	public ModelAndView noneExistView(int xx) {
		return new ModelAndView("none_page");
	}
}
