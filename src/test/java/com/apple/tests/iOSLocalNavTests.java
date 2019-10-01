package com.apple.tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;

public class iOSLocalNavTests extends CommonTest{
	//String ios_url[] = readFile("iOSProductNavList");
		//String iphone_url[] = readFile("iPhoneProductNav");
		String actual_url;
		String actual_text;
		private String URLS = null;
		
		 @Factory(dataProvider="fileDataProvider")
		  public iOSLocalNavTests(String URLS){
			  
			 if(URLS.equals("Home")){
				  this.URLS="";
			  }
			  else{
			  this.URLS=URLS;
			  }
			
		  }
		    
		 @Test( dataProvider="iOS_localNav", groups="Launch")
		  public void iOS_localNav(String xpath, String text, String title, String url){

			  Reporter.log("Verify LocalNav Element with the same text as the URL is grayed out & not clickable for: "+ (BaseURL+URLS)+"<br>");
			  System.out.println("Verify LocalNav Element with the same text as the URL is grayed out & not clickable for: "+ (BaseURL+URLS));
			  loadPage(driver, BaseURL+URLS);
			  CommonModules nav = new CommonModules(driver);
			  actual_text = nav.localNav(xpath, text, title);
			  String[] str = URLS.split("/");
			  Assert.assertNotEquals(actual_text.toLowerCase(),str[(str.length-1)], actual_text+" link clickable for Page: "+(BaseURL+URLS));
		 }
		 
		@Test( dataProvider="iOS_localNav", groups="Launch")
		  public void iOS_localNavList(String xpath, String text, String title, String url){

			  Reporter.log("LocalNav Tests for: "+ (BaseURL+URLS)+"<br>");
			  System.out.println("LocalNav Tests for: "+ (BaseURL+URLS));
			  loadPage(driver, BaseURL+URLS);
			  CommonModules nav = new CommonModules(driver);
			  Reporter.log("EXPECTED : "+text+ " : "+ (BaseURL+url)+"<br>");
			  System.out.println("EXPECTED : "+text+ " : "+ (BaseURL+url));
			  actual_url = nav.localNavList(xpath, text, title);
			  if(actual_url.contains("/")){
			  verifyDestinationURLNoBack(actual_url,BaseURL+url,"Failed on Page: "+(BaseURL+URLS));
			  }
			  else{
				String[] str = URLS.split("/");
			    Assert.assertEquals(actual_url.toLowerCase(),str[(str.length-1)],"Failed on Page: "+(BaseURL+URLS));
			  }
		 }
		  
}
