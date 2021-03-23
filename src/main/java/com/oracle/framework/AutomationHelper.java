package com.oracle.framework;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import com.google.common.base.Function;

public class AutomationHelper 
{
	public static WebElement findElement(WebDriver driver, By locator, int waitTime) 
	{
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(waitTime)).pollingEvery(Duration.ofMillis(500))
				.ignoring(NoSuchElementException.class)
				.ignoring(TimeoutException.class)
				.ignoring(ElementNotVisibleException.class)
				.ignoring(StaleElementReferenceException.class);

		WebElement element = wait.until(new Function<WebDriver, WebElement>() 
		{
			public WebElement apply(WebDriver driver) 
			{
				return driver.findElement(locator);
			}
		});
		return element;
	}
	
	
}
