package com.apple.tests;

import org.testng.Reporter;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;
import com.apple.util.PropertyFileReader;

public class GlobalFooterTests extends CommonTest{
	
	PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	private String URLS = null;
	
	String actual_url;
	//String arr_url[] = readFile("GlobalNavList");
	
	  @Factory(dataProvider="fileDataProvider")
	  public GlobalFooterTests(String URLS){
		  
		  if(URLS.equals("Home")){
			  this.URLS="";
		  }
		  else{
		  this.URLS=URLS;
		  }
		 
	  }
	  
	  @Test(groups={"Launch", "global"})
	  public void globalFooterTests(){
	
		  Reporter.log("Testing Global Footer exists for: "+ (BaseURL+URLS)+"<br>");
		  System.out.println("***********Global Footer Exists Tests for: "+ (BaseURL+URLS));
		
		  loadPage(driver, BaseURL+URLS);
	
		  CommonModules ft = new CommonModules(driver);
		  ft.globalFooter_Text();
		  ft.globalFooter_countryLink();
	  }
	  
	  @Test(dataProvider="apple_gfLinks", groups={"Launch", "global"})
	  public void globalFooterLinksTests(String identifier, String text, String title, String url){
		 
		  Reporter.log("Global Footer Links Tests for: "+ (BaseURL+URLS)+"<br>");
		  System.out.println("***********Running Global Footer Links Tests for: "+ (BaseURL+URLS));
		
		  loadPage(driver, BaseURL+URLS);
	
		  CommonModules ft = new CommonModules(driver);
		  Reporter.log("EXPECTED : "+text+ " : "+ (BaseURL+url)+"<br>");
		  System.out.println("EXPECTED : "+text+ " : "+ (BaseURL+url));
		  actual_url = ft.globalFooter_Links(identifier,text,title);
		  verifyDestinationURLNoBack(actual_url,BaseURL+url,"Failed on Page: "+(BaseURL+URLS));
		
	  }
	  
	
}
