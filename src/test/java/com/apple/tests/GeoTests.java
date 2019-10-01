package com.apple.tests;

import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.apple.Common.CommonActions;
import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;
import com.apple.listeners.SoftAssertor;
import com.apple.util.PropertyFileReader;

public class GeoTests extends CommonTest{

	PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	
	  @Test(dataProvider="geo_pages",groups={"Launch", "Geo"})
	    public void GlobalNavGeoTests(String url)  {
	       
		  Reporter.log("Global Nav Geo Tests for: "+ (BaseURL+url)+"<br>");
		 
		  loadPage(driver, BaseURL+url);
		  int links = driver.findElements(By.xpath(prop.getValue("GlobalNavLinks_XPath"))).size();
		  CommonModules nav = new CommonModules(driver);
		  for(int i=1;i<links;i++){
			String href = nav.globalNavGeo(i);
			if(driver.getTitle().equals(prop.getValue("404_TEXT")))	{
				Reporter.log("!!!!!!!ERROR!!!!!!!:404 Found for link: "+href+"<br>");
				SoftAssertor.assertFalse(driver.getTitle().equals(prop.getValue("404_TEXT")),"404 on link "+href+" for Page "+(BaseURL+url));
				driver.navigate().back();
				CommonActions.sleep(1000);
				SoftAssertor.assertEquals(driver.getCurrentUrl(),BaseURL+url);
			}
			else{
			SoftAssertor.assertEquals(driver.getCurrentUrl(), href,"Expected URL: "+href+" does not match actual URL: "+driver.getCurrentUrl());
			driver.navigate().back();
			CommonActions.sleep(1000);
			SoftAssertor.assertEquals(driver.getCurrentUrl(),BaseURL+url);
			}
		  }
		  
	    }

}
