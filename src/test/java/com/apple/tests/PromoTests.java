package com.apple.tests;


import org.testng.Reporter;
import org.testng.annotations.Test;

import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;

public class PromoTests extends CommonTest{
	
	String actual_url;
	
  
	@Test(dataProvider="promos", groups="promo")
	public void verifyPromoTests(String url, String elem, String title, String turl) {
		
		 loadPage(driver, BaseURL+url);
		 Reporter.log("Running Promos Testing for: "+(BaseURL+url));
		 CommonModules nav = new CommonModules(driver);
		 actual_url = nav.promosTesting(elem,title);
		
		 verifyDestinationURLNoBack(actual_url,turl,"Failed on Page: "+(BaseURL+url));
		
	}
	
	@Test(dataProvider="promos")
	public void verifyPromoRemovalTests(String url, String elem, String title, String turl) {
		
		 loadPage(driver, BaseURL+url);
		 Reporter.log("Running Promos Testing for: "+(BaseURL+url));
		 CommonModules nav = new CommonModules(driver);
		 nav.promoRemoval(elem);
		
	}
	
}
