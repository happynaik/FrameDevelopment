package com.oracle.framework;

import java.util.ResourceBundle;

public class ReadPropertiesFile 
{
    private static ResourceBundle rb = ResourceBundle.getBundle("config");
    
    public static void main(String[] args) {
        String browser = rb.getString("browser");
        System.out.println(browser);
    }

}
