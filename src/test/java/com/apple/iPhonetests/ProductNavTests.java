package com.apple.iPhonetests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;
import com.apple.util.PropertyFileReader;

public class ProductNavTests extends CommonTest{
	
	PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	String actual_url;
	
	
	  @Test
	  @Parameters("URLS")
	  public void iOS_productNav(String URLS){
		  loadPage(driver, URLS);
		  CommonModules nav = new CommonModules(driver);
		  nav.productNav();
	  }
	  
	  
	  @Test( dataProvider="iOS_prodNav", dependsOnMethods="iOS_productNav")
	  public void iOS_productNavList(String xpath, String text, String title, String url){
		  CommonModules nav = new CommonModules(driver);
		  actual_url = nav.productNavList(xpath, text, title);
		 
	 }
	 
	  
	  @Test(dataProvider="iOS_prodLogo", dependsOnMethods="iOS_productNav")
	  public void iOS_productLogo(String xpath, String text, String src, String title, String url){
		  CommonModules nav = new CommonModules(driver);
		  actual_url=nav.productLogo(xpath, text, src, title);
		  verifyDestinationURL(actual_url,url);
	  }
  
}
