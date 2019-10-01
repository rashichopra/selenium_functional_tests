/**
 * 
 */
package com.apple.listeners;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.fest.swing.image.ImageException;
import org.fest.swing.image.ScreenshotTaker;
import org.fest.swing.testng.listener.AbstractTestListener;
import org.fest.util.Arrays;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.ITestAnnotation;
import org.testng.xml.XmlSuite;

import com.apple.Common.CommonActions;
import com.apple.Common.CommonTest;
import com.apple.util.PropertyFileReader;

/**
 * @author rashi_chopra
 *
 */
public class TakeScreenShotOnError extends AbstractTestListener {
	
	
	 static PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	 
	
	 private CommonActions screenshotTaker;
	  private String output;
	  private boolean ready;
	
			
	  /**
	   * Creates a new <code>{@link ScreenshotOnFailureListener}</code>.
	   */
	  public TakeScreenShotOnError() {
	    
	  }

	  /**
	   * Gets the output directory from the given context after the test class is instantiated and before any configuration
	   * method is called.
	   * @param context the given method context.
	   */
	  @Override public void onStart(ITestContext context) {
	    output = context.getOutputDirectory();
	   // ready = !isEmpty(output) && screenshotTaker != null;
	  }

	  /**
	   * When a test fails, this method takes a screenshot of the desktop and adds an hyperlink to the screenshot it in the
	   * HTML test report.
	   * @param result contains information about the failing test.
	   */
	  @Override public void onTestFailure(ITestResult result) {
	   
	    String screenshotFileName = takeScreenshotAndReturnFileName(result);
	   
	    Reporter.setCurrentTestResult(result);
	    Reporter.setEscapeHtml(false);
	    Reporter.log("<a href=\'"+ screenshotFileName+ "\'>Screenshot</a>");
	  }
	  
	/**
	 * This method is provided by TestNG's listeners and is triggered after a method gets executed
	 * It intercepts the test method. if it fails, captures the screen shot and stores the screen shot as 
	 * /test-output/screenshots/<Failed Test Method Name>.png
	 */
	public String takeScreenshotAndReturnFileName(ITestResult testResult) {
		
		/*Reporter.setCurrentTestResult(testResult);
		String workingDir = System.getProperty("user.dir");
		if (testResult.isSuccess() == false) {
			System.out.println("Test Result for "
					+ method.getTestMethod().getMethodName()
					+ " is not passed ");*/
		String workingDir = System.getProperty("user.dir");
		ITestContext method = testResult.getTestContext();

				
				String screenShotFile = workingDir + File.separator
						+ CommonTest.TEST_RESULTS_OUTPUT_FOLDER
						+ File.separator + CommonTest.SCREENSHOTS_FOLDER_NAME
						+ File.separator+prop.getValue("PROJECT_NAME")+File.separator
						+"Fail_"+ method.getName()
						+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()))
						+ CommonTest.SCREENSHOT_IMG_FORMAT;
				//Reporter.log("There is a failure & Screen Shot file name is "+screenShotFile);
				// Now you can do whatever you need to do with it, for example copy somewhere
				
				try {
					FileUtils.copyFile(CommonActions.ScreenShotTaker(), new File(screenShotFile));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				return screenShotFile;
			
	}


	public static String concat(Object... objects) {
	    if (Arrays.isEmpty(objects)) return null;
	    StringBuilder b = new StringBuilder();
	    for (Object o : objects) b.append(o);
	    return b.toString();
	  }

}
