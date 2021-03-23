package com.oracle.utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

public class RetryTestNg implements IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, @SuppressWarnings("rawtypes") Class arg1, @SuppressWarnings("rawtypes") Constructor arg2, Method arg3) {

		IRetryAnalyzer RetryTest = annotation.getRetryAnalyzer();
		if(RetryTest==null)
			annotation.setRetryAnalyzer(Re_executeFailedTest.class);
	}

}
