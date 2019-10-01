package com.apple.tests;


import org.testng.Reporter;
import org.testng.annotations.Factory;

import org.testng.annotations.Test;

import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;

public class ProductNavTests extends CommonTest{
	
	
	//String ios_url[] = readFile("iOSProductNavList");
	//String iphone_url[] = readFile("iPhoneProductNav");
	String actual_url;
	private String URLS = null;
	
	 @Factory(dataProvider="fileDataProvider")
	  public ProductNavTests(String URLS){
		  
		 if(URLS.equals("Home")){
			  this.URLS="";
		  }
		  else{
		  this.URLS=URLS;
		  }
		
	  }
	  
	  
	  @Test( dataProvider="iOS_prodNav", groups="Launch")
	  public void iOS_productNavList(String xpath, String text, String title, String url){

		  Reporter.log("Product Nav Tests for: "+ (BaseURL+URLS)+"<br>");
		  System.out.println("Product Nav Tests for: "+ (BaseURL+URLS));
		  loadPage(driver, BaseURL+URLS);
		  CommonModules nav = new CommonModules(driver);
		  if(nav.productNav()){
			  Reporter.log("EXPECTED : "+text+ " : "+ (BaseURL+url)+"<br>");
			  System.out.println("EXPECTED : "+text+ " : "+ (BaseURL+url));
		  actual_url = nav.productNavList(xpath, text, title);
		  if(!(actual_url.equals("invalid"))) verifyDestinationURLNoBack(actual_url,BaseURL+url,"Failed on Page: "+(BaseURL+URLS));
		  }
		  else{
			  Reporter.log("WARNING: Product Nav does not exist on Page: "+(BaseURL+URLS));
		  }
	 }
	  
	 /* @Test( dataProvider="iPhone_prodNav")
	  public void iPhone_productNavList(String xpath, String text, String title, String url){

		  for(int i=0;i<iphone_url.length;i++){
		  loadPage(driver, BaseURL+iphone_url[i]);
		  logger.info("Running Product Nav tests for: "+(BaseURL+iphone_url[i]));
		  CommonModules nav = new CommonModules(driver);
		  nav.productNav();
		  actual_url = nav.productNavList(xpath, text, title);
		  verifyDestinationURLNoBack(actual_url,url);
		  }
	 }*/
	 
  
}
