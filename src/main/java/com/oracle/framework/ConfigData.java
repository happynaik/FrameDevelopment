package com.oracle.framework;
import static com.oracle.framework.PropertiesReader.*;
public class ConfigData 
{		
	private static final String CONFIG = "config";  	
	public static final String BROWSER=getDataFromProperty(CONFIG,"browser");
	public static final String APPURL=getDataFromProperty(CONFIG,"googleURL");
	
}
