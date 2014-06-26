package com.chefooff.servicedesk.resource.json;

//Start Json parsing
public class JsonValues {
	public Component[] components;
	public String key;
	public boolean active;
	public String displayName;
	public String emailAddress;
	public Integer total;
	public Integer startAt;
	public Integer maxResults;
	public Issues[] issues;
	public String uploadLimit;

	public Integer getTotal() {
		return total;
	}
	
	public Integer startAt() {
		return startAt;
	}
	
	public Integer getMaxResults() {
		return maxResults;
	}
	
	public String uploadLimit(){
		return uploadLimit;
	}
	
	public String getIssueKey(){
		return key;
	}
	
	public String getDisplayName(){
		return displayName;
	}
	
	public boolean getActive(){
		return active;
	}
	
	public String getEmailAddress(){
		return emailAddress;
	}
}