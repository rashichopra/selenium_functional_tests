/**
 * 
 */
package com.apple.Common;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.Augmenter;
import org.testng.Assert;
import org.testng.Reporter;

import com.apple.util.Decryption;
import com.apple.util.LogUtil;
import com.apple.util.PropertyFileReader;
import com.apple.util.ScreenshotUtil;
import com.googlecode.fightinglayoutbugs.FightingLayoutBugs;
import com.googlecode.fightinglayoutbugs.LayoutBug;
import com.googlecode.fightinglayoutbugs.WebPage;

/**
 * This class represents a general web page in the WebDriver world.
 * It also provides some useful methods to use on the page.
 * @author rashi_chopra
 *
 */
public class CommonActions {

	
    protected static WebDriver driver = null;
    
    static PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
    
	//value for the wait loop
	private int WAIT_TO_CHECK = 500;
	
	/**
	 * Assumes we are only running one instance of a driver at one time
	 * @param driver
	 */
	public CommonActions(WebDriver driver) {
		if (CommonActions.driver == null) {
			CommonActions.driver = driver;
		}
	}
	
	public CommonActions(){
		
	}
	

	public void setPageDriver( WebDriver driver){
		CommonActions.driver = driver;
	}
	
	/*
	 * Method for log4j implementation, here the input parameter is the class name
	 * It reads the log4j.xml and prints logging information accordingly.
	 */
	public static Log getLog (Class<?> string) {
		return LogUtil.getLog(string, "log4j.xml");
	}
	
