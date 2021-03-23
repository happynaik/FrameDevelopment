package com.oracle.framework;

import static com.oracle.framework.PageLoad.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

//Browser Setup And Functions Related To Browser 
public class WebdriverInitializer {
	private String browser;
	private EventFiringWebDriver eventdriver;
	private CaptureEvent eventListener;
	private static DesiredCapabilities cap = null;
	protected static WebDriver webDriver = null;
	
	public WebdriverInitializer(String browser) {
		this.browser = browser;
	}

	protected WebDriver getWebDriver() {
		return webdriverInitializer(this.browser);
	}
	
	private WebDriver webdriverInitializer(String Browser) {
		switch (Browser.toLowerCase()) {
		case "chrome":
			File chromeFile = new File(".\\driver\\chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", chromeFile.getAbsolutePath());

			File fl = new File("C:\\downloadFile\\");
			Path path = Paths.get("C:\\downloadFile\\");
			if (Files.exists(path)) {
				try {
					deleteDir(fl);
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Files.createDirectories(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					Files.createDirectories(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			String downloadFilepath = "C:\\downloadFile\\";
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);
			chromePrefs.put("safebrowsing.enabled", "true");
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			options.setAcceptInsecureCerts(true);
			options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
			options.addArguments("test-type");
			options.addArguments("start-maximized");
			options.addArguments("disable-infobars");
			options.addArguments("--disable-extensions");  			
			webDriver = new ChromeDriver(options);
			//System.out.println(webDriver);			
			eventListener = new CaptureEvent(1000);
			eventdriver = new EventFiringWebDriver(webDriver);
			eventdriver.register(eventListener);
			System.out.println(eventdriver);
			waitForPageLoaded(eventdriver);
			eventdriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);			
			break;
			
		
		case "firefox":

			File FireFoxFile = new File(".\\driver\\geckodriver_64_V0.20.exe");
			System.setProperty("webdriver.gecko.driver", FireFoxFile.getAbsolutePath());
			webDriver = new FirefoxDriver();
			eventdriver = new EventFiringWebDriver(webDriver);
			eventListener = new CaptureEvent(200);
			eventdriver.register(eventListener);
			eventdriver.manage().window().maximize();
			eventdriver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			break;

		case "ie":
			File ieFile = new File(".\\driver\\IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", ieFile.getAbsolutePath());
			webDriver = new InternetExplorerDriver();
			webDriver.manage().window().maximize();
			break;
			
		case "headless":
			cap = DesiredCapabilities.htmlUnit();			
			webDriver = new HtmlUnitDriver(cap);
			eventdriver = new EventFiringWebDriver(webDriver);
			eventListener = new CaptureEvent(200);
			eventdriver.register(eventListener);
			break;
		

		default:
			break;
		}
		return eventdriver;
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));

				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
}
