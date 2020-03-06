package com.study.messagesource;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageFormatDemo {

	void readme() {
		// 本地化信息/本地化对象
		// 组成：语言类型和国家/地区类型
		Locale locale = Locale.getDefault();
		locale = new Locale("zh", "CN"); // <==>Locale.CHINA
		locale = new Locale("zh"); // <==> Locale.CHINESE

		// 支持本地化的格式化工具：
		// NumberFormat DateFormat MessageFormat
		// parent Format

	}

	public static void main(String[] args) {
		String pattern = "{0},你好!你于{1}在农业银行存入{2}元。";
		Object[] params = { "John", new GregorianCalendar().getTime(), 1.0E3 };// 1.0E3

		// 使用默认的本地化对象格式化信息
		String result = MessageFormat.format(pattern, params);
		System.out.println(result);

		// 使用特定的本地化对象格式化消息
		pattern = "At {1,time,short } on {1,date,long},{0} paid {2,number,currency}.";
		MessageFormat messageFormat = new MessageFormat(pattern, Locale.US);
		result=messageFormat.format(params);
		
		System.out.println(result);
		
		//spring 定义的访问国际化消息的接口：MessageSource 子接口：HierarchicalMessageSource，ApplicationContext
		//典型实现：1.ReloadableResourceBundleMessageSource
		//2.ResourceBundleMessageSource
		
//		ResourceBundle
	}
}
