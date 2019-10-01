package com.apple.listeners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import org.apache.commons.logging.Log;
import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.apple.Common.CommonActions;
import com.apple.Common.CommonModules;
import com.apple.Common.CommonTest;
import com.apple.util.ExcelReader;
import com.apple.util.PropertyFileReader;

public class IOSTests extends CommonTest{
	
	PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	String actual_url;
	private static Log logger = getLog(IOSTests.class);
	

	@Test(dataProvider = "allMyDevices")
    public void homePage(Dimension screenSize, List<String> tags, List<String> extags) throws IOException {
       // WebDriver driver = createDriver("http://ic10.apple.com", screenSize);
		 loadPage(driver, BaseURL);
        System.out.println("Running Tests for Screen size: "+screenSize);
        checkPageLayout(driver, "galenspecfiles/globalNav.spec", tags, extags);
    }
    
	 public void checkPageLayout(WebDriver driver, String pageSpecPath, List<String> tagsToInclude, List<String> tagsToExclude) throws IOException{ 
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
	        	}
	        }
	    
	    }
	    
	    public void configureValidationListener() {
	        //TODO 
	    	
	    }
	    
	    public WebDriver createDriver(String url, Dimension screenSize) {
	        driver.get(url);
	        driver.manage().window().setSize(screenSize);
	        return driver;
	    }
   
   
    
    @DataProvider
    public Object[][] allMyDevices() {
        return new Object[][]{
         // {size(400, 800), asList("mobile"), asList("tablet","desktop", "all")},
          //{size(600, 800), asList("tablet"), asList("mobile", "desktop", "all")},
          {size(1024, 768), asList("desktop", "all"), asList("mobile","tablet")},
        };
    }
    
    public static Dimension size(int w, int h) {
        return new Dimension(w, h);
    }
	 
	public List<String> asList(String...args){
		List<String> tags = new ArrayList<String>();
		for(int i=0;i<args.length;i++){
		tags.add(args[i]);
		}
		return tags;
	}
	
  }

