package com.apple.iPhonetests;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;
import com.apple.util.PropertyFileReader;

public class GlobalNavTests extends CommonTest{
	
	
	PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	String actual_url;
	
	
	 @Test
	  @Parameters("URLS")
	  public void iOS_globalNav(String URLS){
		  loadPage(driver, URLS);
		  System.out.println("Is the method running");
		  CommonModules nav = new CommonModules(driver);
		  nav.globalNav();
	  }
	  
	  @Test(dataProvider="apple_globalNav", dependsOnMethods="iOS_globalNav")
	  public void iOS_globalNavList(String text, String title, String url){
		  CommonModules nav = new CommonModules(driver);
		//  actual_url = nav.globalNavList(text,title);
		  //verifyDestinationURL(actual_url,url);
	  }
	
}
