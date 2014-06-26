package com.chefooff.servicedesk.resource.json;

public class UserDetails {
	private String displayName;
	private String name;
	private String self;
	private String emailAddress;

	//returns Stefan Dinev 
	public String getDisplayName() {
		return displayName;
	}
	
	//Returns trigram
	public String getName() {
		return name;
	}
	
	public String getSelf() {
		return self;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
}