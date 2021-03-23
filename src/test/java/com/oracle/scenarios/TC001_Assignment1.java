package com.oracle.scenarios;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.oracle.framework.BaseClass;
import com.oracle.framework.DataProvider;
import com.oracle.pages.*;
import com.oracle.framework.CommonFunctions;
import com.relevantcodes.extentreports.LogStatus;

import static com.oracle.framework.CommonConstant.*;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 
 * Assignment 1 :
Open google.com and enter any keyword
Retrieve the top 10 results
Store it in a file
Repeat this operation for any other 5 keywords
Read the values from the file and display it in the console
 * 
 **/

public class TC001_Assignment1 extends BaseClass {
	private GoogleSearchResultPage landingPage;	
	private CommonFunctions commonFunctions;
	private HomePage oraHomePage;
	
	static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	DataProvider dataproviders = new DataProvider(excelAssignments);
	int rowCnt = dataproviders.getRowCount(sheetKeywords);
	int colcnt = dataproviders.getColCount(sheetKeywords);
	int Rownum;
	static File file = new File(System.getProperty("user.dir")+ "\\Output.txt");

	@Override
	@BeforeClass
	public void setup() {
		super.setup();
		log.info("Setup Done");
		landingPage = new GoogleSearchResultPage(driver);
		commonFunctions = new CommonFunctions(driver);
		oraHomePage = new HomePage(driver);
	}

	@Test
	(retryAnalyzer = com.oracle.utility.Re_executeFailedTest.class)
	public void Assignment1() throws Throwable {
		for (Rownum = 1; Rownum <= rowCnt; Rownum++) {
			String keyword = dataproviders.readDataFromExcel(sheetKeywords, "Keywords", Rownum);
			
			SearchandUpdateFile(keyword);
			
			
		}
	}

	public void SearchandUpdateFile(String keyword) throws Throwable {
	try {
		
		Thread.sleep(8000);
		//log.info("Browser launched succesfully");
		extentTest.log(LogStatus.PASS, "User able to launch google.com");
		System.out.println(keyword);
		oraHomePage.setSearchValue(keyword);
		// Store results in a file
		int counter = 1;
		
		// Retrieve top 10 results
		List<WebElement> list = driver.findElements(By.xpath("//div[@class='yuRUbf']/a/h3"));
		//System.out.println(list.size());
		for (int i = 0; i <= list.size() - 1; i++) {
			if (!list.get(i).getText().isEmpty()) {
				//System.out.println(counter + list.get(i).getText());
				String searchresults = counter + list.get(i).getText();
				// FileUtils.writeStringToFile(file,searchresults);
				
				writeToFileApacheCommonIO(file, searchresults);
				writeToFileApacheCommonIO(file, System.lineSeparator());
				counter++;
			}

		}
		//System.out.println(counter);
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='yuRUbf']/a/h3")));
		
		if (counter < 10) {
			driver.findElement(By.xpath("//span[text()='Next']")).click();
			
			Thread.sleep(5000);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='yuRUbf']/a/h3")));
			List<WebElement> newlist = driver.findElements(By.xpath("//div[@class='yuRUbf']/a/h3"));
			for (int i = 0; i <= newlist.size() - 1; i++) {
				if (!newlist.get(i).getText().isEmpty() && counter < 11) {
					//System.out.println(counter + newlist.get(i).getText());
					String searchresults = counter + newlist.get(i).getText();
					
					writeToFileApacheCommonIO(file, searchresults);
					writeToFileApacheCommonIO(file, System.lineSeparator());
					counter++;
				}
			}
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}

			
			

	

		
		/*dataproviders.writeDataToExcel(sheetKeywords, "LinkName", Rownum, srNumber);
		
		*/

	}



	@Override
	@AfterClass
	public void tearDown() throws IOException {
		super.tearDown();
		
	}



public void writeToFileApacheCommonIO(File file, String msg) {
	try {
		// 3rd parameter boolean append = true
		FileUtils.writeStringToFile(file, msg, true);
	} catch (IOException e) {
		e.printStackTrace();
	}
}

// This function will handle staleelement reference exception
public void handleStaleElement(String elementxpath) {
	int count = 0;
	// It will try 4 times to find same element using name.
	while (count < 4) {
		try {
			// If exception generated that means It Is not able to find
			// element then catch block will handle It.
			WebElement staledElement = driver.findElement(By.xpath(elementxpath));
			// If exception not generated that means element found and
			// element text get cleared.
			staledElement.click();
		} catch (StaleElementReferenceException e) {
			e.toString();
			System.out.println("Trying to recover from a stale element :" + e.getMessage());
			count = count + 1;
		}
		count = count + 4;
	}
}
}

