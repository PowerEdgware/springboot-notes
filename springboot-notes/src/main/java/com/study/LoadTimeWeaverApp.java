package com.study;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.LoadTimeWeavingConfigurer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.instrument.classloading.tomcat.TomcatLoadTimeWeaver;

import com.study.annotation.EnableEnhancedLoadTimeWeaving;

import org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@EnableLoadTimeWeaving(aspectjWeaving = AspectJWeaving.ENABLED)// 此方式有bug，aspect无法织入目标对象
//@ImportResource(locations = "classpath:aop-aspectj-via-loadtimeweaver.xml")  这种方式也不行

//TODO 自定义实现
@EnableEnhancedLoadTimeWeaving(aspectjWeaving = AspectJWeaving.DISABLED)
@PropertySource(value = {"classpath:abc.yml"})
public class LoadTimeWeaverApp extends SpringBootServletInitializer /* implements LoadTimeWeavingConfigurer*/ {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(LoadTimeWeaverApp.class);
	}
	
	public static void main(String[] args) {
		//invoke StartApp
		SpringApplication.run(LoadTimeWeaverApp.class, args);
		//System.err.println(Arrays.toString(LoadTimeWeaverApp.class.getAnnotations()));
		
		//AJ
//		GenericBeanDefinition
		//AspectJWeavingEnabler
		//ContextNamespaceHandler
		
	}

//	@Override
//	public LoadTimeWeaver getLoadTimeWeaver() {
//		return new TomcatLoadTimeWeaver();
//	}
	
	@Value(value = "${rnd.val}")
	String value;
}
