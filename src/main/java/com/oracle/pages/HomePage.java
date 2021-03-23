package com.oracle.pages;

import static com.oracle.framework.PropertiesReader.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.oracle.framework.AutomationHelper;
import com.relevantcodes.extentreports.LogStatus;

public class HomePage {
	private WebDriver driver;
	static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public boolean isHomePageLaunched() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		return wait
				.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(getDataFromProperty("objects", "orcSearchTxtField"))))
				.isDisplayed();

	}
	
	public WebElement getSearchTextbox() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		return wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(getDataFromProperty("objects", "orcSearchTxtField"))));
	}
	
	public void setSearchValue(String keyword) {
		
		getSearchTextbox().clear();
		// Enter any keyword to search
		getSearchTextbox().sendKeys(keyword, Keys.ENTER);		
		log.info("Entered keyword in search textbox as -"+keyword);
	}

	
}
