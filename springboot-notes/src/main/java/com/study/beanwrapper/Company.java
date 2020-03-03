package com.study.beanwrapper;

import java.util.Map;

public class Company {
	private String name;
	private Employee managingDirector;
	
	private Map<String, String> attr;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Employee getManagingDirector() {
		return this.managingDirector;
	}

	public void setManagingDirector(Employee managingDirector) {
		this.managingDirector = managingDirector;
	}

	public Map<String, String> getAttr() {
		return attr;
	}

	public void setAttr(Map<String, String> attr) {
		this.attr = attr;
	}

	@Override
	public String toString() {
		return "Company [name=" + name + ", managingDirector=" + managingDirector + ", attr=" + attr + "]";
	}
	
}
