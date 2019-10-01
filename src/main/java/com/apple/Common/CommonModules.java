/**
 * 
 */
package com.apple.Common;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.mindengine.galen.browser.Browser;
import net.mindengine.galen.browser.SeleniumBrowser;
import net.mindengine.galen.page.Page;
import net.mindengine.galen.page.selenium.SeleniumPage;
import net.mindengine.galen.specs.page.PageSection;
import net.mindengine.galen.specs.reader.page.PageSpec;
import net.mindengine.galen.specs.reader.page.PageSpecReader;
import net.mindengine.galen.specs.reader.page.SectionFilter;
import net.mindengine.galen.validation.PageValidation;
import net.mindengine.galen.validation.SectionValidation;
import net.mindengine.galen.validation.ValidationError;
import net.mindengine.galen.validation.ValidationListener;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;

import com.apple.util.PropertyFileReader;
import com.apple.util.ScreenshotUtil;

/**
 * @author rashi_chopra
 *
 */
public class CommonModules extends CommonActions{

	
	PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	private String expr, expr2, Message, Message2=null;
	private String GF_copyright_Text="Copyright © 2014 Apple Inc. All rights reserved.";
	
	private static int buy_index= 2;
	private static int piped_index=1;
	private static int sos_index=1;
	private static int link_index=1;
	
	private List<String> prodNames1 = Arrays.asList("iOS", "iPhone", "Mac");
	private static String title;

	private String meta_desc;
	private String omni_page;
	Actions builder = new Actions(driver);
		
	public CommonModules(WebDriver driver) {
		super(driver);
	}
	
	
	/******************************************Global/Local Navigation****************************************/
	
	
	/*****
	 * globalNav: Verify the existence of the Global Nav on the Page
	 */
	public boolean globalNav(){
		
		expr = prop.getValue("globNav_Id");
		Message = "Global nav Element does not exist";
		Message2= "Global nav is not visible on the Page";
		
		boolean nav = isElementPresent_Id(expr);
		
		return nav;
		//System.out.println("Global Nav Exists and is visible on the Page");
		
	}
	
	
	/*****
	 * globalNavList: Verify the Links on the global nav and respective destination
	 * @param GN_Text
	 * @param LP_Title
	 * @return
	 */
	public String globalNavList(String GN_Text, String LP_Title){
	
		expr = prop.getValue("GN_"+GN_Text+"_CSS");
		Message = "Global Nav Element for " +GN_Text +" not found";
		
		WebElement gn = getElement_CSS(expr, Message);
		Reporter.log("ACTUAL : "+gn.getText()+" : ");
		System.out.println("ACTUAL TEXT : "+gn.getText());
		clickElement(gn, GN_Text);
		Wait_pageLoad(driver,LP_Title);
		
		Reporter.log(getPageURL());
		System.out.println("ACTUAL URL : "+ getPageURL());
		return getPageURL();
		
	}
	
	public String globalNavGeo(int i){
		
		WebElement gnav = driver.findElement(By.xpath("//ul[@id='globalnav']/li["+(i+1)+"]/a"));
			String href = gnav.getAttribute("href");
			gnav.click();
			sleep(1000);
			return href;
		
	}
	
	/*****
	 * productNav: Verify the existence of Product Nav on the Page
	 */
	public boolean productNav(){
		
		expr = prop.getValue("prodNav_Id");
		Message = "Product Nav Element does not exist";
		Message2 = "Product Nav is not displayed on the Page";
	
		boolean nav = isElementPresent_Id(expr);
		//System.out.println(nav);
		return nav;
		//System.out.println("Product Nav Exists and is visible on the Page");
		
	}
	
