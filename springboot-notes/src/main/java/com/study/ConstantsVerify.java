package com.study;

public class ConstantsVerify {

	public static void main(String[] args) {
		//以下输出是什么，去掉字段f的final呢?
		System.out.println(S.f);
	}
}

class S extends F{
	static final String f="final";
	static {
		System.out.println("S");
	}
}
class F{
	static {
		System.out.println("F");
	}
}
