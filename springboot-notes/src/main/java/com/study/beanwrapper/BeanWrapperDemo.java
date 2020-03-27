package com.study.beanwrapper;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValue;

//javaBean 
/**
 * 设置和获取属性值以及嵌套属性
 * 
 * @author
 *
 */
public class BeanWrapperDemo {

//		 PropertyAccessorUtils
	// PropertyAccessorFactory
	public static void main(String[] args) {
		Company company = new Company();
		BeanWrapper companyBeanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(company);
		// setting the company name..
		companyBeanWrapper.setPropertyValue("name", "Some Company Inc.");
		// ... can also be done like this:
		PropertyValue value = new PropertyValue("name", "Some Company Inc.");
		companyBeanWrapper.setPropertyValue(value);
		
		//set nested attr
		companyBeanWrapper.setAutoGrowNestedPaths(true);
		companyBeanWrapper.setPropertyValue("attr['a']", "a");
		companyBeanWrapper.setPropertyValue("attr['b']", "b");
		
		try {
			companyBeanWrapper.setAutoGrowNestedPaths(true);//允许内嵌属性不存在则创建
			String propertyName="managingDirector.salary";//获取内嵌对象的属性描述
			PropertyDescriptor pd=companyBeanWrapper.getPropertyDescriptor(propertyName);
		
			System.out.println(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//// ok, let's create the director and tie it to the company:
		BeanWrapper directorBeanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(new Employee());
		directorBeanWrapper.setPropertyValue("userName", "Jim Stravinsky");
		directorBeanWrapper.setPropertyValue("salary", "889.54");
		//绑定
		companyBeanWrapper.setPropertyValue("managingDirector", directorBeanWrapper.getWrappedInstance());

		// retrieving the salary of the managingDirector through the company
		Float salary = (Float) companyBeanWrapper.getPropertyValue("managingDirector.salary");
		System.out.println(salary);
		
		System.out.println(companyBeanWrapper.getWrappedInstance());
		
		
		//注册自定义属性编辑器，如：CustomDateEditor
		
		//把自定义属性编辑器注册到Spring容器的方式
		//1.CustomEditorConfigurer
		//2.PropertyEditorRegistrar and PropertyEditorRegistry
		//Instrumentation
	}
}