    public String localNav(String PN_Xpath, String PN_Text, String LP_Title){
		
    	String link=null;
    	expr = prop.getValue("whatsnew_id");
    	Message = "Local Nav Element for "+PN_Text+" not found";
		Message2 = "Local Nav Element 'What's New' not found";
		WebElement ln = getElement_Id(expr,Message2);
		ln.click();
		sleep(1000);
		if(isElementPresent_XPath(PN_Xpath+"a")){
		WebElement pn = getElement_XPath(PN_Xpath+"a", Message);
		link=pn.getText();
		}
		else{
			Reporter.log(PN_Text+" is not clickable and is expected for this Element on this Page");
			System.out.println(PN_Text+" is not clickable and is expected for this Element on this Page");
			link="not clickable";
		}
		
		return link;
	}
    
	
	/*****
	 * productNavList: Verify the Links on the Product Nav and respective destination
	 * @param PN_Xpath
	 * @param PN_Text
	 * @param LP_Title
	 * @return
	 */
	public String productNavList(String PN_Xpath, String PN_Text, String LP_Title){
		
		Message = "Product Nav Element for "+PN_Text+" not found";
		String link = null;
		WebElement pn = getElement_CSS(PN_Xpath, Message);
		Reporter.log("ACTUAL: "+pn.getText()+" : ");
		if(pn.isDisplayed()){
		clickElement(pn,PN_Text);
		Wait_pageLoad(driver, LP_Title);
		Reporter.log(getPageURL());
		WebElement pn1 = getElement_CSS(PN_Xpath, Message);
		verifyTextColor(pn1,prop.getValue("GRAY"));
		link = getPageURL();
		}
		else link = "invalid";
		return link;
		
	}
	
     public String localNavList(String PN_Xpath, String PN_Text, String LP_Title){
		
    	String link=null;
    	expr = prop.getValue("whatsnew_id");
		Message = "Local Nav Element for "+PN_Text+" not found";
		Message2 = "Local Nav Element 'What's New' not found";
		WebElement ln = getElement_Id(expr,Message2);
		ln.click();
		sleep(1000);
		if(isElementPresent_XPath(PN_Xpath+"a")){
		WebElement pn = getElement_XPath(PN_Xpath+"a", Message);
		verifyTextColor(pn,prop.getValue("BLACK"));
		Reporter.log("ACTUAL: "+pn.getText()+" : ");
		System.out.println("ACTUAL TEXT: "+pn.getText());
		clickElement(pn,PN_Text);
		Wait_pageLoad(driver, LP_Title);
		Reporter.log(getPageURL());
		System.out.println("ACTUAL URL: "+getPageURL());
		link=getPageURL();
		}
		else{
			WebElement pn = getElement_XPath(PN_Xpath+"span", Message);
			Reporter.log("ACTUAL: "+pn.getText()+" : ");
			System.out.println("ACTUAL TEXT: "+pn.getText());
			Reporter.log("Not Clickable-Expected");
			verifyTextColor(pn,prop.getValue("GRAY"));
			link=pn.getText();
		}
		
		return link;
		
	}
	

	/*****
	 * productLogo: Verify the Product Logo alt text, source and destination url
	 * @param PL_Xpath
	 * @param PL_alttext
	 * @param PL_src
	 * @param LP_Title
	 * @param url
	 * @return
	 */
	public String productLogo(String PL_Xpath, String PL_alttext, String PL_src, String LP_Title){
		
		Message = "Product Logo for Product "+ PL_alttext +" not found";
		
		WebElement pl = getElement_XPath(PL_Xpath+"/img", Message);
		verifyImage(pl,PL_alttext, PL_src);
		
		WebElement pl1 = getElement_XPath(PL_Xpath, Message);
		clickElement(pl1);
		Wait_pageLoad(driver, LP_Title);
		return getPageURL();
		
	}
	
	
	
	
	
	
	/****************************************************Footer*********************************************/
	
	/*****
	 * globalFooter_Text: Verify the Buy and Copyright text in the Global Footer
	 */
	public void globalFooter_Text(){
		
		expr = prop.getValue("GF_buy_Xpath");
		Message = "Global Footer Buy Paragraph not found";
		expr2 = prop.getValue("GF_copyright_Xpath");
		Message2 = "Global Footer Copyright Paragraph not found";
		
		WebElement gf_text = getElement_XPath(expr, Message);
		verifyText(gf_text, prop.getValue("GF_buy_Text"));
		
		WebElement gf_text1 = getElement_XPath(expr2, Message2);
		verifyText(gf_text1,GF_copyright_Text);
		
	}
	
