package com.study;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringReadme {

	void readme() {
		// BeanDefinitionRegistry
		// BeanDefinitionReader  ClassPathBeanDefinitionScanner|XmlBeanDefinitionReader|AnnotatedBeanDefinitionReader
		// BeanDefinition
		//EnableAutoConfiguration
		//ConfigurationClassPostProcessor
		//org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
		//org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
		//org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
		
		
		// ClassLoader
		// Thread
		// Class

	}

	public static void main(String[] args) throws IOException {
		System.out.println(Thread.currentThread().getContextClassLoader());
		System.out.println(Thread.currentThread().getContextClassLoader().getParent());
		System.out.println(ClassLoader.getSystemClassLoader());
		
		/*
		 * sun.misc.Launcher$AppClassLoader@73d16e93
			sun.misc.Launcher$ExtClassLoader@15db9742
			sun.misc.Launcher$AppClassLoader@73d16e93
		 */
		 String cls = System.getProperty("java.system.class.loader");
		System.out.println(cls);

		String name = "java/sql/Array.class";

		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(name);

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();

			System.out.println(url.toString());
		}
		
		SpringApplication.run(SpringReadme.class, args);
	}
}
