package com.chefooff.servicedesk.resource;

import java.net.UnknownHostException;

public class SrvEnv {
		
	static String env = "stable";
	static{
		String localhostname = null;
		try {
			localhostname = java.net.InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//String localhostname = "jira-test.colo.elex.be";
		if( localhostname.split("\\.")[0].contains("-") ){
			env = localhostname.split("\\.")[0].split("\\-")[1];
		} else if( localhostname.equals("pc1219") ){
			env = "test";
		}
	}
		
	public static String getEnv(){
		return env;
	}
}
