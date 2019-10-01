/**
 * 
 */
package com.apple.listeners;

import java.util.List;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * @author rashi_chopra
 *
 */
public class TestListener implements IInvokedMethodListener{

	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
	}

	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
		Reporter.setCurrentTestResult(testResult);
		 
		if (method.isTestMethod()) {
 
			List<String> verificationFailures = SoftAssertor.getVerificationFailures();
		
			int size = verificationFailures.size(); 
			//if there are verification failures...
			if ( size > 0) {
 				//set the test to failed
				testResult.setStatus(ITestResult.FAILURE);
				
				testResult.setAttribute("ErrorMsg ", verificationFailures.toString());
				Reporter.log(verificationFailures.toString());
 
				//if there is an assertion failure add it to verificationFailures
				if (testResult.getThrowable() != null) {
					verificationFailures.add(testResult.getThrowable().getMessage());
				}
				
				
				StringBuffer failureMsg = new StringBuffer();
				for(int i=0;i<size;i++) {
					failureMsg.append(verificationFailures.get(i)).append("\n");
				}
				
				//set merged throwable
				Throwable merged = new Throwable(failureMsg.toString());
				testResult.setThrowable(merged);
				}
			}
		
		
		}

}
