package com.study.beanwrapper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanInfoFactory;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.ExtendedBeanInfoFactory;
import org.springframework.core.SpringProperties;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.StringUtils;


public class InnerBeanInfo {
	/** Stores the BeanInfoFactory instances. */
	private static List<BeanInfoFactory> beanInfoFactories = SpringFactoriesLoader.loadFactories(
			BeanInfoFactory.class, CachedIntrospectionResults.class.getClassLoader());
	public static final String IGNORE_BEANINFO_PROPERTY_NAME = "spring.beaninfo.ignore";


	private static final boolean shouldIntrospectorIgnoreBeaninfoClasses =
			SpringProperties.getFlag(IGNORE_BEANINFO_PROPERTY_NAME);
	
	public static void main(String[] args) {
		try {
			BeanInfo beanInfo=getBeanInfo(Company.class);
			System.out.println(beanInfo);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		
		List<String> list=Arrays.asList("a","b");
		String result=StringUtils.collectionToDelimitedString(list, "][");
		System.out.println(result);
	}
	private static BeanInfo getBeanInfo(Class<?> beanClass) throws IntrospectionException {
		for (BeanInfoFactory beanInfoFactory : beanInfoFactories) {
			BeanInfo beanInfo = beanInfoFactory.getBeanInfo(beanClass);
			if (beanInfo != null) {
				return beanInfo;
			}
		}
		return (shouldIntrospectorIgnoreBeaninfoClasses ?
				Introspector.getBeanInfo(beanClass, Introspector.IGNORE_ALL_BEANINFO) :
				Introspector.getBeanInfo(beanClass));
	}
	
	
}