	public String globalFooter_Links(String identifier, String GF_Text, String GF_Title){
		
		Message = "Global Footer Links not found";
		
		WebElement gf = getElement_XPath(identifier, Message+GF_Text);
		Reporter.log("ACTUAL : "+gf.getText()+" : ");
		System.out.println("ACTUAL TEXT: "+gf.getText());
		clickElement(gf,GF_Text);
		Wait_pageLoad(driver, GF_Title);
		Reporter.log(getPageURL());
		System.out.println("ACTUAL URL: "+getPageURL());
		buy_index++;
		return getPageURL();
	}
	
	
	public String globalFooter_countryLink(){
		
		expr = prop.getValue("GF_countryLink_Xpath");
		Message = "Global Footer Country Image Link not found";
		
			WebElement gf = getElement_XPath(expr+"/img", Message);
			verifyImage(gf, prop.getValue("GF_ChooseCountry_Alt"), prop.getValue("GF_ChooseCountry_Src"));
			Reporter.log("Country Image Alt: "+prop.getValue("GF_ChooseCountry_Alt")+" : ");
			System.out.println("Expected Country Image Alt: "+prop.getValue("GF_ChooseCountry_Alt"));
			Reporter.log("Src: "+prop.getValue("GF_ChooseCountry_Src")+"<br>");
			System.out.println("Country Image Src: "+prop.getValue("GF_ChooseCountry_Src"));
			WebElement gf1 = getElement_XPath(expr, Message);
			clickElement(gf1);
			Wait_pageLoad(driver, prop.getValue("GF_ChooseCountry_Title"));
			
		Reporter.log("ACTUAL : "+getPageURL());
		System.out.println("ACTUAL URL: "+getPageURL());
		return getPageURL();
	}
	
	
	public String breadCrumbs(){
		
		expr = prop.getValue("GF_breadcrumbs_Xpath");
		Message = "Global Footer Breadcrumbs not found";
		
		WebElement h = getElement_XPath(expr+"[1]/a", Message);
		sleep(3000);
		Reporter.log("ACTUAL : "+h.getText()+" : ");
		System.out.println("ACTUAL TEXT : "+h.getText());
		clickElement(h,prop.getValue("HOME_TEXT"));
		Wait_pageLoad(driver, prop.getValue("HOME_TITLE"));
		
		Reporter.log(getPageURL());
		System.out.println("ACTUAL URL : "+getPageURL());
		return getPageURL();
		}
		
	
	public void breadCrumbs(String pname){
		
		
		expr = prop.getValue("GF_breadcrumbs_Xpath");
		Message = "Global Footer Breadcrumbs not found";
			
		WebElement bc = getElement_XPath(expr+"[2]", Message);
		sleep(3000);
		Reporter.log("ACTUAL : "+bc.getText());
		System.out.println("ACTUAL : "+bc.getText());
		verifyText(bc,pname);
			
		}
	
	public String breadCrumbs(String plink, String ptitle, String pname){
		
		
		expr = prop.getValue("GF_breadcrumbs_Xpath");
		Message = "Global Footer Breadcrumbs not found";
			
		WebElement bc = getElement_XPath(expr+"[3]", Message);
		sleep(3000);
		verifyText(bc,pname);
		
		WebElement bc1 = getElement_XPath(expr+"[2]/a", Message);
		Reporter.log("ACTUAL : "+bc.getText()+" : "+bc1.getText()+" : ");
		System.out.println("ACTUAL TEXT : "+bc.getText()+" : "+bc1.getText());
		clickElement(bc1,plink);
		Wait_pageLoad(driver, ptitle);
		
		Reporter.log(getPageURL());
		System.out.println("ACTUAL URL : "+getPageURL());
		return getPageURL();
		
		}
	
