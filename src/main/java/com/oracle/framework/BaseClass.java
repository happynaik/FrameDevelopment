package com.oracle.framework;

import static com.oracle.framework.PropertiesReader.*;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.oracle.utility.ExtentManager;
import com.oracle.utility.TestCleanup;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseClass {
	protected WebDriver driver;
	public static ExtentReports extentReport;
	public static ExtentTest extentTest;
	public static String result;

	static {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		System.setProperty("current.date.time", dateFormat.format(new Date()));
	}
	static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	@BeforeSuite
	public void beforeSuite() {
		log.info("BASE CLASS BEFORE_SUITE");
		// Extent manager Instance
		extentReport = ExtentManager.Instance();
	}

	@BeforeClass
	public void setup() {
		log.info("BASE CLASS BEFORE_CLASS");

		try {
			WebdriverInitializer webinit = new WebdriverInitializer(ConfigData.BROWSER);
			driver = webinit.getWebDriver();
			log.info(driver);

		} catch (Throwable e) {
			log.info(e);
			//
		}
		String className = this.getClass().getSimpleName();
		String browser = getDataFromProperty("config", "browser");
		extentReport.addSystemInfo("Browser", browser);
		extentTest = extentReport.startTest(className);
		extentTest.assignCategory("Support Portal Regression Test on Browser - " + browser);
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		log.info("BASE CLASS AFTER_METHOD");

		if (result.getStatus() == ITestResult.FAILURE) {
			log.info(result.getMethod().getMethodName() + " -- TEST SCENARIO FAILED");

			// Converting Screenshot to Base 64 Format
			String base64img = "";
			try {
				TakesScreenshot ts = (TakesScreenshot) driver;
				String scnShot = ts.getScreenshotAs(OutputType.BASE64);
				base64img = "data:image/jpg;base64, " + scnShot;
				log.info("Screenshot taken");
			} catch (Throwable e) {

				log.info("Exception while taking screenshot " + e.getMessage());
			}

			String image = extentTest.addBase64ScreenShot(base64img);
			log.info("Screenshot attached");
			extentTest.log(LogStatus.FAIL, result.getMethod().getMethodName() + " -- TEST FAILED", image);
			extentTest.log(LogStatus.FAIL, result.getMethod().getMethodName() + " -- TEST SCENARIO FAILED");

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			log.info(result.getMethod().getMethodName() + " -- TEST SCENARIO PASSED");
			extentTest.log(LogStatus.PASS, result.getMethod().getMethodName() + " -- TEST SCENARIO PASSED");
		} else if (result.getStatus() == ITestResult.SKIP) {
			log.info(result.getMethod().getMethodName() + " -- TEST SCENARIO SKIPPED");

			String base64img = "";
			try {
				TakesScreenshot ts = (TakesScreenshot) driver;
				String scnShot = ts.getScreenshotAs(OutputType.BASE64);
				base64img = "data:image/jpg;base64, " + scnShot;
				log.info("Screenshot taken");
			} catch (Throwable e) {

				log.info("Exception while taking screenshot " + e.getMessage());
			}

			String image = extentTest.addBase64ScreenShot(base64img);
			log.info("Screenshot attached");
			extentTest.log(LogStatus.INFO, result.getMethod().getMethodName() + " -- TEST SKIPPED", image);
			extentTest.log(LogStatus.INFO, result.getMethod().getMethodName() + " -- TEST SCENARIO SKIPPED");
		}
	}

	@AfterClass
	public void tearDown() throws IOException {
		log.info("BASE CLASS AFTER_CLASS");
		extentReport.endTest(extentTest);
		extentReport.flush();
		driver.close();
		driver.quit();
	}

	@AfterSuite
	public void afterSuite() throws Throwable {

		log.info("BASE CLASS AFTER_SUITE");
		extentReport.close();
		log.info("Extent report Closed");
	
		try {
			if(CommonConstant.local.exists())
			{
			FileUtils.copyFileToDirectory(ExtentManager.pjtPath, CommonConstant.local);
			}

		
			log.info(CommonConstant.des);
			if(CommonConstant.des.exists())
			{
				CommonConstant.des.delete();
			}
			FileUtils.copyFile(ExtentManager.pjtPath, CommonConstant.des);
			log.info("Move report file success");
		} catch (Throwable e) {
			log.info(e);
		}

		// Open report in browser
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("disable-infobars");
		options.addArguments("--disable-extensions");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		String url = ExtentManager.Path;
		driver.get(url);
		// TestCleanup.killProcess("chrome");
		//TestCleanup.killProcess("chromedriver");
	}
}
