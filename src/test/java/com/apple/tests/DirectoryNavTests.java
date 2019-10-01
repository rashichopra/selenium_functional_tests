package com.apple.tests;

import org.testng.Reporter;
import org.testng.annotations.Factory;

import org.testng.annotations.Test;

import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;


public class DirectoryNavTests extends CommonTest {
	
	
	//String ios_url[] = readFile("iOSDirectoryNavList");
	//String iphone_url[] = readFile("iPhoneDirectoryNav");
	
	String actual_url;
	private String URLS = null;
	
	 @Factory(dataProvider="fileDataProvider")
	  public DirectoryNavTests(String URLS){
		  
		 if(URLS.equals("Home")){
			  this.URLS="";
		  }
		  else{
		  this.URLS=URLS;
		  }

	  }
	  
	  @Test( dataProvider="iOS_dirNav", groups="Launch")
	  public void iOS_dirNavList(String xpath, String text, String title, String url){
		
		  Reporter.log("Directory Nav Tests for: "+ (BaseURL+URLS)+"<br>");
		  System.out.println("************Running Directory Nav Tests for: "+ (BaseURL+URLS)+"***************");
		 
		  loadPage(driver, BaseURL+URLS);
//		  for(int i=0;i<ios_url.length;i++){
//			loadPage(driver, BaseURL+ios_url[i]);
//			logger.info("Running Directory Nav tests for: "+(BaseURL+ios_url[i]));
		  CommonModules nav = new CommonModules(driver);
		  if(nav.directoryNav()){
			  Reporter.log("EXPECTED : "+text+ " : "+ (BaseURL+url)+"<br>");
			  System.out.println("EXPECTED : "+text+ " : "+ (BaseURL+url));
		  actual_url = nav.directoryNavList(xpath, text, title);
		  verifyDestinationURLNoBack(actual_url,BaseURL+url,"Failed on Page: "+(BaseURL+URLS));
		  }else{
			  Reporter.log("Directory Nav does not exist on Page: "+(BaseURL+URLS));
		  }
	 }
	  
	/*  @Test( dataProvider="iphone_dirNav")
	  public void iphone_dirNavList(String xpath, String text, String title, String url){
		  
		  for(int i=0;i<iphone_url.length;i++){
			loadPage(driver, BaseURL+iphone_url[i]);
			logger.info("Running Directory Nav tests for: "+(BaseURL+iphone_url[i]));
		  CommonModules nav = new CommonModules(driver);
		  nav.directoryNav();
		  actual_url = nav.directoryNavList(xpath, text, title);
		  verifyDestinationURLNoBack(actual_url,BaseURL+url);
		  }
	 }*/
}
