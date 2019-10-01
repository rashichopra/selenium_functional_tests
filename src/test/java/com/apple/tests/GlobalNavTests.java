package com.apple.tests;


import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.apple.Common.CommonActions;
import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;
import com.apple.listeners.SoftAssertor;
import com.apple.util.PropertyFileReader;

public class GlobalNavTests extends CommonTest{
	
	
	PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	private String URLS = null;
	
	String actual_url;
	//String arr_url[] = readFile("GlobalNavList");
	  
	
	  @Factory(dataProvider="fileDataProvider")
	  public GlobalNavTests(String URLS){
		  
		  if(URLS.equals("Home")){
			  this.URLS="";
		  }
		  else{
		  this.URLS=URLS;
		  }
		 
	  }
	  
	  
	  @Test(dataProvider="apple_globalNav", groups={"Launch", "us"})
	  public void GlobalNavListTests(String text, String title, String url){
		  
		  Reporter.log(" Test For: "+ (BaseURL+URLS)+" for Global Nav list item "+text+"<br>");
		  System.out.println("***********Running Global Nav Tests for: "+ (BaseURL+URLS)+" for list item "+text+"******");
		 
		  loadPage(driver, BaseURL+URLS);
		  if(URLS.contains("ipad-air")){
		  CommonActions.sleep(10000);
		  }
		  CommonModules nav = new CommonModules(driver);
		  Assert.assertTrue(nav.globalNav(), "Global Nav does not exist on this Page:"+(BaseURL+URLS));
		  Reporter.log("EXPECTED : "+text+ " : "+ (BaseURL+url)+"<br>");
		  System.out.println("EXPECTED : "+text+ " : "+ (BaseURL+url));
		  actual_url = nav.globalNavList(text,title);
		  verifyDestinationURLNoBack(actual_url,BaseURL+url,"Failed on Page: "+(BaseURL+URLS));
		  
		// }
	  }
	  
	
	  
	  
	  @Test(groups={"Launch", "global"})
	  public void globalbreadcrumbTests(){
		  
		  Reporter.log(" BreadCrumb Tests for: "+ (BaseURL+URLS)+" Home Link"+"<br>");
		  System.out.println("***********Running Global(Home Link) BreadCrumb Tests for: "+ (BaseURL+URLS)+"*************");
		 
		   loadPage(driver, BaseURL+URLS);
		   CommonModules nav = new CommonModules(driver);
		   Reporter.log("EXPECTED : "+prop.getValue("HOME_TEXT")+ " : "+ BaseURL+"<br>");
		   System.out.println("EXPECTED : "+prop.getValue("HOME_TEXT")+ " : "+ BaseURL);
		   actual_url = nav.breadCrumbs();
		   verifyDestinationURLNoBack(actual_url,BaseURL,"Failed on Page: "+(BaseURL+URLS));
			 
	  }
	
	  
	 /* @Test(groups={"Launch", "global", "screenshot"})
		public void pageScreenshots() {
			
		  Reporter.log("==============================================================================================" );
		  Reporter.log("ScreenShot taken for Page : "+"********"+(BaseURL+URLS)+"*********");
		  Reporter.log("==============================================================================================" );
			loadPage(driver,BaseURL+URLS);
			if(URLS.contains("ipad-air")){
			CommonActions.sleep(10000);
			}
			CommonModules mod = new CommonModules(driver);
			String[] str = URLS.split("/");
			mod.takePageScreenshots(str[(str.length-1)]);
		}*/
}
