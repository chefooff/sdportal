package com.chefooff.servicedesk.resource.json;


public class Fields {
	private String summary;
	private String duedate;
	private String updated;
	private String created;
	public UserDetails assignee;
	public UserDetails reporter;
	public StatusPriority priority;
	public StatusPriority status;
	public Resolution resolution;
	
	public String getSummary() {
		return summary;
	}
	public String getDuedate() {
		return duedate;
	}
	
	public String getUpdated() {
		return updated;
	}
	
	public String getCreated() {
		return created;
	}
}