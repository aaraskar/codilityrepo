package com.qa.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * 
 * @author AJIT RASKAR
 *
 */
public class RetryLogic implements IRetryAnalyzer {
	int counter = 0;
	int retryLimit = 1;

	/**
	 * This method executes failed test case one more time
	 */
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if (counter < retryLimit) {
			counter++;
			return true;
		}
		return false;
	}

}
