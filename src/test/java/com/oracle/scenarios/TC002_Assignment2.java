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
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
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

public class TC002_Assignment2 extends BaseClass {
	private GoogleSearchResultPage landingPage;	
	private CommonFunctions commonFunctions;
	private HomePage oraHomePage;
	
	static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	DataProvider dataproviders = new DataProvider(excelAssignments);
	int rowCnt = dataproviders.getRowCount(sheetKeywords);
	int colcnt = dataproviders.getColCount(sheetKeywords);
	int Rownum;
	static File file = new File(System.getProperty("user.dir")+ "\\Output.txt");
	String url = "";
	HttpURLConnection huc = null;
	int respCode = 200;
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
		// Retrieve top 10 results
				List<WebElement> list = driver.findElements(By.xpath("//div[@class='yuRUbf']/a"));
				int counter=1;
				ArrayList al = new ArrayList();
				//System.out.println(list.size());
				for (int i = 0; i <= list.size() - 1; i++) {
					if (!list.get(i).getText().isEmpty()) {
						
						url = list.get(i).getAttribute("href");
						//System.out.println(url);
						al.add(url);
						
					}

				}
				
				for(int i=0;i<=al.size()-1;i++){
					if(counter < 11){
					System.out.println(al.get(i));
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='yuRUbf']/a[@href='"+al.get(i)+"']")));
					driver.findElement(By.xpath("//div[@class='yuRUbf']/a[@href='"+al.get(i)+"']")).click();
					Thread.sleep(15000L);
					if(driver.getPageSource().contains("java")) {
					     System.out.println("Keyword found in "+al.get(i));
					     List<WebElement> occurence =driver.findElements(By.xpath("//*[text()[contains(.,'JAVA')]] | //*[text()[contains(.,'java')]]"));
					     
					     System.out.println("No of times keyword present-"+occurence.size());
					    }
					    else
					    {
					     System.out.println("Keyword not found in "+al.get(i));
					    }
					driver.navigate().back();				   
				    Thread.sleep(10000L);
				    counter++;
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
	public static void verifyURLStatus(String URL) {
		try { 
		URL url = new URL(URL);
		HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
		HttpURLConnection.setFollowRedirects(false); 
		httpURLConnect.setConnectTimeout(3000); 
		httpURLConnect.connect(); 
		switch (httpURLConnect.getResponseCode()) { 
		case 200: 
		   System.out.println(URL + " - " + httpURLConnect.getResponseMessage()); 
		default: 
		   System.out.println(URL + " - " + httpURLConnect.getResponseMessage() + " - " + HttpURLConnection.HTTP_NOT_FOUND); 
		httpURLConnect.disconnect(); 
		  }  
		} catch (Exception e) { 
		System.out.println("Url has Issue" + URL); 
		e.printStackTrace(); 
		}
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

