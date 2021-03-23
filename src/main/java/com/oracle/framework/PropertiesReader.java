package com.oracle.framework;

import java.io.File;
import java.io.FileInputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

//For Reading Property Files
public class PropertiesReader 
{
	static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public static String getDataFromProperty(String fileName, String key) 
	{
		String value = null;
		
		try {
			File f = new File("./src/test//resources/" + fileName + ".properties");
			FileInputStream fis = new FileInputStream(f);
			Properties prop = new Properties();
			prop.load(fis);
			value = (String) prop.get(key);
		} catch (Exception e) {
			log.info(e);
		}
		return value;
	}

}
