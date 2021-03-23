package com.oracle.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class WebElementUtils 
{
	
    private static WebDriver webDriver;

    public static void clearInputField(WebElement input) 
    {
        if (input != null) 
        {
            input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            input.sendKeys(Keys.DELETE);
        }
    }

    public static void sendKeysToWebElement(WebElement element, String text) 
    {
        if (element != null) 
        {
            element.sendKeys(text);
        }
    }

    public static boolean isElementEnabled(WebElement element) 
    {
        return (element != null) && element.isEnabled();
    }

    public static WebElement getElement(WebElement webElement) 
    {
        WebElement result = null;

        if (webElement != null) 
        {
            result = webElement;
        }

        return result;
    }

    public static void clickOnWebElement(WebElement element) 
    {
        if (element != null) 
        {
            element.click();
        }
    }

    public static void mouseOverOnWebElement(WebElement element) 
    {
        if (element != null) 
        {
            Actions builder= new Actions(webDriver);
            builder.moveToElement(element).build().perform();
        }
    }

    public static String getWebElementText(WebElement element) 
    {
        return (element != null) ? element.getText() : null;
    }

    public static void setWebDriverProvider(WebDriver driverProvider) 
    {
        webDriver = driverProvider;
    }

    public static void clickOnElement(WebElement element) 
    {
        if (element != null) {
            element.click();
        }
    }
    
    public static void clickOnElementJS(WebDriver driver,WebElement element)
    {
        if(element != null) 
        {         
        	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
        }
    }
    
    
    
    public static void clickOnElementLocation(WebDriver driver,WebElement element)
	{
		if(element != null)
		{
			 try {
				 Thread.sleep(500);
                 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
             } catch (Exception e) {
            	 e.printStackTrace();
             }
    	element.click();
    	}
	}
    
    public static void sendKeysToElement(WebElement element, Keys keys) 
    {
        if (element != null) {
            element.sendKeys(keys);
        }
    }
    
    //Alert Handling
    
    public static void clickOKonAlert(WebDriver driver)
	{
		Alert alt = driver.switchTo().alert();
		alt.accept();
	}
	public static void clickCancelonAlert(WebDriver driver)
	{
		Alert alt = driver.switchTo().alert();
		alt.dismiss();
	}
	public static void enterTextonAlert(WebDriver driver, String text)
	{
		Alert alt = driver.switchTo().alert();
		alt.sendKeys(text);
	}
	public static String getAlertMessage(WebDriver driver)
	{
		Alert alt = driver.switchTo().alert();
		return alt.getText();
	}
	
	//DropDown Handlers
	
	public static void selectDDLByIndex(WebElement ddl, int index)
	{
		Select DDL = new Select(ddl);
		DDL.selectByIndex(index);
	}
	
	public static void selectDDLByValue(WebElement ddl, String value)
	{
		Select DDL = new Select(ddl);
		DDL.selectByValue(value);
	}
	
	public static void selectDDLByVisibleText(WebElement ddl, String text)
	{
		Select DDL = new Select(ddl);
		DDL.selectByVisibleText(text);
	}
	public static void deselectDDLByIndex(WebElement ddl, int index)
	{
		Select DDL = new Select(ddl);
		DDL.deselectByIndex(index);
	}
	
	public static void timeStamp()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		System.setProperty("current.date.time", dateFormat.format(new Date()));
	}
	
	public static void SelectItemFromVisibilityDropdown(String VisibilityItemVal,WebElement ddl) {
		
		Select selectValforVisibility=new Select(ddl);		
		List<WebElement> lstitems=selectValforVisibility.getOptions();		
		for(int i=0;i<lstitems.size();i++){			
			if(lstitems.get(i).getText().equals(VisibilityItemVal)){
				selectValforVisibility.selectByVisibleText(VisibilityItemVal);				
			break;
			}
		}		
	}
	
	
	
	public static boolean isElementDisplayed(WebElement element)
	{
			
			try {
				return element.isDisplayed();			
			} catch (Exception e) {
				return false;
			}
		} 

// Find an element
 


}
