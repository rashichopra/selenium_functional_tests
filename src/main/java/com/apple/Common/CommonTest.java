/**
 * 
 */
package com.apple.Common;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.sikuli.api.robot.Key;
import org.sikuli.webdriver.SikuliFirefoxDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.zeroturnaround.zip.ZipUtil;

import com.apple.util.Decryption;
import com.apple.util.ExcelReader;
import com.apple.util.LogUtil;
import com.apple.util.PropertyFileReader;
import com.apple.util.ScreenshotUtil;


/**
 * @author rashi_chopra
 *
 */
public class CommonTest {

	
	public static WebDriver driver;
	
	PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	PropertyFileReader urllistfile = new PropertyFileReader("resources/URL_List");
	protected static Properties props;
	protected static String BaseURL = null;
	protected static String configFile = null;
	String[] dialog;
	
	public static int MaxWaitSeconds = 30;
	public static final String SCREENSHOTS_FOLDER_NAME = "screenshots";
	public static final String SEARCH_FOLDER_NAME = "search";
	// public static final String TEST_RESULTS_OUTPUT_FOLDER = "test-output";
	public static final String TEST_RESULTS_OUTPUT_FOLDER = "target";
	public static final String SCREENSHOT_IMG_FORMAT = ".png";

	public final String DATAFILE_NAME = "resources/DataFile.xls";
	public final String SHEET_NAME_NAV = "Navigation";
	public final String SHEET_NAME_Project = "Project";
	public final String SHEET_NAME_BC = "BreadCrumb";
	public final String SHEET_NAME_PN = "ProductNav";
	public final String SHEET_NAME_DN = "DirectoryNav";
	

	/**
	 * This method deletes the screenshots folder if it is present
	 * in a property's test-output folder
	 */
	@BeforeSuite(alwaysRun = true)
	public void clearErrorScreenShotFolder() {
		//clearScreenShotFolder();
	}
	
	@BeforeSuite(alwaysRun=true)
	public void beforeSuite(ITestContext configParameters) {
		
		System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
		System.setProperty("org.uncommons.reportng.escape-output", "false"); //This Property is for escaping the HTML to get Screenshot as a link in the index.html report
		System.setProperty("mail.smtp.auth", "true");
		 Reporter.setEscapeHtml(false);
		//System.setProperty("webdriver.ie.driver", "/Users/rashi_chopra/Documents/SeleniumDriver/IEDriverServer");
		
			configFile = configParameters.getCurrentXmlTest().getParameter("configFileName");
			readConfig(configFile);
			
		/*	if(CommonTest.isIE()&&BaseURL.contains("ic")){
			dialog = new String[]{"resources/Authenticate.exe", "Windows Security", "rashi_chopra",Decryption.decrypt("DGtAhouo8WQx8nXPBv1pjn4/R0cu6jUL")};
			try {
				Process ppl = Runtime.getRuntime().exec(dialog);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
			else if(CommonTest.isFF()&&BaseURL.contains("ic")){
				String[] cmd = { "osascript", "resources/FFAuth.scpt", "rashi_chopra", Decryption.decrypt("DGtAhouo8WQx8nXPBv1pjn4/R0cu6jUL")};
				try {
					Runtime.getRuntime().exec(cmd);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
			/*dialog = new String[]{"resources/Authenticate.exe", "Authentication Required", "rashi_chopra",Decryption.decrypt("DGtAhouo8WQx8nXPBv1pjn4/R0cu6jUL")};
			
			try {
				Process ppl = Runtime.getRuntime().exec(dialog);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			//}
			/*else if(CommonTest.isChrome()&&BaseURL.contains("ic")){
			String[] cmd = { "osascript", "resources/ChromeAuth.scpt", "rashi_chopra", Decryption.decrypt("DGtAhouo8WQx8nXPBv1pjn4/R0cu6jUL")};
			try {
				Runtime.getRuntime().exec(cmd);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}*/
		
	}

	/*@BeforeTest(alwaysRun=true)
	public void setUp() throws Exception {
		//			  FirefoxProfile profile = new FirefoxProfile();
		//			  profile.updateUserPrefs(arg0);
		driver = new FirefoxDriver();
		//driver = new SafariDriver();

	}*/

