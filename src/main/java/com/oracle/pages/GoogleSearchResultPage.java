package com.oracle.pages;

import static com.oracle.framework.PropertiesReader.getDataFromProperty;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleSearchResultPage {
	private WebDriver driver;
	static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	public GoogleSearchResultPage(WebDriver driver) {
		this.driver = driver;
	}

	public boolean isHomePageLaunched() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		return wait
				.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(getDataFromProperty("objects", "orcSearchTxtField"))))
				.isDisplayed();

	}

}
