package com.study.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//@Component
public class AuthFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getParameter("auth")==null) {
			throw new RuntimeException(new IllegalAccessException("\"No Auth\""));
		}
		filterChain.doFilter(request, response);
	}
	
	@Configuration
	static class AuthFilterConfiguration{
		
//		final AuthFilter authFilter;
//		public AuthFilterConfiguration(AuthFilter authFilter) {
//			super();
//			this.authFilter = authFilter;
//		}
		@Bean
		public FilterRegistrationBean<Filter> authFilter() {
			FilterRegistrationBean<Filter> filterRegistrationBean=new FilterRegistrationBean<>();
			filterRegistrationBean.setFilter(new AuthFilter());
			filterRegistrationBean.addUrlPatterns("/*");
			return filterRegistrationBean;
		}
	}
}


