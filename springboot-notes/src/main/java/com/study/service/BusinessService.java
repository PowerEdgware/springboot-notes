package com.study.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BusinessService {

	@Autowired
	BusinessService businessService;//引用自身代理
	
	public BusinessService() {}

	@Transactional
	public String doSomething(String params) {
		int range = rnd.nextInt(100);
		System.out.println("range=" + range);
//		if (range > 10) {
//			throw new PessimisticLockingFailureException("acquire lock failed");
//		}
		System.out.println("dosomething with params=" + params+ getClass());
		//走的是代理方法 而不是this
		businessService.doSomethingWithAnno(params);
		
		return "OK";
	}
	@Transactional
	public String doSomethingWithAnno(String params) {
		int range = rnd.nextInt(100);
		System.out.println("range=" + range);
//		if (range > 50) {
//			throw new PessimisticLockingFailureException("acquire lock failed");
//		}
		System.out.println("doSomethingWithAnno with params=" + params);
		return "OK";
	}
	Random rnd = new Random();
}
