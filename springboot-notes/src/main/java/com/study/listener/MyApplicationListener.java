package com.study.listener;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ClassUtils;

public class MyApplicationListener implements ApplicationListener<ApplicationPreparedEvent> {

	public static final String ASPECTJ_WEAVING_ENABLER_BEAN_NAME = "org.springframework.context.config.internalAspectJWeavingEnabler";

	static final String ASPECTJ_WEAVING_ENABLER_CLASS_NAME = "org.springframework.context.weaving.AspectJWeavingEnabler";

	static final String DEFAULT_LOAD_TIME_WEAVER_CLASS_NAME = "org.springframework.context.weaving.DefaultContextLoadTimeWeaver";

	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event) {
		ConfigurableApplicationContext context = event.getApplicationContext();
		SpringApplication application = event.getSpringApplication();

		registerLoadTimeWeaverBeanDefinetion(context, application);

		// 注册weaver processor
//		RootBeanDefinition def = new RootBeanDefinition(ASPECTJ_WEAVING_ENABLER_CLASS_NAME);
//		holder=new BeanDefinitionHolder(def, ASPECTJ_WEAVING_ENABLER_BEAN_NAME);
//		BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
		Set<Object> sources = application.getAllSources();
		// TODO
		Set<Class<?>> clazzSet = transform(sources, context.getClassLoader());
		context.addBeanFactoryPostProcessor(new MyBeanFactoryRegistryPostProcessor(clazzSet));
	}

	private void registerLoadTimeWeaverBeanDefinetion(ConfigurableApplicationContext context,
			SpringApplication application) {
		// 注册load-time weaver
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
		builder.getRawBeanDefinition().setBeanClassName(DEFAULT_LOAD_TIME_WEAVER_CLASS_NAME);
		builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		builder.getRawBeanDefinition().setSource(application);

		BeanDefinitionHolder holder = new BeanDefinitionHolder(builder.getBeanDefinition(),
				ConfigurableApplicationContext.LOAD_TIME_WEAVER_BEAN_NAME);

		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context.getBeanFactory();
		BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);

	}

	private Set<Class<?>> transform(Set<Object> sources, ClassLoader classLoader) {
		return sources.stream().filter(x -> {
			return x instanceof String || x instanceof Class;
		}).map(x -> {
			if (x instanceof String) {
				try {
					return ClassUtils.forName(x.toString(), classLoader);
				} catch (Throwable e) {
					throw new RuntimeException(e);
				}
			}
			return (Class<?>) x;
		}).collect(Collectors.toSet());
	}

}