	public String breadCrumbs(String plink1, String ptitle1, String url1, String plink2, String ptitle2, String pname){
		
		
		expr = prop.getValue("GF_breadcrumbs_Xpath");
		Message = "Global Footer Breadcrumbs not found";
			
		WebElement bc = getElement_XPath(expr+"[4]", Message);
		sleep(3000);
		verifyText(bc,pname);
		
		WebElement bc1 = getElement_XPath(expr+"[2]/a", Message);
		Reporter.log("ACTUAL : "+bc.getText()+" : "+bc1.getText()+" : ");
		clickElement(bc1,plink1);
		Wait_pageLoad(driver, ptitle1);
		Assert.assertEquals(getPageURL(), url1);
		Reporter.log(getPageURL()+" : ");
		driver.navigate().back();
		
		WebElement bc2 = getElement_XPath(expr+"[3]/a", Message);
		Reporter.log(bc2.getText()+" : ");
		clickElement(bc2,plink2);
		Wait_pageLoad(driver, ptitle2);
		Reporter.log(getPageURL());
		
		return getPageURL();
		
		}

	
	public boolean directoryNav(){
		
		expr = prop.getValue("dirNav_Id");
		Message = "Directory Nav Element does not exist";
		Message2 = "Directory Nav is not displayed on the Page";
		
		boolean nav = isElementPresent_Id(expr);
		return nav;
		
		//System.out.println("Directory Nav Exists and is visible on the Page");
	}
	
	
	public String directoryNavList(String DN_Xpath, String DN_Text, String LP_Title){
		
		Message = "Directory Nav Element for "+DN_Text+" not found";
		
		WebElement dn = getElement_XPath(DN_Xpath, Message);
		sleep(3000);
		Reporter.log("ACTUAL : "+dn.getText()+" : ");
		System.out.println("ACTUAL TEXT : "+dn.getText());
		clickElement(dn,DN_Text);
		Wait_pageLoad(driver, LP_Title);
		
		Reporter.log(getPageURL());
		System.out.println("ACTUAL URL : "+getPageURL());
		return getPageURL();
	}
	
	
	/*********************************************Promos*******************************************/
	
	/****
	 * promosTesting: Verify Promos present on a list of given pages and navigation works
	 * @param CSS
	 * @param TP_Title
	 * @return
	 */
	public String promosTesting(String CSS, String TP_Title){
		
		Message = "Promo Element not found on the Page";
		
		WebElement pt = getElement_CSS(CSS, Message);
		clickElement(pt);
		//sleep(2000);
		Wait_pageLoad(driver, TP_Title);
		
		return getPageURL();
	}

	/****
	 * promoRemoval: Verify Promos removed from the same set of pages as above
	 * @param CSS
	 */
	public void promoRemoval(String CSS){
		
		WebElement pr = waitOnCSS(CSS);
		Assert.assertNull(pr, "Removed Element is present on the page");
		
	}

	
	/****************************************404 Status Check*************************************/
	
