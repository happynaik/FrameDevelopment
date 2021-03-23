package com.oracle.framework;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class CaptureEvent extends BaseClass implements WebDriverEventListener

{
	long start;
	private int aSpeed = 0;
	static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public CaptureEvent(int aSpeed) {
		this.aSpeed = aSpeed;
	}

	private void setControlSpeed() {
		try {
			Thread.sleep(aSpeed);			
		} catch (InterruptedException e) {
			e.printStackTrace();			
		}
	}

	public void beforeNavigateTo(String url, WebDriver driver) {
		setControlSpeed();
		start = System.currentTimeMillis();
		//System.out.println("Before navigating to: '" + url + "'");
		
	}

	public void afterNavigateTo(String url, WebDriver driver) {
		setControlSpeed();
		System.out.println("Navigated to:'" + url + "'");
		System.out.println(new Date() + " Page Loading took " + (System.currentTimeMillis() - start));
	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
		setControlSpeed();
		//System.out.println("Value of the:" + element.toString() + " before any changes made");
	}

	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		setControlSpeed();
		//System.out.println("Element value changed to: " + element.toString());
	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
		setControlSpeed();
		start = System.currentTimeMillis();
		//System.out.println("Trying to click on: " + element.toString());		
			try 
			{
				driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
				element.isDisplayed();
				element.isEnabled();
			} catch (Exception e) {
				driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			}			
	}

	public void afterClickOn(WebElement element, WebDriver driver) {
		setControlSpeed();
		//System.out.println(new Date() + " Clicking On Element took " + (System.currentTimeMillis() - start));
		//System.out.println("Clicked on: " + element.toString());
	}

	public void beforeNavigateBack(WebDriver driver) {
		setControlSpeed();
		System.out.println("Navigating back to previous page");
	}

	public void afterNavigateBack(WebDriver driver) {
		setControlSpeed();
		System.out.println("Navigated back to previous page");
	}

	public void beforeNavigateForward(WebDriver driver) {
		setControlSpeed();
		System.out.println("Navigating forward to next page");
	}

	public void afterNavigateForward(WebDriver driver) {
		setControlSpeed();
		System.out.println("Navigated forward to next page");
	}

	public void onException(Throwable error, WebDriver driver) {
		setControlSpeed();
		//System.out.println("Printing Exception occured: " + error);
		//extentTest.log(LogStatus.INFO, "Scenario : " + " Failed " + " With Error " + error);
	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		setControlSpeed();
		//System.out.println("Trying to find Element : " + by.toString());
		element = driver.findElement(by);	
	
		if (driver instanceof JavascriptExecutor) {
	        ((JavascriptExecutor)driver).executeScript("arguments[0].setAttribute('style', arguments[1]);",element,"color: black; border: 3px solid black;");
	    }
		/*if (driver instanceof JavascriptExecutor) {
	        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='2px solid black'", element);
	    }	*/	
	}

	@Override
	public void afterAlertAccept(WebDriver arg0) {

	}

	@Override
	public void afterAlertDismiss(WebDriver arg0) {

	}

	@Override
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {

	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		setControlSpeed();
		/*element = driver.findElement(by);
		if (driver instanceof JavascriptExecutor) 
		{
			((JavascriptExecutor)driver).executeScript("arguments[0].style.border=''", element);
		}	*/
		try 
		{
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			element.isDisplayed();
			element.isEnabled();
		} catch (Exception e) {
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		}	
	}

	@Override
	public void afterNavigateRefresh(WebDriver arg0) {

	}

	@Override
	public void afterScript(String arg0, WebDriver arg1) {
		setControlSpeed();

	}

	@Override
	public void beforeAlertAccept(WebDriver arg0) {

	}

	@Override
	public void beforeAlertDismiss(WebDriver arg0) {

	}

	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {

	}

	@Override
	public void beforeNavigateRefresh(WebDriver arg0) {

	}

	@Override
	public void beforeScript(String arg0, WebDriver arg1) {
		setControlSpeed();

	}

	public void beforeSwitchToWindow(String windowName, WebDriver driver) {

	}

	public void afterSwitchToWindow(String windowName, WebDriver driver) {

	}

	@Override
	public <X> void afterGetScreenshotAs(OutputType<X> arg0, X arg1) {
		
		
	}

	@Override
	public void afterGetText(WebElement arg0, WebDriver arg1, String arg2) {
		
		
	}

	@Override
	public <X> void beforeGetScreenshotAs(OutputType<X> arg0) {
		
		
	}

	@Override
	public void beforeGetText(WebElement arg0, WebDriver arg1) {
		
		
	}
}