package com.qa.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

/**
 * Provides impmentation for retry logic for failed test cases
 * @author ARASKAR
 *
 */
public class RAnalyzerRunTime implements IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(com.qa.utils.RetryLogic.class);
		
	}

}