	/*****
	 * brokenlinksStatusChecker, JSFilesStatusChecker, CSSFilesStatusChecker, ImageFilesStatusChecker: Broken links status check methods for all links, JS, image and CSS files
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public boolean brokenlinksStatusChecker(String url) throws MalformedURLException, IOException{
		
		boolean status = true;
		
		List<WebElement> elems = driver.findElements(By.xpath(prop.getValue("allLinks_XPath")));
		for(WebElement elem: elems){
			String link = elem.getAttribute("href");
			Assert.assertNotNull(link, "WARNING: href attribute for one or more links is null. This is NOT a 404 & not necessarily an issue. For Page: "+url);
			
			if(!link.isEmpty()||link!=null){
			
			int rcode = getResponseCode(link);
			
			if(rcode==404){
				status=false;
				Reporter.log("<br>"+"Link: "+link+"............ Status: "+rcode+"<br>");
				System.out.println("Link: "+link+"............ Status: "+rcode);
			}
			else{
				
				System.out.println("Link: "+link+"............ Status: "+rcode);
			}
			}
		}
		
		return status;
	}
	
	public String linkChecker(int i) {
		
		WebElement elem = getElement_XPath("("+prop.getValue("allLinks_XPath")+")"+"["+(i+8)+"]","Element not found");
		String href=elem.getAttribute("href");
		if(!(href.contains("#"))&&elem.isDisplayed()){
			Reporter.log("EXPECTED LINK(href): "+href);
			System.out.println("EXPECTED LINK(href): "+href);
				elem.click();
				sleep(1000);
			Reporter.log("ACTUAL LINK: "+driver.getCurrentUrl());
			System.out.println("ACTUAL LINK: "+driver.getCurrentUrl());
			if(!(CommonTest.isIE()||CommonTest.isSafari())){
				getConsoleErrors(driver.getCurrentUrl());
			}
		}
		else href= "invalid";
		return href;
		
	}
	
	public boolean JSFilesStatusChecker(String url) throws MalformedURLException, IOException{
		
		boolean status = true;
		
		List<WebElement> elems = driver.findElements(By.xpath(prop.getValue("JSLinks_XPath")));
		for(WebElement elem: elems){
			String link = elem.getAttribute("src");
			Assert.assertNotNull(link, "WARNING: src attribute for one or more links is null. This is NOT a 404 & not necessarily an issue. For Page: "+url);
			
			if(!link.isEmpty()||link!=null){
			
			int rcode = getResponseCode(link);
			
			if(rcode==404){
				status=false;
				Reporter.log("Link: "+link+"............ Status: "+rcode+"\n");
				
			}
			else{
				Reporter.log("Link: "+link+"............ Status: "+rcode+"\n");
			}
			}
		}
		
		return status;
	}
	
	
	public boolean CSSFilesStatusChecker(String url) throws MalformedURLException, IOException{
		
		boolean status = true;
		
		List<WebElement> elems = driver.findElements(By.xpath(prop.getValue("CSSLinks_XPath")));
		for(WebElement elem: elems){
			String link = elem.getAttribute("href");
			Assert.assertNotNull(link, "WARNING: href attribute for one or more links is null. This is NOT a 404 & not necessarily an issue. For Page: "+url);
			
			if(!link.isEmpty()||link!=null){
			
			int rcode = getResponseCode(link);
			
			if(rcode==404){
				status=false;
				Reporter.log("Link: "+link+"............ Status: "+rcode+"\n");
				
			}
			else{
				Reporter.log("Link: "+link+"............ Status: "+rcode+"\n");
			}
			}
		}
		
		return status;
	}
	
	public boolean ImageFilesStatusChecker(String url) throws MalformedURLException, IOException{
		
		boolean status = true;
		
		List<WebElement> elems = driver.findElements(By.xpath(prop.getValue("ImageLinks_XPath")));
		for(WebElement elem: elems){
			String link = elem.getAttribute("src");
			Assert.assertNotNull(link, "WARNING: src attribute for one or more links is null. This is NOT a 404 & not necessarily an issue. For Page: "+url);
			
			if(!link.isEmpty()||link!=null){
			
			int rcode = getResponseCode(link);
			
			if(rcode==404){
				status=false;
				Reporter.log("Link: "+link+"............ Status: "+rcode+"\n");
			}
			else{
				Reporter.log("Link: "+link+"............ Status: "+rcode+"\n");
			}
			}
		}
		
		return status;
	}
	
	
	/******************************************SEO Checks*****************************************/
	
	/****
	 * pageTitleValidation, omnitagValidation, metaDescriptionValidation
	 * @return
	 */
	public static String pageTitleValidation(){
		
		title = driver.getTitle();
		
		return title;
	}
	
	public String omnitagValidation(){
		
		expr = prop.getValue("omniTag_XPath");
		WebElement omni = waitOnXPath(expr);
		omni_page = omni.getAttribute("content");
		
		return omni_page;
		
	}
	
	public String metaDescriptionValidation(){
		
		expr = prop.getValue("metaDesc_XPath");
		WebElement meta = waitOnXPath(expr);
		meta_desc = meta.getAttribute("content");
		
		return meta_desc;
		
	}
	
	public void printSEOAttributes(){
		
		Reporter.log("Title: "+pageTitleValidation());
		if(pageTitleValidation().toCharArray().length>70){
			Reporter.log("(WARNING: "+pageTitleValidation().toCharArray().length+" characters in Title...Google will cut off at 70)");
		}
		Reporter.log("Meta Description: "+metaDescriptionValidation());
		if(metaDescriptionValidation().toCharArray().length>150){
			Reporter.log("(WARNING: "+metaDescriptionValidation().toCharArray().length+" characters in Meta Desc...Google will cut off at 150)");
		}
		Reporter.log("Omni Tag: "+omnitagValidation());
		
	}
	
