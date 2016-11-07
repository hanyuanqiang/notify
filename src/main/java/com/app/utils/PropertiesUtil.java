package com.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public static String getValue(String key){
		Properties prop = new Properties();
		InputStream input = new PropertiesUtil().getClass().getResourceAsStream("/app.properties");
		try {
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (String)prop.get(key);
	}
}
