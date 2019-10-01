package com.apple.tests;


import org.testng.Reporter;

import org.testng.annotations.Test;

import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;
import com.apple.util.PropertyFileReader;

public class BreadCrumbTests extends CommonTest{
	
	PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	String actual_url;
	  
	  /*@Test(dataProvider="bc_firstlevel", groups="Launch")
	  public void firstlevel_breadcrumbs(String url, String name){
		  
		  loadPage(driver, BaseURL+url);
		 
		  Reporter.log("BreadCrumb tests for: "+(BaseURL+url)+"<br>");
		 
		  CommonModules nav = new CommonModules(driver);
		  Reporter.log("EXPECTED : "+name+"<br>");
		  nav.breadCrumbs(name);
	  }
	  
	  @Test(dataProvider="bc_seclevel", groups="Launch")
	  public void seclevel_breadcrumbs(String url, String name1,String title,String link, String name2){
		  
		  loadPage(driver, BaseURL+url);
		 
		  Reporter.log("BreadCrumb tests for: "+(BaseURL+url)+"<br>");
		
		  CommonModules nav = new CommonModules(driver);
		  Reporter.log("EXPECTED : "+name2+" : "+name1+" : "+(BaseURL+link)+"<br>");
		  actual_url = nav.breadCrumbs(name1,title,name2);
		  verifyDestinationURLNoBack(actual_url,BaseURL+link,"Failed on Page: "+(BaseURL+url));
	  }*/
	  
	  
	  @Test(dataProvider="bc_thirdlevel")
	  public void thirdlevel_breadcrumbs(String url, String name1,String title1,String link1, String name2,String title2, String link2,String name3){
		  
		  loadPage(driver, BaseURL+url);
		  Reporter.log("BreadCrumb tests for: "+(BaseURL+url)+"<br>");
		  System.out.println("********Running BreadCrumb tests for: "+(BaseURL+url)+"********");
		 
		  CommonModules nav = new CommonModules(driver);
		  Reporter.log("EXPECTED : "+name3+" : "+name1+" : "+(BaseURL+link1)+" : "+name2+" : "+link2+"<br>");
		  actual_url = nav.breadCrumbs(name1,title1,BaseURL+link1,name2,title2,name3);
		  verifyDestinationURLNoBack(actual_url,BaseURL+link2, "Failed on Page: "+(BaseURL+url));
	  }
	  
}
