package com.study.listener;

import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving;
import org.springframework.context.weaving.AspectJWeavingEnabler;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.lang.Nullable;

import com.study.annotation.EnableEnhancedLoadTimeWeaving;

//必须早于 ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry 的加载
public class MyBeanFactoryRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {

	@Nullable
	private AnnotationAttributes enableLTW;
	final Set<Class<?>> weaverAnnotationClazzSet;

	public MyBeanFactoryRegistryPostProcessor(Set<Class<?>> clazzSet) {
		this.weaverAnnotationClazzSet = clazzSet;
		this.enableLTW = findLoadTimeWeaverAnno();
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		if (enableLTW == null) {
			return;
		}
		if (registry instanceof DefaultListableBeanFactory) {
			DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) registry;
			boolean switchOn = isWeaverEnabled(beanFactory.getBeanClassLoader());

			if (beanFactory.containsBeanDefinition(ConfigurableApplicationContext.LOAD_TIME_WEAVER_BEAN_NAME)) {
				if (switchOn) {
					// 先于业务类被加载时，添加load-time-weaver，实现在业务bean加载时做字节码增强
					LoadTimeWeaver loadTimeWeaver = (LoadTimeWeaver) beanFactory
							.getBean(ConfigurableApplicationContext.LOAD_TIME_WEAVER_BEAN_NAME);
					AspectJWeavingEnabler.enableAspectJWeaving(loadTimeWeaver, beanFactory.getBeanClassLoader());
				}
			}
		}
	}

	private AnnotationAttributes findLoadTimeWeaverAnno() {
		for (Class<?> clazz : weaverAnnotationClazzSet) {
			StandardAnnotationMetadata annotationMetadata = new StandardAnnotationMetadata(clazz);
			AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(
					annotationMetadata.getAnnotationAttributes(EnableEnhancedLoadTimeWeaving.class.getName(), false));
			if (annotationAttributes != null) {
				return annotationAttributes;
			}
		}
		return null;
	}

	// 和优先级高于 ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry 的加载
	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}

	boolean isWeaverEnabled(ClassLoader beanClassLoader) {
		boolean switchOn = false;
		AspectJWeaving aspectJWeaving = this.enableLTW.getEnum("aspectjWeaving");
		switch (aspectJWeaving) {
		case DISABLED:
			// AJ weaving is disabled -> do nothing
			break;
		case AUTODETECT:
			if (beanClassLoader.getResource(AspectJWeavingEnabler.ASPECTJ_AOP_XML_RESOURCE) == null) {
				// No aop.xml present on the classpath -> treat as 'disabled'
				break;
			}
			// aop.xml is present on the classpath -> enable
			switchOn = true;
			break;
		case ENABLED:
			switchOn = true;
			break;
		}
		return switchOn;
	}

}
