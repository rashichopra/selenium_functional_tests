/**
 * 
 */
package com.apple.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author rashi_chopra
 *
 */
public class PropertyFileReader {

	Properties prop = new Properties();
	
	public PropertyFileReader(String filename) {
		try {
			File pageElementFile = new File(filename);
			FileReader pageElementPropFile = new FileReader(
					pageElementFile.getAbsolutePath());
			prop.load(pageElementPropFile);
		} catch (IOException iex) {
			System.out.println("Unable to load the property file "
					+ iex.getMessage());

		}
	}
	
	public String getValue(String key) {
		
	return	prop.getProperty(key);

	}

}
