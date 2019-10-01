package com.apple.iPhonetests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;
import com.apple.util.PropertyFileReader;

public class DirectoryNavTests extends CommonTest {
	
	PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	String actual_url;
	
	
	  @Test
	  @Parameters("URLS")
	  public void iPhone_directoryNav(String URLS){
		  loadPage(driver, URLS);
		  CommonModules nav = new CommonModules(driver);
		  nav.directoryNav();
	  }
	  
	  
	  @Test( dataProvider="iPhone_dirNav", dependsOnMethods="iPhone_directoryNav")
	  public void iPhone_dirNavList(String xpath, String text, String title, String url){
		  CommonModules nav = new CommonModules(driver);
		  actual_url = nav.directoryNavList(xpath, text, title);
		  verifyDestinationURL(actual_url,url);
	 }
}
