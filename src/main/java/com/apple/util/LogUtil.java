/**
 * 
 */
package com.apple.util;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author rashi_chopra
 *
 */

public class LogUtil {

	static PropertyFileReader prop = new PropertyFileReader("resources/locators.properties");
	static String timeStamp = null;
    static String logFileName = null;
   
    
	/*
	 * Method for log4j implementation, here the input parameter is the class name
	 * It reads the log4j.xml and prints logging information accordingly.
	 */
	/* Usage is as follows:
	 * Add this line in your class:
	 * private static Log logger = getLog(<class name>);
	 */
	public static Log getLog (Class<?> className, String fileName) {
		Log logger = LogFactory.getLog(className);
		timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        logFileName = prop.getValue("PROJECT_NAME")+"_"+timeStamp;       
        System.setProperty("logfilename", logFileName);
		File myFile = new File(fileName);
		//System.out.println("The Log4j Path is: " + myFile.getAbsolutePath());
		DOMConfigurator.configure(myFile.getAbsolutePath());
		return logger;
	}
	
	/**
	 * This will create the log with the default file name log4j.xml
	 * @param className
	 * @return
	 */
	public static Log getLog (Class<?> className) {
		return getLog(className, "log4j.xml");
	}
	/*
	 *Method for log4j formatting of the message
	 *
	 */
	public String printLogMessage(String message){
		return "\n******************" + message + "******************\n";
	}
  
}
