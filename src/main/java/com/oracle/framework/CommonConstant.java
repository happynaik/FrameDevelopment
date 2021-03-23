package com.oracle.framework;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.oracle.utility.ExtentManager;

public class CommonConstant {
	// Common constants
	
	public static final String excelAssignments = System.getProperty("user.dir")+ "\\src\\test\\resources\\TestData\\Keywords.xlsx";
	public static final String sheetKeywords = "Assignment1";
	//report paths
	public static final File local = new File("E:/Projects/Ora_reports");
	public static final File des =new File(System.getProperty("user.dir")+"\\test_output\\Automation_Report_Oracle.html");
}
