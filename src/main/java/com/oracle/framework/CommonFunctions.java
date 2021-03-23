package com.oracle.framework;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


public class CommonFunctions {
	private WebDriver driver;	
	static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public CommonFunctions(WebDriver driver) {
		this.driver = driver;
	}

	public void launchApplication() {
		String url = ConfigData.APPURL;
		driver.get(url);
		log.info("Google URL Launched");
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Get the Load Event End
		long loadEventEnd = (Long) js.executeScript("return window.performance.timing.loadEventEnd;");

		// Get the Navigation Event Start
		long navigationStart = (Long) js.executeScript("return window.performance.timing.navigationStart;");

		// Difference between Load Event End and Navigation Event Start is Page Load
		// Time
		System.out.println("Page Load Time is " + (loadEventEnd - navigationStart) / 1000 + " seconds.");
	}

	public String currentTimeVariable() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		LocalDateTime ldt = LocalDateTime.now();
		log.info(dtf.format(ldt));
		return dtf.format(ldt);
	}
	
	public static int getRandowRowNumber(int totalRowCount)
	{
		Random rand = new Random();
		int  n = rand.nextInt(totalRowCount);
		
		if(n==0)
			return n+1;
		else
			return n;
	}
	public void ReadFileAndDisplayInConsole(File file) {
		try {
			java.util.List<String> lines = FileUtils.readLines(file);
			for (String l : lines) {
				
				System.out.println(l.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
