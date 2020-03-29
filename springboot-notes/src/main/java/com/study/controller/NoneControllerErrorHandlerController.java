package com.study.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理非controller抛出的异常(Controller抛出的异常可以由 {@link @ControllerAdvice} 处理)
 * 
 * @author shangcj
 *
 */
@RestController
public class NoneControllerErrorHandlerController extends AbstractErrorController {

	private final ErrorProperties errorProperties;

	public NoneControllerErrorHandlerController(ErrorAttributes errorAttributes, ErrorProperties errorProperties,
			List<ErrorViewResolver> errorViewResolvers) {
		super(errorAttributes, errorViewResolvers);
		this.errorProperties = errorProperties;
//		this.errorProperties.setIncludeException(true);
	}

	static Logger log = LoggerFactory.getLogger(NoneControllerErrorHandlerController.class);

	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "${server.error.path:${error.path:/error}}")
	public ResponseEntity<Map<String, Object>> errJson(HttpServletRequest request) {
		Map<String, Object> errAttributes = getErrorAttributes(request,
				isIncludeStackTrace(request, MediaType.APPLICATION_JSON_UTF8));

		HttpStatus status = getStatus(request);

		return new ResponseEntity<Map<String, Object>>(errAttributes, status);
	}

	protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
		IncludeStacktrace include = this.errorProperties.getIncludeStacktrace();
		if (include == IncludeStacktrace.ALWAYS) {
			return true;
		}
		if (include == IncludeStacktrace.ON_TRACE_PARAM) {
			return getTraceParameter(request);
		}
		return false;
	}
	

	@Override
	public String getErrorPath() {
		return this.errorProperties.getPath();
	}

	@Configuration
	static class ExtraErrorHandlerConfiguration {
		private final ServerProperties serverProperties;
		public ExtraErrorHandlerConfiguration(ServerProperties serverProperties) {
			this.serverProperties = serverProperties;
		}

		@Bean
		public ErrorController errorPathController() {
			return () -> {
				return serverProperties.getError().getPath();
			};
		}

		@Bean
		public ErrorProperties errorProperties() {
			return serverProperties.getError();
		}

	}

}
