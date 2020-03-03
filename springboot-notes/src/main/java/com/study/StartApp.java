package com.study;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.boot.config.CustomPropertiesFileApplicationListener;
import com.study.pojo.DemoReq;

@SpringBootApplication
//@RestController
//@ImportResource
//@PropertySource
@Controller
public class StartApp {
//TransactionAutoConfiguration
	public static void main(String[] args) {
		// SpringApplication.run(StartApp.class, args); ==>
		AnnotationConfigApplicationContext parent = new AnnotationConfigApplicationContext();
		String parentName = "bootapp";
		parent.setId(parentName);
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(BeanOntop.class);
		beanDefinitionBuilder.addPropertyValue("parentName", parentName);

		parent.registerBeanDefinition("demoBean", beanDefinitionBuilder.getBeanDefinition());
		// 刷新
		parent.refresh();

		SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder(StartApp.class);
		applicationBuilder.headless(true).parent(parent).bannerMode(Mode.CONSOLE).web(WebApplicationType.SERVLET);
		ConfigurableApplicationContext context=	applicationBuilder.run(args);
		
		try {
			//RequestContextHolder  
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		context.close();
		////		 DispatcherServlet
		//ExceptionHandlerExceptionResolver
		//Driver
		//Class.forName("com.mysql.jdbc.Driver");

	}

	@Autowired
	private BeanOntop top;

	@GetMapping("parentInfo")
	public Object getParentBeans() {
		String beans = Arrays.toString(top.getBeanNames());
		String name = top.getParentName();
		return beans + "\t" + name;
	}

//	@Value
//	private String myKey;
	
	@Autowired
	private ConfigurableEnvironment environment;

	@GetMapping("/get/{key}")
	public String getCustomeValue(@PathVariable String key) {
		String val = environment.getProperty(key);
		System.err.println("key=" + key + " val=" + val);
		return val;
	}

	@GetMapping("/get/all")
	public Object getCustomProperySource() {
		return environment.getPropertySources().get(CustomPropertiesFileApplicationListener.PROPERTY_SOURCE_NAME);
	}
	
	@GetMapping("/testBean")//ServletModelAttributeMethodProcessor  //PropertyAccessorFactory
	public String testBean(DemoReq req) {
		System.out.println(req);
		if(req.getReqName()==null) {
			throw new RuntimeException("请求参数不合法");
		}
		return "我是小灰马";
	}
	//StringHttpMessageConverter
	@ExceptionHandler
	@ResponseBody
	public String handleException(Exception e,HttpServletResponse response) {
//		response.setStatus(413);
//		response.setContentType("text/html;charset=ISO-8859-1");
//		response.setCharacterEncoding("ISO-8859-1");
		return "系统错误："+e.getMessage()+" Language";
	}
	
	@Bean
	public String demoStr() {
		return "OK";
	}
}

class BeanOntop implements ApplicationContextAware {
	private String parentName;
	ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
		this.parentName = this.context.getApplicationName();
	}

	public String[] getBeanNames() {
		return context.getBeanDefinitionNames();
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
