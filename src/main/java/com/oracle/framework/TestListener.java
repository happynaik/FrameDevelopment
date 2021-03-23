package com.oracle.framework;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestListener implements ITestListener {
	WebDriver driver = null;
	String filepath = "./test-output/screenshots/";
	static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public void onTestSuccess(ITestResult result) {

	}

	@Override
	public void onTestFailure(ITestResult result) {
		takeScreenShot(result.getTestClass().getName() + result.getStartMillis() + result.getThrowable(), result);
	}

	public void takeScreenShot(String methodName, ITestResult result) {
		// get the driver
		if (!new File(filepath).exists()) {
			new File(filepath).mkdirs();
		}
		driver = WebdriverInitializer.webDriver;
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// The below method will save the screen shot with test method name
		try {
			File file = new File(filepath + methodName + ".png");
			FileUtils.copyFile(scrFile, file);
			Reporter.setCurrentTestResult(result);
			Reporter.log("<br> <img src='" + file.getAbsolutePath() + "'/> <br>");
			Reporter.setCurrentTestResult(null);
			log.info("***Placed screen shot in " + filepath + " ***");
		} catch (IOException e) {
			log.info(e);
		}
	}

	@Override
	public void onTestStart(ITestResult result) {

	}

	@Override
	public void onTestSkipped(ITestResult result) {

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {

	}

	@Override
	public void onFinish(ITestContext context) {

	}
}