	/************************************Layout Testing
	 * @return *********************************************/
	
	 public List<ValidationError> checkPageLayout(WebDriver driver, String pageSpecPath, List<String> tagsToInclude, List<String> tagsToExclude) throws IOException{ 
	        Browser browser = new SeleniumBrowser(driver);
	        Page page = new SeleniumPage(driver);
	        PageSpecReader pageSpecReader = new PageSpecReader(browser);
	        PageSpec spec = pageSpecReader.read(new File(pageSpecPath));
	        SectionFilter sectionFilter = new SectionFilter(tagsToInclude, tagsToExclude);
	        List<PageSection> pageSections = spec.findSections(tagsToInclude);
	        
	        // allows to subscribe for all validation events. Could also be null
	        ValidationListener validationListener = null;
	        SectionValidation sectionValidation = new SectionValidation(pageSections, new PageValidation(browser, page, spec, validationListener, sectionFilter), validationListener);
	        
	        List<ValidationError> errors = sectionValidation.check();
	        if (errors != null) {
	            //TODO do something with errors
	        	for(ValidationError error:errors){
	        		System.out.println(error.toString());
	        		Reporter.log(error.toString());
	        	}
	        }
	         return errors;
	    }
	
	 
	 
	 
	/*************************************Screenshot Methods****************************************/

	
	public void searchBoxLayoutTesting(String keyword){
		
		expr = prop.getValue("searchBox_Id");
		Message = "Search Icon Not Found";
		
		WebElement search = getElement_Id(expr, Message);
		search.click();
		sleep(1000);
		search.sendKeys(keyword);
		sleep(1000);
		try {
			ScreenshotUtil.takeScreenshotAndSave(CommonTest.SEARCH_FOLDER_NAME, keyword);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String searchFieldCopy(){
		
		expr = prop.getValue("searchBox_Id");
		Message = "Search Icon Not Found";
		
		WebElement search = getElement_Id(expr, Message);
		search.click();
		sleep(1000);
		return search.getAttribute("placeholder");
	}
	
	
	public void takePageScreenshots(String filename){
		
		try {
			ScreenshotUtil.takeScreenshotAndSave("Pages", filename);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public String getOmnitureDebuggerPage(String pageUrl, String key ) {
		
		WebElement value = null;
		
		WebElement link = waitOnXPath("//li[@id='gn-mac']/a");
		link.click();
		sleep(2000);
		String currentWindowHandle = driver.getWindowHandle();
		// Execute the Javascript to get the Omniture Debugger on the page

		executeJavascript("window.open('','dp_debugger',"+
			    "'width=600,height=600,location=0,menubar=0,status=1,toolbar=0,resizable=1,scrollbars=1')"+
			    ".document.write('<script language=\"JavaScript\" id=dbg src=\"https://www.adobetag.com/d1/digitalpulsedebugger/live/DPD.js\">"+"</script>');");
		// Get the number of windows available. If more than one window is present, switch to the new 
		// window
		Set<String> windowHandles = driver.getWindowHandles();
		HashMap<String, String> keyvaluepair = new HashMap<String,String> ();
		for(String windowHandle:windowHandles) {
			// Switch to the window having the javascript
			if(windowHandle.equals(currentWindowHandle) == false ) {
				// Switch to the window opened by Javascript
				driver.switchTo().window(windowHandle);
				//
				List<WebElement> listOfkeyvaluepairs = driver.findElements(By.xpath("//table[@class='debugtable']/tbody/tr/td[1]"));
				List<WebElement> listvalues = driver.findElements(By.xpath("//table[@class='debugtable']/tbody/tr/td[2]"));
				Assert.assertNotNull(listOfkeyvaluepairs,"Omniture debugger pairs not found on the page");
				//String siteCatalystBeacons = listOfkeyvaluepairs.getText();
				int i=1;
				//for(int i=1;i<listOfkeyvaluepairs.size();i++){
					for(WebElement list:listOfkeyvaluepairs){
						if(list.getText().equals(key)){
							System.out.println(list.getText());
							value = waitOnXPath("//table[@class='debugtable']/tbody/tr["+i+"]/td[2]");
							value.getText();
					}
						i++;
				}
			
				driver.close();
				driver.switchTo().window(currentWindowHandle);
			//}
			}
			
		}
			
	return 	value.getText();
	}
	
	
	/*************************************Video Tests************************************************/
	
	public void videoTesting(){
		
		
		executeJavascript("");
		
		
		
		
		/*List<WebElement> videos = driver.findElements(By.xpath("//span[@class='play']"));
		
		for(WebElement video:videos){
			video.click();
			sleep(5000);
			
			WebElement pause = waitOnCSS(".pause");
			Assert.assertNotNull(pause, "Pause button not found");
			pause.click();
			System.out.println("Pause Button: "+pause.getAttribute("style"));
			Assert.assertTrue(pause.getAttribute("style").contains("hidden"),"Video not paused on clicking pause");
			sleep(5000);
			
			WebElement play = waitOnCSS(".play");
			Assert.assertNotNull(play, "Play button not found");
			play.click();
			System.out.println("Play Button: "+play.getAttribute("style"));
			Assert.assertTrue(play.getAttribute("style").contains("hidden"),"Video did not start playing");
			System.out.println("Pause Button: "+pause.getAttribute("style"));
			Assert.assertTrue(pause.getAttribute("style").contains("visible"),"Video still paused on clicking play");
			sleep(5000);
			
			WebElement ff = waitOnCSS(".fastForward");
			Assert.assertNotNull(ff, "FF button not found");
			ff.click();
			System.out.println("FF Button: "+ff.getAttribute("class"));
			Assert.assertTrue(ff.getAttribute("class").contains("fastForward-active"), "Fast Forward not Active");
			sleep(5000);
			play.click();
			System.out.println("Play Button: "+play.getAttribute("style"));
			System.out.println("Pause Button: "+pause.getAttribute("style"));
			sleep(3000);
			
			WebElement fb = waitOnCSS(".fastBackward");
			Assert.assertNotNull(fb, "Play button not found");
			fb.click();
			System.out.println("FB Button: "+fb.getAttribute("class"));
			Assert.assertTrue(fb.getAttribute("class").contains("fastBackward-active"),"Fast backward not Active");
			sleep(5000);
			play.click();
			System.out.println("Play Button: "+play.getAttribute("style"));
			System.out.println("Pause Button: "+pause.getAttribute("style"));
			sleep(3000);
			
			WebElement mute = waitOnCSS(".volumeMute");
			Assert.assertNotNull(mute, "Mute button not found");
			mute.click();
			System.out.println("Mute button: " +driver.findElement(By.cssSelector(".volumeTrackProgress")).getAttribute("style"));
			Assert.assertTrue(driver.findElement(By.cssSelector(".volumeTrackProgress")).getAttribute("style").contains("0px"));
			sleep(1000);
			
			WebElement full = waitOnCSS(".volumeFull");
			Assert.assertNotNull(full, "Volume button not found");
			full.click();
			System.out.println("Full volume: "+driver.findElement(By.cssSelector(".volumeTrackProgress")).getAttribute("style"));
			Assert.assertTrue(driver.findElement(By.cssSelector(".volumeTrackProgress")).getAttribute("style").contains("38px"));
			sleep(1000);
			
			WebElement cc = waitOnCSS(".captionsControl");
			Assert.assertNotNull(cc, "CC button not found");
			cc.click();
			System.out.println("Captions: "+cc.getAttribute("aria-checked"));
			System.out.println("Play Button: "+play.getAttribute("style"));
			System.out.println("Pause Button: "+pause.getAttribute("style"));
			Assert.assertTrue(cc.getAttribute("aria-checked").contains("true"));
			sleep(5000);
			
			WebElement close = waitOnCSS(".close");
			Assert.assertNotNull(close, "Close button not found");
			close.click();
			sleep(3000);
		}*/
	}

}


