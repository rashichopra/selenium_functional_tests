package com.apple.tests;


import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;

public class SEOCheckTests extends CommonTest{
  
	
	private String URLS = null;
	
	 @Factory(dataProvider="fileDataProvider")
	  public SEOCheckTests(String URLS){
		  
		 if(URLS.equals("Home")){
			  this.URLS="";
		  }
		  else{
		  this.URLS=URLS;
		  }

	  }
	
	@Test(groups={"SEO","Launch"})
	public void pageTitleValidation() {
		Reporter.log("Checking Title length for: "+(BaseURL+URLS)+"<br>" );
		loadPage(driver,BaseURL+URLS);
		Assert.assertNotNull(CommonModules.pageTitleValidation(),"No Title set for the Page: "+(BaseURL+URLS));
		Assert.assertTrue(CommonModules.pageTitleValidation().toCharArray().length<=70,"(Title length: "+CommonModules.pageTitleValidation().toCharArray().length+" for Page: "+(BaseURL+URLS));
		
	}
	
	@Test(groups={"SEO","Launch"})
	public void omnitagValidation() {
		Reporter.log("Checking omni_page length for: "+(BaseURL+URLS)+"<br>" );
		loadPage(driver,BaseURL+URLS);
		CommonModules mod = new CommonModules(driver);
		Assert.assertNotNull(mod.omnitagValidation(),"Omni_page tag not set for the Page: "+(BaseURL+URLS));
	}
	
	@Test(groups={"SEO","Launch"})
	public void metaDescriptionValidation() {
		Reporter.log("Checking Meta Desc length for: "+(BaseURL+URLS)+"<br>" );
		System.out.println("Meta Desc, Title & Omni_page Check for: "+(BaseURL+URLS) );
		loadPage(driver,BaseURL+URLS);
		CommonModules mod = new CommonModules(driver);
		mod.printSEOAttributes();
		Assert.assertNotNull(mod.metaDescriptionValidation(), "Meta Description not set for the page: "+(BaseURL+URLS));
		Assert.assertTrue(mod.metaDescriptionValidation().toCharArray().length<=150, "(Meta Desc Length: "+mod.metaDescriptionValidation().toCharArray().length+" for Page: "+(BaseURL+URLS));
		
	}
	
	
}
