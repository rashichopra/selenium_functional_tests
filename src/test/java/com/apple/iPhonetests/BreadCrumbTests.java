package com.apple.iPhonetests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;
import com.apple.util.PropertyFileReader;

public class BreadCrumbTests extends CommonTest{
	
	PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	String actual_url;
 
  
	  @Test
	  @Parameters("URLS")
	  public void globalFooter_breadcrumbs(String URLS){
	  loadPage(driver, URLS);
	  CommonModules nav = new CommonModules(driver);
	  nav.breadCrumbs("iOS");
	  }
	  
	  @Test
	  @Parameters({"URLS", "page"})
	  public void globalFooter_breadcrumbs1(String URLS, String page){
	  loadPage(driver, URLS);
	  CommonModules nav = new CommonModules(driver);
	 
	  }
}
