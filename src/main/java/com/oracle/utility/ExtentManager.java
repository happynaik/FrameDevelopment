package com.oracle.utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oracle.framework.CommonConstant;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;


public class ExtentManager 
{
	private static ExtentReports extent;
	public static Date date = new Date();  
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");  
	public static String strDate= formatter.format(date);  
	public static String Path =  System.getProperty("user.dir")+"\\test_output\\Automation_Report_"+strDate+".html";
	
	public static File pjtPath = new File(System.getProperty("user.dir")+"\\test_output\\Automation_Report_"+strDate+".html");
	
	public static ExtentReports Instance()
	{
		extent = new ExtentReports(Path, true, DisplayOrder.NEWEST_FIRST);
		extent.addSystemInfo("Environment", "QA");
		extent.loadConfig(new File(CommonConstant.EXTENTCONFIG_PATH));
		return extent;
	}
}