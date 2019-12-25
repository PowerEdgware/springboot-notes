package com.study;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.BeansException;
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
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.study.boot.config.CustomPropertiesFileApplicationListener;

@SpringBootApplication
@RestController
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
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		context.close();

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
