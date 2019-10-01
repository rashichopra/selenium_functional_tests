package com.apple.tests;


import junit.framework.Assert;

import org.testng.ITest;
import org.testng.Reporter;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.apple.Common.CommonActions;
import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;
import com.apple.util.PropertyFileReader;

public class SearchTests extends CommonTest {
  
	
	private String URLS = null;
	PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	
	 
	  @Factory(dataProvider="fileDataProvider")
	  public SearchTests(String URLS){
		  
		 if(URLS.equals("Home")){
			  this.URLS="";
		  }
		  else{
		  this.URLS=URLS;
		  }

	  }
	 
	
	/*@Test(dataProvider="search", groups={"Launch", "search", "screenshot"})
	public void SearchOverlayTests(String text) {
		
	
		Reporter.log("For Page: "+(BaseURL+URLS));
		loadPage(driver,BaseURL+URLS);
		if(URLS.contains("ipad-air")){
		CommonActions.sleep(10000);
		}
		CommonModules mod = new CommonModules(driver);
		mod.searchBoxLayoutTesting(text);
		
	}*/
	
	@Test(groups={"Launch", "search", "copy"})
	public void SearchFieldCopyTests() {
		
		Reporter.log("Search Field Copy Tests For Page: "+(BaseURL+URLS));
		loadPage(driver,BaseURL+URLS);
		if(URLS.contains("ipad-air")){
		CommonActions.sleep(10000);
		}
		CommonModules mod = new CommonModules(driver);
		Assert.assertEquals(mod.searchFieldCopy(),prop.getValue("search_placeholder"));
		
	}
}
