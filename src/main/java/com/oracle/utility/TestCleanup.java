package com.oracle.utility;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TestCleanup {
	static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public static void killProcess(String ProcessName) {

		try {
			System.out.println("Killing the Process : " + ProcessName);
			Runtime.getRuntime().exec("taskkill /f /im " + ProcessName + ".exe");
			
		}

		catch (Throwable e) {
			log.info("Exception:" + e);
		}
	}

	public static void clearTempFolder() throws IOException {

		try {
			File file = new File(System.getProperty("java.io.tmpdir"));
			FileUtils.cleanDirectory(file);
		} catch (Throwable e) {
			log.info("Exception:" + e);
		}
	}

}