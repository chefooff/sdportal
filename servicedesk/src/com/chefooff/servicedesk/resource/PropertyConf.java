package com.chefooff.servicedesk.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyConf {

	static Properties conFile;
	static{
		conFile = new Properties();
		try {
			InputStream stream = PropertyConf.class.getClassLoader().getResourceAsStream("com/chefooff/servicedesk/resource/portlet.properties");
			try {
				conFile.load(stream);
			} finally {
				stream.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static Properties getPropfile(){
		return conFile;
	}
}