	public void loadPage(WebDriver driver, String url) {
		
		driver.get(url);
        driver.manage().window().maximize();     
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	public static void Wait_pageLoad(WebDriver driver,String title){
		int i=0;
		while(i<10){
			
			if(driver.getTitle().equals(title)){
			break;
			}
			sleep(1000);
			i++;
		}
		
		//System.out.println(driver.getTitle());
		Assert.assertEquals(driver.getTitle(),title);
	}
	
	
	public WebElement waitOnElement(By element, int maxSeconds, String frame) {
		WebElement identifier = null;

		for (int i = 0; i < maxSeconds; i++) {
			try {
				if (frame == null)
					identifier = driver.findElement(element);
				else {
					driver.switchTo().defaultContent();
					identifier = driver.switchTo().frame(frame).findElement(element);
				}
				
				//if element is found return
				if (identifier != null) 
					break;
				
				Thread.sleep(WAIT_TO_CHECK);
			} catch (Exception e) {			
				identifier = null;
			}		
		}
		return identifier;
	}
	
	/**
	 * Waits until webdriver can find an element with the specified name attribute.
	 * 
	 * @param name the 'name' attribute value
	 * @param maxMilliseconds how long we should wait
	 * @param frame the frame the element is located on
	 * @return true if WebDriver is able to find a web element with the given name attribute value
	 */
	public WebElement waitOnName(String name, int maxSeconds, String frame) {
		return waitOnElement(By.name(name), maxSeconds, frame);
	}


	/**
	 * Waits until webdriver can find an element with the specified name attribute.
	 * @param name
	 * @param frame
	 * @return
	 */
	public WebElement waitOnName(String name, String frame ) {
		return waitOnName(name, CommonTest.MaxWaitSeconds, frame);
	}

	/**
	 * Waits until webdriver can find an element with the specified name attribute.
	 * @param name
	 * @return
	 */
	public WebElement waitOnName(String name) {
		return waitOnName(name, CommonTest.MaxWaitSeconds, null);
	}
	
	public WebElement waitOnName(String name, int maxTime) {
		return waitOnName(name, maxTime, null);
	}

	/**
	 * Waits until webdriver can find the specified header text. The header
	 * title is the common ui header text that spans all pages (part of the new
	 * qbo ui refresh)
	 * 
	 * @param id
	 * @param maxMilliseconds
	 * @param frame
	 * @return true if WebDriver is able to find an element with the given id
	 */
	public WebElement waitOnId(String id, int maxSeconds, String frame) {
		return waitOnElement(By.id(id), maxSeconds, frame);
	}

	public WebElement waitOnId(String id, String frame ) {
		return waitOnId(id,  CommonTest.MaxWaitSeconds, frame);
	}

	public WebElement waitOnId(String id) {
		return waitOnId(id,  CommonTest.MaxWaitSeconds, null);
	}

	public WebElement waitOnId(String id, int maxTime) {
		return waitOnId(id, maxTime, null);
	}

	
	/**
	 * Waits until webdriver can find the element specified by xpath.
	 *
	 * @param path
	 * @param maxMilliseconds
	 * @param frame
	 * @return true if WebDriver is able to find an element with the given id
	 */
	public WebElement waitOnXPath(String path, int maxSeconds, String frame) {
		return waitOnElement(By.xpath(path), maxSeconds, frame);
	}
	public WebElement waitOnXPath(String path, String frame ) {
		return waitOnXPath(path, CommonTest.MaxWaitSeconds, frame);
	}
	public WebElement waitOnXPath(String path) {
		return waitOnXPath(path, CommonTest.MaxWaitSeconds, null);
	}
	public WebElement waitOnXPath(String path, int maxTime) {
		return waitOnXPath(path, maxTime, null);
	}
	
	/**
	 * Waits until webdriver can find the element specified by xpath.
	 *
	 * @param path
	 * @param maxMilliseconds
	 * @param frame
	 * @return true if WebDriver is able to find an element with the given id
	 */
	public WebElement waitOnCSS(String css, int maxSeconds, String frame) {
		return waitOnElement(By.cssSelector(css), maxSeconds, frame);
	}
	public WebElement waitOnCSS(String css, String frame ) {
		return waitOnCSS(css, CommonTest.MaxWaitSeconds, frame);
	}
	public WebElement waitOnCSS(String css) {
		return waitOnCSS(css, CommonTest.MaxWaitSeconds, null);
	}
	public WebElement waitOnCSS(String css, int maxTime) {
		return waitOnCSS(css, maxTime, null);
	}

	/**
	 * Waits until webdriver can find the element specified by linkText.
	 *
	 * @param path
	 * @param maxMilliseconds
	 * @param frame
	 * @return true if WebDriver is able to find an element with the given linkText
	 */
	public WebElement waitOnText(String linkText, int maxSeconds, String frame) {
		return waitOnElement(By.linkText(linkText), maxSeconds, frame);
	}
	public WebElement waitOnText(String linkText, String frame ) {
		return waitOnText(linkText, CommonTest.MaxWaitSeconds, frame);
	}
	public WebElement waitOnText(String linkText) {
		return waitOnText(linkText, CommonTest.MaxWaitSeconds, null);
	}
	public WebElement waitOnText(String linkText, int maxTime) {
		return waitOnText(linkText, maxTime, null);
	}

	/*****
	 * Methods for: Check Presence of an Element on the Page by XPath or Id, Verify its visibility and click on the element
	 * @param XPath, Id
	 * @param Message
	 */
	public WebElement getElement_XPath(String XPath, String Message){
		WebElement elem = waitOnXPath(XPath); 
		//WebElement elem = driver.findElement(By.xpath(XPath)); 
		Assert.assertNotNull(elem,Message);
		return elem;
		
	}
	
	public boolean isElementPresent_XPath(String XPath){
		boolean elem_present=true;
		try{
		WebElement elem = driver.findElement(By.xpath(XPath)); 
		elem.isDisplayed();
		}catch(NoSuchElementException ex){
			elem_present=false;
		}
		return elem_present;
		
	}
	
	public WebElement getElement_Id(String Id, String Message){
		WebElement elem = waitOnId(Id);
		Assert.assertNotNull(elem, Message);
		return elem;
	}
	
	public boolean isElementPresent_Id(String Id){
		boolean elem_present=true;
		try{
		WebElement elem = driver.findElement(By.id(Id));
		Assert.assertTrue(elem.isDisplayed());
		}catch(NoSuchElementException ex){
			elem_present=false;
			//System.out.println(elem_present);
		}
		
		return elem_present;
	}
	
	public WebElement getElement_CSS(String XPath, String Message){
		WebElement elem = waitOnCSS(XPath); 
		//WebElement elem = driver.findElement(By.xpath(XPath)); 
		Assert.assertNotNull(elem,Message);
		return elem;
		
	}
	
	public boolean isElementPresent_CSS(String CSS, String Message){
		boolean elem_present=true;
		try{
		WebElement elem = waitOnCSS(CSS); 
		Assert.assertTrue(elem.isDisplayed());
		}catch(NoSuchElementException ex){
			elem_present=false;
			//System.out.println(elem_present);
		}
		
		return elem_present;
	}
	
	public void isElementDisplayed(WebElement Elem, String Message){
		sleep(3000);
		Assert.assertTrue(Elem.isDisplayed(),Message);
	}
	
	public void clickElement(WebElement Elem, String Text){
		
		Assert.assertEquals(Elem.getText(), Text);
		Elem.click();
		
	}
	
	public void clickElement(WebElement Elem){
		Elem.click();
	}
	
	
	public String getPageURL(){
		return driver.getCurrentUrl();
	}
	
	public void verifyImage(WebElement Elem, String Alt, String Src ){
		Assert.assertEquals(Elem.getAttribute("alt"), Alt);
		Assert.assertTrue(Elem.getAttribute("src").contains(Src),"Actual Src: "+Elem.getAttribute("src")+"does not contain: "+Src);
		Reporter.log("Image Source:" +Elem.getAttribute("src"));
		Reporter.log("Image Alt:" +Elem.getAttribute("alt"));
	}
	
	public void verifyText(WebElement Elem, String Text){
		
		Assert.assertEquals(Elem.getText(), Text);
	}
	
	public void verifyTextColor(WebElement Elem,String color){
		
		Assert.assertEquals(Elem.getCssValue("color"),color);
	}
	
	
	/**
	 * 
	 * @param list the element for selection list
	 * @param selection the text for the element to be selected from the selection list
	 * @return true if the selection is found
	 */
	public boolean selectDropDownList(WebElement list, String selection) {
		List<WebElement> componentList = list.findElements(By.tagName("option"));
		for (WebElement component : componentList) {
			if (component.getText().contains(selection)) {
				component.click();
				return true;
			}
		}
		return false;
	}
	

	/* Function to return number of columns/rows in a table depending on the XPath *
	 * 
	 */
	
	public int getCount(String xPath){
		int count = 0;
		count = driver.findElements(By.xpath(xPath)).size();		
		return count;
	}
	
	
	/*****
	 * sleep: Replace the try...catch block and Thread.sleep with this method for a cleaner code
	 * @param timeout
	 */
	public static void sleep(long timeout){
		
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method returns a list of src attribute values for a given xpath (Can
	 * be used for Javascript src elements)
	 * 
	 * @param driver
	 * @param imageXpath
	 * @return
	 */
	public List<String> getJSSourceAttributes(WebDriver driver, String elemXpath) {
		List<String> javaScriptSrcList = new ArrayList<String>();
		List<WebElement> elems = new ArrayList<WebElement>();
		if (isElementPresent(driver, elemXpath) == true) {
			elems = getElementsWithSameXpath(elemXpath);
			for (WebElement jsElement : elems) {
				javaScriptSrcList.add(jsElement.getAttribute("src"));
			}
		}

		return javaScriptSrcList;
	}
	
	/**
	 * Checks whether an element is present on the page. It uses an elements
	 * xpath expression to find it on the page
	 * 
	 * @param driver
	 *            webdriver instance used in the test
	 * @param xPathExpression
	 *            Xpath expression for a given element on the page
	 * @return true, if element is present; false if not found
	 */
	public boolean isElementPresent(WebDriver driver, String xPathExpression) {
		boolean isElementPresent = false;
		WebElement targetElement = null;

		try {
			targetElement = waitOnXPath(xPathExpression);
			if (targetElement != null)
				isElementPresent = true;
			else
				isElementPresent = false;
		} catch (org.openqa.selenium.ElementNotVisibleException ex) {
			isElementPresent = false;
			System.out.println("ElementNotVisibleException  for element with xpath "
					+ xPathExpression + " Error is " + ex.getMessage());
		} catch (org.openqa.selenium.NoSuchElementException ex) {
			System.out.println("NoSuchElementException  for element with xpath "
					+ xPathExpression + " Error is " + ex.getMessage());
			isElementPresent = false;
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			System.out.println("Stale Element Reference for element with xpath "
					+ xPathExpression + " Error is " + ex.getMessage());
			isElementPresent = false;
		}

		return isElementPresent;
	}
	
	/**
	 * Returns the text values for all elements of the page sharing the same
	 * xpath
	 * 
	 * @param driver
	 * @param subNavLinkXPath
	 * @return
	 */
	public List<String> getTextFromElementWithSameXpath(WebDriver driver,
			String subNavLinkXPath) {
		// WebElement linkTexts = waitOnXPath(subNavLinkXPath);
		sleep(1000);
		List<WebElement> subNavLinks = driver.findElements(By
				.xpath(subNavLinkXPath));
		List<String> subNavLinksText = new ArrayList<String>();
		String elementTxt = null;
		for (WebElement subNavLink : subNavLinks) {
			// Replace any new line characters with spaces
			elementTxt = subNavLink.getText();
			if (elementTxt != null && !elementTxt.isEmpty()) {
				elementTxt = elementTxt.replace("\n", " ");
				subNavLinksText.add(elementTxt);
			}
		}
		return subNavLinksText;
	}

	/**
	 * Gets all the web element references sharing a common xpath and returns a
	 * list of webelement objects
	 * 
	 * @param driver
	 * @param subNavLinkXPath
	 * @return
	 */
	public List<WebElement> getElementsWithSameXpath(String subNavLinkXPath) {
		sleep(1000);
		return driver.findElements(By.xpath(subNavLinkXPath));
	}
	
	
	public void verifyLayout(WebDriver driver) throws IOException{
		
	//try {
		WebPage webPage = new WebPage(driver);
		FightingLayoutBugs flb = new FightingLayoutBugs();
		final Collection<LayoutBug> layoutBugs =  	flb.findLayoutBugsIn(webPage);
		System.out.println("Found " + layoutBugs.size() + " layout bug(s).");
		for (LayoutBug bug : layoutBugs) {
		System.out.println(bug);
		}
		/*} catch (Exception e) {
				e.printStackTrace();
				}*/
	
	}
	
	
	public static File ScreenShotTaker(){
		
		File scrFile = null;
		if(CommonTest.isFF()||CommonTest.isIE()){
		try {
	
		
			scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		
		} 
		catch(WebDriverException wex) {
			Reporter.log("Encountered exception "+wex.getMessage()+" on taking screen shot");
			wex.printStackTrace();
		}
		}
		else{
			try {
				scrFile = new File(ScreenshotUtil.makeFullScreenshot(driver));
			} catch (IOException e) {
				Reporter.log("Encountered exception "+e.getMessage()+" on taking screen shot");
				e.printStackTrace();
			} catch (InterruptedException e) {
				Reporter.log("Encountered exception "+e.getMessage()+" on taking screen shot");
				e.printStackTrace();
			}
		}
		return scrFile;
		
	}
	
	/****
	 * getResponseCode: Get HTTP response code for any given url
	 * @param urlString
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static int getResponseCode(String urlString) throws MalformedURLException, IOException {
		
		URL u = new URL(urlString);
		HttpURLConnection huc =(HttpURLConnection) u.openConnection();
		
		if(urlString.contains("ic")){
		String authStr = "rashi_chopra" + ":" + Decryption.decrypt("DGtAhouo8WQx8nXPBv1pjn4/R0cu6jUL");
		byte[] authEncBytes = Base64.encodeBase64(authStr.getBytes());
		String authStringEnc = new String(authEncBytes);  
		huc.setRequestMethod("POST");
		huc.setDoOutput(true);
	    huc.setRequestProperty("Authorization", "Basic " + authStringEnc);
		}
		
		huc.setRequestMethod("GET");
		huc.connect();
		
		return huc.getResponseCode();
	}

	/*****
	 * executeJavascript: Method to execute javascript in selenium
	 * @param javascript
	 */
	 public void executeJavascript(String javascript) {
	        ((JavascriptExecutor)driver).executeScript(javascript);
	    }
	 
	 /****
	  * getConsoleErrors: Use Selenium logging API to get browser console logs 
	  */
	 public static void getConsoleErrors(String url){
		 
		 Logs log = driver.manage().logs();
	     List<LogEntry> logsEntries = log.get("browser").getAll();
	        
	     for (LogEntry entry: logsEntries) {
	       
	        if (entry.getLevel().toString().equals("SEVERE")){
	        	Reporter.log("###################Start Displaying Console Errors###################");
	        	System.out.println("###################Start Displaying Console Errors###################");
	        	Reporter.log(entry.getMessage()+" for Page "+url);
	            System.out.println(entry.getMessage()+" for Page "+url);
	            Reporter.log("###################Stop Displaying Console Errors###################");
	            System.out.println("###################Stop Displaying Console Errors###################");
	        }
	        }
	 }
	 
	
}
