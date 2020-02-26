package com.study;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyEditorRegistrySupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

//http\://www.springframework.org/schema/c=org.springframework.beans.factory.xml.SimpleConstructorNamespaceHandler
//http\://www.springframework.org/schema/p=org.springframework.beans.factory.xml.SimplePropertyNamespaceHandler
//http\://www.springframework.org/schema/util=org.springframework.beans.factory.xml.UtilNamespaceHandler

@Qualifier
public class BeanPopulate {

	public static void main(String[] args) {
		// ApplicationContext.getBean
		ApplicationContext context = new ClassPathXmlApplicationContext();
		 context.getBean("cc");//AbstractAutowireCapableBeanFactory#doCreateBean
//		AutowiredAnnotationBeanPostProcessor
		 //DefaultListableBeanFactory
		 //XmlBeanFactory  ApplicationContext BeanFactory
//		 QualifierAnnotationAutowireCandidateResolver
//		 PropertyEditorSupport editorSupport
//		 PropertyEditorRegistrySupport
//		 CustomEditorConfigurer
		 //SingletonBeanRegistry
		 //BeanDefinitionRegistry
		 
//		 DispatcherServlet

		BeanPopulate bean = new BeanPopulate();
		// 1.Spring bean operate
		BeanWrapper wrapper = new BeanWrapperImpl(bean);
		wrapper.setAutoGrowNestedPaths(true);

		PropertyDescriptor[] pds = wrapper.getPropertyDescriptors();
		Arrays.asList(pds).forEach(pd -> {
			System.out.println(pd.getDisplayName()+"\t"+pd.getName());
		});
		// 设置值
		wrapper.setPropertyValue("name", "xxx");
		wrapper.setPropertyValue("age", "xe");
		wrapper.setPropertyValue("list", "1,a,5");
		wrapper.setPropertyValue("map['ax']", "a");
		wrapper.setPropertyValue("map['x']", "a");
//		wrapper.setPropertyValues(map);PropertyEditor TypeConverter MethodParameter
		System.out.println(bean);

		// 2.java内省 Introspector PropertyDescripter(属性描述)
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(BeanPopulate.class);
			beanInfo.getPropertyDescriptors();

			PropertyDescriptor pd = new PropertyDescriptor("name", BeanPopulate.class);
			// pd.createPropertyEditor(beanInfo);
			// pd.setValue(attributeName, value);
//			pd.getWriteMethod()
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}

	}
	private Map<String, String> map;

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	private List<String> list;

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	private String name;
	private String age;
	int sex;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		System.out.println(map.getClass());
		System.out.println(list.getClass());
		return "BeanPopulate [map=" + map + ", list=" + list + ", name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}



}