	@AfterSuite(alwaysRun=true)
	public void tearDown() throws Exception {
		/*final List<JavaScriptError> jsErrors = JavaScriptError.readErrors(driver);
		System.out.println("###start displaying errors"); 
		for(int i = 0; i < jsErrors.size(); i++) {
		 System.out.println(jsErrors.get(i).getErrorMessage()); 
		System.out.println(jsErrors.get(i).getLineNumber());
		 System.out.println(jsErrors.get(i).getSourceName());
		}
		System.out.println("###stop displaying errors");*/
		//Assert.assertTrue(jsErrors.isEmpty(),"JS errors occured: " + jsErrors);
		driver.quit();
		
	}
	
	/*@AfterSuite(alwaysRun=true)
	public void screenshot(){
		ZipUtil.pack(new File("test-output/html"), new File("target/Test-Results-"+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()))+".zip"));
		//FileUtils.copyFile(new File("test-output/html"), new File("target/Test-Results-"+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()))));
		
	}*/

	public void loadPage(WebDriver driver, String url) {
		
		if(BaseURL.contains("ic")&&!(props.getProperty("BROWSER").toUpperCase().contains("SAFARI"))){
			String new_url = url.replace("ic", Decryption.decrypt("56gquR3OZ4mKFafCE97tr1jihJiJOytnZk/xS/HHc7M=")+"@ic");
			 driver.get(new_url);
		}
		
		driver.get(url);
		
		//Actions builder = new Actions(driver);
		/*builder.keyDown(Keys.TAB);
		builder.keyUp(Keys.TAB);
		builder.keyDown(Keys.TAB);*/
		//builder.sendKeys(Keys.TAB);
		CommonActions.sleep(3000);
		//((JavascriptExecutor) driver).executeScript("window.focus();");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		/*driver.switchTo().activeElement();
		driver.close();
		driver.switchTo().defaultContent();*/
		  driver.manage().window().maximize();     

	}

	
	public void navigateBack(){
		if(!isSafari()){
		driver.navigate().back();
		}
		else{
			((JavascriptExecutor)driver).executeScript("history.go(-1)");
		}
	}

	public void verifyDestinationURL(String actual_url, String url){

		if(url.equals("NA")){

			Assert.assertEquals(actual_url,BaseURL);
		}
		else{
			Assert.assertEquals(actual_url,BaseURL+url);
		}
		navigateBack();
	}

	public void verifyDestinationURLNoBack(String actual_url, String url, String Message){
		
		if(url.contains("NA")){

			Assert.assertEquals(actual_url,BaseURL,Message);
		}
		else{
			Assert.assertEquals(actual_url,url,Message);
		}
	
	}

	/**
	 * Clears Screen shot folder before execution of a test suite
	 */
	public void clearScreenShotFolder() {
		// Deletes all files and subdirectories under dir.
		// Returns true if all deletions were successful.
		// If a deletion fails, the method stops attempting to delete and
		// returns false.

		String workingDir = System.getProperty("user.dir");

		String screenShotFolder = workingDir + File.separator
				+ TEST_RESULTS_OUTPUT_FOLDER + File.separator
				+ SCREENSHOTS_FOLDER_NAME;
		System.out.println("Folder to be cleared is " + screenShotFolder);
		File dir = new File(screenShotFolder);
		if (dir.exists()) {
			try {
				delete(dir);
			} catch (IOException iex) {
				System.out.println("Error seen on deleting a directory "
						+ iex.getMessage());
				iex.printStackTrace();
			}
		} else
			System.out
			.println("Screen Shot director not present in test-output, no action needed");

	}

