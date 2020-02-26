package com.study.pojo;

public class DemoReq {


	String reqName;
	String reqId;
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
