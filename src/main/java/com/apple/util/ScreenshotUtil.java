/**
 * 
 */
package com.apple.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.apple.Common.CommonActions;
import com.apple.Common.CommonTest;

/**
 * @author rashi_chopra
 *
 */
public class ScreenshotUtil extends CommonTest{

	PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	private static String screenShotFile;
//	protected static Properties props;
	
	
	/******
	 * takeScreenshotAndSave: 
	 * @param folder
	 * @param keyword
	 * @throws InterruptedException 
	 */
		public static void takeScreenshotAndSave(String folder, String keyword) throws InterruptedException{
	
			String workingDir = System.getProperty("user.dir");
			screenShotFile = workingDir + File.separator
					+ CommonTest.TEST_RESULTS_OUTPUT_FOLDER
					+ File.separator + folder
					+ File.separator+folder+"_"+keyword+"_"+props.getProperty("BROWSER")
					+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()))
					+ CommonTest.SCREENSHOT_IMG_FORMAT;
		
			try {
					FileUtils.copyFile(CommonActions.ScreenShotTaker(), new File(screenShotFile));
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 Reporter.log("Screenshot taken and Saved at: " +screenShotFile);
		 Reporter.setEscapeHtml(false);
		 //Reporter.log("<a href=\'"+ screenShotFile+ "\'>Click For Screenshot</a>");
	}
		
		
		/*******
		 * makeFullScreenshot: 
		 * @param driver
		 * @return
		 * @throws IOException
		 * @throws InterruptedException
		 */
		 public static String makeFullScreenshot(WebDriver driver) throws IOException, InterruptedException {
		        byte[] bytes = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
		        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
		        int capturedWidth = image.getWidth();
		        int capturedHeight = image.getHeight();
		        
		        long longScrollHeight = (Long)((JavascriptExecutor)driver).executeScript("return Math.max(" +
		                "document.body.scrollHeight, document.documentElement.scrollHeight," +
		                "document.body.offsetHeight, document.documentElement.offsetHeight," +
		                "document.body.clientHeight, document.documentElement.clientHeight);"
		            );
		        
		        int scrollHeight = (int)longScrollHeight;
		        
		        File file = File.createTempFile("screenshot", ".png");
		        
		        if (Math.abs(capturedHeight - scrollHeight) > 40) {
		            int scrollOffset = capturedHeight;
		            
		            int times = scrollHeight / capturedHeight;
		            int leftover = scrollHeight % capturedHeight;
		            
		            final BufferedImage tiledImage = new BufferedImage(capturedWidth, scrollHeight, BufferedImage.TYPE_INT_RGB);
		            Graphics2D g2dTile = tiledImage.createGraphics();
		            g2dTile.drawImage(image, 0,0, null);

		            
		            int scroll = 0;
		            for (int i = 0; i < times - 1; i++) {
		                scroll += scrollOffset;
		                scrollVerticallyTo(driver, scroll);
		                Thread.sleep(100);
		                BufferedImage nextImage = ImageIO.read(new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
		                g2dTile.drawImage(nextImage, 0, (i+1) * capturedHeight, null);
		            }
		            if (leftover > 0) {
		                scroll += scrollOffset;
		                scrollVerticallyTo(driver, scroll);
		                BufferedImage nextImage = ImageIO.read(new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
		                BufferedImage lastPart = nextImage.getSubimage(0, nextImage.getHeight() - leftover, nextImage.getWidth(), leftover);
		                g2dTile.drawImage(lastPart, 0, scrollHeight - leftover, null);
		            }
		            
		            scrollVerticallyTo(driver, 0);
		            
		            ImageIO.write(tiledImage, "png", file);
		        }
		        else {
		            ImageIO.write(image, "png", file);
		        }
		        return file.getAbsolutePath();
		    }

		    public static void scrollVerticallyTo(WebDriver driver, int scroll) {
		        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0, " + scroll + ");");
		    }
}