	/**
	 * Deletes a directory. If directory has files, it will delete all files and
	 * then delete the drectory for empty directories it deletes directly
	 * 
	 * @param file
	 * @throws IOException
	 */
	private static void delete(File file) throws IOException {
		System.out.println("File Exists " + file.exists() + " file Directory "
				+ file.isDirectory());
		if (file.exists() && file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				file.delete();
				System.out.println("Directory is deleted : "
						+ file.getAbsolutePath());

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					fileDelete.delete();
					// delete(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					System.out.println("Directory is deleted : "
							+ file.getAbsolutePath());
				}
			}

		}

	}


	/*
	 * Method for log4j implementation, here the input parameter is the class name
	 * It reads the log4j.xml and prints logging information accordingly.
	 */
	public static Log getLog (Class<?> string) {
		return LogUtil.getLog(string, "log4j.xml");
	}


	public static void readConfig(String configFile) {
		try {
			props = new Properties();
			File myFile = new File(configFile);
			System.out.println(myFile.getAbsolutePath()
					+ " config file name:  " + configFile);
			FileReader configfile = new FileReader(myFile.getAbsolutePath());
			props.load(configfile);

			BaseURL = props.getProperty("BASE_URL");
			
			MaxWaitSeconds = Integer.parseInt(props
					.getProperty("MAX_WAIT_TIME_SECONDS"));

			if (props.getProperty("REMOTE").equalsIgnoreCase("No")
					|| props.getProperty("REMOTE") == "") {
				if (props.getProperty("BROWSER").equalsIgnoreCase("FF")) {
					FirefoxProfile profile = null;
					if (props.getProperty("newFirefoxProfilePath") != null) {
						profile = new FirefoxProfile(new File(
								props.getProperty("newFirefoxProfilePath")));
					} else {
						profile = new FirefoxProfile();
						//JavaScriptError.addExtension(profile);
					}
					profile.setEnableNativeEvents(false);
					driver = new FirefoxDriver(profile);
				}

				else if (props.getProperty("BROWSER").toUpperCase().contains("IE")) {
					driver = new InternetExplorerDriver();
				} 
				
				else if (props.getProperty("BROWSER").toUpperCase().contains("CHROME")) {
					
					driver = new ChromeDriver();
					
				}
				
				else if (props.getProperty("BROWSER").toUpperCase().contains("SAFARI")) {
					driver = new SafariDriver();
				}
			} 
			else {
				// We could use any driver for our tests...
				// defalut to htmlunit if not selected
				DesiredCapabilities capabilities = null;

				if (props.getProperty("BROWSER").equalsIgnoreCase("FF")) {
					FirefoxProfile profile = null;
					if (props.getProperty("newFirefoxProfilePath") != null) {
						profile = new FirefoxProfile(new File(
								props.getProperty("newFirefoxProfilePath")));
					} else {
						profile = new FirefoxProfile();
					}
					profile.setEnableNativeEvents(false);
					capabilities = new DesiredCapabilities();
					capabilities.setBrowserName("firefox");
					capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
					capabilities.setCapability(FirefoxDriver.PROFILE, profile);

				} else if (props.getProperty("BROWSER").equalsIgnoreCase("IE7")) {
					capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setBrowserName("internet explorer");
					capabilities.setVersion("7");
					capabilities
							.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				//	capabilities.setCapability("useLegacyInternalServer", true);

				} else if (props.getProperty("BROWSER").equalsIgnoreCase("IE8")) {
					capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setBrowserName("internet explorer");
					capabilities.setVersion("8");
					capabilities
							.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				}

				else if (props.getProperty("BROWSER").equalsIgnoreCase("IE9")) {
					capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setBrowserName("internet explorer");
					capabilities.setVersion("9");
					capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				} 
				else if (props.getProperty("BROWSER").equalsIgnoreCase("IE10")) {
					capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setBrowserName("internet explorer");
					capabilities.setVersion("10");
					capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				} 
				else if (props.getProperty("BROWSER").equalsIgnoreCase("IE")) {
					capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setBrowserName("internet explorer");
					capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				} 
				else if (props.getProperty("BROWSER").equalsIgnoreCase("CHROME")) {
					capabilities = DesiredCapabilities.chrome();
					capabilities.setBrowserName("chrome");
					capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
					
				}
				else if (props.getProperty("BROWSER").equalsIgnoreCase("SAFARI")) {
					capabilities = DesiredCapabilities.safari();
					capabilities.setBrowserName("safari");
					capabilities.setPlatform(org.openqa.selenium.Platform.MAC);
					
				
				}
				else {
					capabilities = DesiredCapabilities.htmlUnit();
				}

				String hub = props.getProperty("HUB");
				String port = props.getProperty("PORT");

				// defalut hub to localhost
				if (hub == null || hub.isEmpty()) {
					hub = "localhost";
				}

				// default port to 4444
				if (port == null || port.isEmpty()) {
					port = "4444";
				}

				// add proxy setting to the webdriver
				String proxyString = props.getProperty("PROXY");
				String noProxyString = props.getProperty("NOPROXY");
				if (proxyString != null && !proxyString.isEmpty()) {
					Proxy proxysetting = new Proxy();
					proxysetting.setHttpProxy(proxyString)
							.setFtpProxy(proxyString).setSslProxy(proxyString);
					if (noProxyString != null && !proxyString.isEmpty()) {
						proxysetting.setNoProxy(noProxyString);
					}

					capabilities.setCapability(CapabilityType.PROXY,
							proxysetting);
				}

				String hubLocation = "http://" + hub + ":" + port + "/wd/hub";

				// Get a handle to the driver. This will throw an exception
				// if a matching driver cannot be located
				driver = new RemoteWebDriver(new URL(hubLocation), capabilities);

			}

		} catch (Exception ex) {
			System.out.println("Unable to load the config file: "
					+ ex.getMessage());
			// ex.printStackTrace();
		}
	}
	
	
	public String[] readFile(String list){
		
		String str = null;
		String[] arr = null;
		
		str = urllistfile.getValue(list);
		arr = str.split(",");
		/*try {
			BufferedReader br = new BufferedReader(new FileReader(new File(file)));
			 str = br.readLine();
			 arr = str.split(",");
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return arr;
	}
	
	
	
	/**
	 * check the current test is running on IE or non-IE browsers
	 * 
	 * @return return true if it's a IE browser test, else it will return false
	 */
	public static boolean isIE() {
		return props.getProperty("BROWSER").toUpperCase().contains("IE");
	}

	/**
	 * check the current test is running on IE or non-IE browsers
	 * 
	 * @return return true if it's a IE browser test, else it will return false
	 */
	public static boolean isIE8() {
		return props.getProperty("BROWSER").toUpperCase().contains("IE8");
	}

	/**
	 * check the current test is running on IE or non-IE browsers
	 * 
	 * @return return true if it's a IE browser test, else it will return false
	 */
	public static boolean isIE9() {
		return props.getProperty("BROWSER").toUpperCase().contains("IE9");
	}

	/**
	 * check the current test is running on IE or non-IE browsers
	 * 
	 * @return return true if it's a IE browser test, else it will return false
	 */
	public static boolean isFF() {
		return props.getProperty("BROWSER").toUpperCase().contains("FF");
	}

	/**
	 * check the current test is running on IE or non-IE browsers
	 * 
	 * @return return true if it's a IE browser test, else it will return false
	 */
	public static boolean isChrome() {
		return props.getProperty("BROWSER").toUpperCase().contains("CHROME");
	}

	/**
	 * check the current test is running on Safari or non-Safari browsers
	 * 
	 * @return return true if it's a Safari browser test, else it will return false
	 */
	public static boolean isSafari() {
		return props.getProperty("BROWSER").toUpperCase().contains("SAFARI");
	}
	
	
	//DataProviders

	@DataProvider(name = "apple_globalNav")
	public Object[][] globalNavLinks() {
		Object[][] testData= ExcelReader.readExcelData(DATAFILE_NAME,SHEET_NAME_NAV,"globalNav");
		return testData;
	}
	
	@DataProvider(name = "apple_gfLinks")
	public Object[][] apple_gfbuy() {
		Object[][] testData= ExcelReader.readExcelData(DATAFILE_NAME,SHEET_NAME_NAV,"gf_Links");
		return testData;
	}
	
	@DataProvider(name = "iOS_prodNav")
	public Object[][] prodNavLinks() {
		Object[][] testData= ExcelReader.readExcelData(DATAFILE_NAME,SHEET_NAME_PN,"iOS_prodNav");
		return testData;
	}
	
	@DataProvider(name = "iOS_localNav")
	public Object[][] localNavLinks() {
		Object[][] testData= ExcelReader.readExcelData(DATAFILE_NAME,SHEET_NAME_PN,"iOS_localNav");
		return testData;
	}

	@DataProvider(name = "iOS_prodLogo")
	public Object[][] prodNavLogo() {
		Object[][] testData= ExcelReader.readExcelData(DATAFILE_NAME,SHEET_NAME_NAV,"iOS_prodLogo");
		return testData;
	}

	@DataProvider(name = "iOS_dirNav")
	public Object[][] iOS_dirNav() {
		Object[][] testData= ExcelReader.readExcelData(DATAFILE_NAME,SHEET_NAME_DN,"iOS_dirNav");
		return testData;
	}

	@DataProvider(name = "iPhone_dirNav")
	public Object[][] iPhone_dirNav() {
		Object[][] testData= ExcelReader.readExcelData(DATAFILE_NAME,SHEET_NAME_DN,"iPhone_dirNav");
		return testData;
	}

	@DataProvider(name = "bc_firstlevel")
	public Object[][] bc_firstlevel() {
		Object[][] testData= ExcelReader.readExcelData(DATAFILE_NAME,SHEET_NAME_BC,"bc_firstlevel");
		return testData;
	}
	
	@DataProvider(name = "bc_seclevel")
	public Object[][] bc_seclevel() {
		Object[][] testData= ExcelReader.readExcelData(DATAFILE_NAME,SHEET_NAME_BC,"bc_seclevel");
		return testData;
	}
	
	@DataProvider(name = "bc_thirdlevel")
	public Object[][] bc_thirdlevel() {
		Object[][] testData= ExcelReader.readExcelData(DATAFILE_NAME,SHEET_NAME_BC,"bc_thirdlevel");
		return testData;
	}
	
	@DataProvider(name = "promos")
	public Object[][] promos() {
		Object[][] testData= ExcelReader.readExcelData(DATAFILE_NAME,SHEET_NAME_Project,"promos");
		return testData;
	}
	
	@DataProvider(name = "search")
	public Object[][] search() {
		Object[][] testData= ExcelReader.readExcelData(DATAFILE_NAME,SHEET_NAME_Project,"searchstring");
		return testData;
	}
	
	@DataProvider(name = "screensize")
	public Object[][] screensize() {
		Object[][] testData= ExcelReader.readExcelData(DATAFILE_NAME,SHEET_NAME_Project,"screensize");
		return testData;
	}
	
	@DataProvider(name = "geo_pages")
	public Object[][] geo_pages() {
		Object[][] testData= ExcelReader.readExcelData(DATAFILE_NAME,SHEET_NAME_Project,"geo_pages");
		return testData;
	}
	
	
	@DataProvider(name = "fileDataProvider")
	public static Iterator<Object[]> fileDataProvider (ITestContext context) {
		//Get the input file path from the ITestContext
		String inputFile = context.getCurrentXmlTest().getParameter("filenamePath");
		//System.out.println(inputFile);
		//Get a list of String file content (line items) from the test file.
		List<String> testData = getFileContentList(inputFile);

		//We will be returning an iterator of Object arrays so create that first.
		List<Object[]> dataToBeReturned = new ArrayList<Object[]>();

		//Populate our List of Object arrays with the file content.
		for (String userData : testData)
			{
			//System.out.println(userData);
			dataToBeReturned.add(new Object[] { userData } );
			}
		//return the iterator - testng will initialize the test class and calls the
		//test method with each of the content of this iterator.
			return dataToBeReturned.iterator();
			}

		public static List<String> getFileContentList(String filenamePath){
			
			//Sample utility method to get the file content, any version of
			//this can be adapted, this is just one way of achieving the result.
			InputStream is;
			List<String> lines = new ArrayList<String> ();
			try {
				is = new FileInputStream(new File(filenamePath));
				DataInputStream in = new DataInputStream(is);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				while ((strLine = br.readLine()) != null) {
					lines.add(strLine);
				}
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return lines;
			}

		
	public void createDriver(WebDriver driver,String url, Dimension screenSize) {
	        driver.get(url);
	        driver.manage().window().setSize(screenSize);
	    }
    
    @DataProvider(name="allMyDevices")
    public Object[][] allMyDevices() {
        return new Object[][]{
          {size(1367, 670), asList("mobile"), asList("tablet","desktop", "all")},
          {size(1439, 779), asList("tablet"), asList("mobile", "desktop", "all")},
          {size(1539, 879), asList("desktop", "all"), asList("mobile","tablet")},
          {size(1639, 979), asList("desktop", "all"), asList("mobile","tablet")},
          {size(1739, 1079), asList("desktop", "all"), asList("mobile","tablet")},
          {size(1839, 1179), asList("desktop", "all"), asList("mobile","tablet")},
          {size(1939, 1279), asList("desktop", "all"), asList("mobile","tablet")},
          {size(2558, 1319), asList("desktop", "all"), asList("mobile","tablet")},
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


