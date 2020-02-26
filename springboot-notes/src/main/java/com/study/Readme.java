package com.study;

import org.springframework.transaction.annotation.Transactional;

public class Readme {

	@Transactional
	void readme() {
		//配置额外的properties文件名字
		//方法一： 命令行参数设置--spring.config.name=application,{filename}
		//{filename} 文件名称，不包含后缀
		//方法二：自定义ApplicationListener PropertySourceLoader自己加载特定的配置。
	}
}
