package com.apple.tests;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.apple.Common.CommonActions;
import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;
import com.apple.listeners.SoftAssertor;
import com.apple.util.PropertyFileReader;

public class StatusCheckTests extends CommonTest{
  
	PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	private String URLS = null;
	
	 @Factory(dataProvider="fileDataProvider")
	  public StatusCheckTests(String URLS){
		  if(URLS.equals("Home")){
			  this.URLS="";
		  }
		  else{
		  this.URLS=URLS;
		  }
		
	  }
	 
	 @Test(groups={"Launch","Status"})
		public void linkChecker() {
			
			Reporter.log("Click & Verify each Link clickable & not a 404 for: "+(BaseURL+URLS)+"<br>");
			System.out.println("************Broken Links Check for: "+"**********"+(BaseURL+URLS)+"********" );
			
			loadPage(driver, BaseURL+URLS);
			if(!(CommonTest.isIE()||CommonTest.isSafari())){
			CommonActions.getConsoleErrors(driver.getCurrentUrl());
			}
			CommonModules mod = new CommonModules(driver);
			int links = driver.findElements(By.xpath(prop.getValue("allLinks_XPath"))).size();
			for(int i=0;i<(links-27);i++){
			String href = mod.linkChecker(i);
			if(driver.getTitle().equals(prop.getValue("404_TEXT")))	{
				Reporter.log("!!!!!!!ERROR!!!!!!!:404 Found for link: "+href+"<br>");
				System.out.println("!!!!!!!ERROR!!!!!!!:404 Found for link: "+href);
				SoftAssertor.assertFalse(driver.getTitle().equals(prop.getValue("404_TEXT")),"404 on link "+href+" for Page "+(BaseURL+URLS));
				driver.navigate().back();
				CommonActions.sleep(1000);
				SoftAssertor.assertEquals(driver.getCurrentUrl(),BaseURL+URLS);
			}
			else if(!(href.equals("invalid"))&&!(href.equals("/"))){
			SoftAssertor.assertEquals(driver.getCurrentUrl(),href, "**NOT a 404**, might be a redirect or link not clicked-Please Check manually");
			driver.navigate().back();
			CommonActions.sleep(1000);
			SoftAssertor.assertEquals(driver.getCurrentUrl(),BaseURL+URLS);
			}
			else if((href.equals("/"))){
				SoftAssertor.assertEquals(driver.getCurrentUrl(),BaseURL, "**NOT a 404**, might be a redirect or link not clicked-Please Check manually");
				driver.navigate().back();
				CommonActions.sleep(1000);
				SoftAssertor.assertEquals(driver.getCurrentUrl(),BaseURL+URLS);
			}
			}
		}
	 
	 
	@Test(groups={"Launch","Status"})
	public void linksStatusCheck() throws MalformedURLException, IOException {
		Reporter.log("Broken Links Status Check for: "+(BaseURL+URLS)+"<br>");
		loadPage(driver, BaseURL+URLS);
		CommonModules mod = new CommonModules(driver);
		Assert.assertTrue("One or more of the links are broken for Page: "+(BaseURL+URLS),mod.brokenlinksStatusChecker(BaseURL+URLS));
		
	}
	
	/*@Test(groups={"Launch","Status"})
	public void JSStatusCheck() throws MalformedURLException, IOException {
	//	Reporter.log("=====================================================================================" );
	//	Reporter.log("*************JS Files Status Check tests for: "+"********"+(BaseURL+URLS)+"*******" );
	//	Reporter.log("=====================================================================================" );
		loadPage(driver, BaseURL+URLS);
		CommonModules mod = new CommonModules(driver);
		Assert.assertTrue("One or more of the links are broken for Page: "+(BaseURL+URLS)+", See log for details", mod.JSFilesStatusChecker(BaseURL+URLS));
		
	}
	
	@Test(groups={"Launch","Status"} )
	public void CSSStatusCheck() throws MalformedURLException, IOException {
	//	Reporter.log("=====================================================================================" );
	//	Reporter.log("*************CSS Files Status Check tests for: "+"*******"+(BaseURL+URLS)+"*******" );
	//	Reporter.log("=====================================================================================" );
		loadPage(driver, BaseURL+URLS);
		CommonModules mod = new CommonModules(driver);
		Assert.assertTrue("One or more of the links are broken for Page: "+(BaseURL+URLS)+", See log for details", mod.CSSFilesStatusChecker(BaseURL+URLS));
		
	}
	
	@Test(groups={"Launch","Status", "Image"})
	public void ImageStatusCheck() throws MalformedURLException, IOException {
	//	Reporter.log("=====================================================================================" );
	//	Reporter.log("**************Image Files Status Check tests for: "+"*******"+(BaseURL+URLS)+"******" );
	//	Reporter.log("=====================================================================================" );
		loadPage(driver, BaseURL+URLS);
		CommonModules mod = new CommonModules(driver);
		Assert.assertTrue("One or more of the links are broken for Page: "+(BaseURL+URLS)+", See log for details", mod.ImageFilesStatusChecker(BaseURL+URLS));
		
	}*/
	
}
