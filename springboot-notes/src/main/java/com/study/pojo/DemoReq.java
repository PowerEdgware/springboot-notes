package com.study.pojo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

public class DemoReq {


	@NotEmpty
	@Range(min = 1,max = 10)
	String reqName;
	@Size(max = 100,min=2)
	String reqId;
	
	@Min(10)
	@Max(121)
	int age;
	@Override
	public String toString() {
		return "DemoReq [reqName=" + reqName + ", reqId=" + reqId + ", age=" + age + "]";
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getReqName() {
		return reqName;
	}
	public void setReqName(String reqName) {
		this.reqName = reqName;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	

}
