/**
 * 
 */
package com.apple.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author rashi_chopra
 *
 */
public class LogFileWriter {

	/**
	 * @throws IOException 
	 * 
	 */
	 public static void log(String message) { 
		 
		 try{

	            FileWriter fstream = new FileWriter("target/output.txt",true);
	            BufferedWriter fbw = new BufferedWriter(fstream);
	            fbw.write(message);
	            fbw.newLine();
	            fbw.close();
	        }catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	
	 }
	 
}

