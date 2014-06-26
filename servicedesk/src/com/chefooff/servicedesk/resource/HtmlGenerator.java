package com.chefooff.servicedesk.resource;

import com.chefooff.servicedesk.resource.json.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HtmlGenerator {
	
	private String noFmsg;
	private JsonValues jsonValues;
	private Properties prop;
	private String fName = null;
	private String fType = null;
	private Integer startPos = 0;
	Integer iNumPerPage = null;
	private SimpleDateFormat restDateFormat;
	private SimpleDateFormat myDateFormat;
	private Calendar calendar;
	
	public HtmlGenerator(String jsonJqlAnswer, String fType) throws IOException{
		this(jsonJqlAnswer, fType, new Integer(0));
	}
	
	public HtmlGenerator(String jsonJqlAnswer, String fType, Integer startPos) throws IOException{
		
		prop = PropertyConf.getPropfile();
		iNumPerPage = new Integer(prop.getProperty("jira.issue.num"));
		this.fName = prop.getProperty("jql.name." + fType);
		this.fType = fType;
		this.startPos = startPos;
		
		Gson gson = new Gson();
		jsonValues = gson.fromJson(jsonJqlAnswer, JsonValues.class);
		
		restDateFormat = new SimpleDateFormat(prop.getProperty("date.rest.format"));
		myDateFormat = new SimpleDateFormat(prop.getProperty("date.my.format"));
		calendar = Calendar.getInstance();
	}
	
	public String getIssues() {

		StringBuffer htmlOutput = new StringBuffer();

		if ( 0 != jsonValues.total ) {
			
			String pagesNav = null;
			Integer firstIssueNum = 1;
			Integer maxIssueNum = jsonValues.total;
			if( "alltickets" == fType ){
				pagesNav = pagesNavMenu();
				if( (iNumPerPage+this.startPos) > jsonValues.total ) iNumPerPage = (jsonValues.total-this.startPos);
				maxIssueNum = jsonValues.maxResults+this.startPos;
				if( maxIssueNum > jsonValues.total ) maxIssueNum = jsonValues.total;
				firstIssueNum = this.startPos+1;
			} else iNumPerPage = jsonValues.total;
			
			//Detect ENV
			String jiraUrl = prop.getProperty("jira.srv." + SrvEnv.getEnv());
			
			htmlOutput.append("<table class=\"grid\" width=\"80%\"><tr class=\"titlerow\"><td colspan=\"11\" class=\"jiraformheader\"><h3 class=\"formtitle\">");
			htmlOutput.append(this.fName);
			htmlOutput.append("</h3></td></tr><tr><td colspan=11><div class=\"results-count\">");
			htmlOutput.append("Displaying issues <strong>" + firstIssueNum + "</strong> to <strong>" + maxIssueNum );
			htmlOutput.append("</strong> of <strong>" + jsonValues.total);
			htmlOutput.append("</strong> matching issues.</div>");
			if( "alltickets" == fType ) htmlOutput.append(pagesNav);
			htmlOutput.append("</td></tr><tr>");
			htmlOutput.append("<th></th><th>Key</th><th>Summary</th><th>Assignee</th><th>Reporter</th><th>Pr</th><th>Status</th><th>Res</th><th>Created</th><th>Updated</th><th>Due</th></tr>");
			for(int i = 0; i < iNumPerPage; i = i+1) {
            		htmlOutput.append("<tr");
                    if( (i+1)%2 == 0 ) htmlOutput.append(" style=\"background-color: #F2F2F2;\"");
                    htmlOutput.append("><td>" + (firstIssueNum+i) + "</td><td nowrap><a href=\"");
                    htmlOutput.append(jiraUrl + "browse/" + jsonValues.issues[i].getKey() + "\" target=\"_blank\" title=\"Open issue " + jsonValues.issues[i].getKey() + "\">"+ jsonValues.issues[i].getKey() + "</a></td>");
                    htmlOutput.append("<td><a href=\"" + jiraUrl + "browse/" + jsonValues.issues[i].getKey() + "\" target=\"_blank\" title=\"Open issue " + jsonValues.issues[i].getKey() + "\">" + jsonValues.issues[i].fields.getSummary() + "</a></td>");
                    if( null != jsonValues.issues[i].fields.assignee )
                    		htmlOutput.append("<td><a href=\"" + jiraUrl + "secure/ViewProfile.jspa?name=" + jsonValues.issues[i].fields.assignee.getName() + "\" target=\"_blank\" title=\"Open \"" + jsonValues.issues[i].fields.assignee.getDisplayName() + " JIRA user profile\">" + jsonValues.issues[i].fields.assignee.getDisplayName() + "<a/></td>");
                    else    htmlOutput.append("<td>Unassigned</td>");
                    if( null != jsonValues.issues[i].fields.reporter )
                    		htmlOutput.append("<td><a href=\"" + jiraUrl + "secure/ViewProfile.jspa?name=" + jsonValues.issues[i].fields.reporter.getName() + "\" target=\"_blank\" title=\"Open \"" + jsonValues.issues[i].fields.reporter.getDisplayName() + " JIRA user profile\">" + jsonValues.issues[i].fields.reporter.getDisplayName() + "<a/></td>");
                    else    htmlOutput.append("<td></td>");
                    if( null != jsonValues.issues[i].fields.priority )
                    		htmlOutput.append("<td><img src=\"" + jsonValues.issues[i].fields.priority.getIconUrl() + "\" title=\"" + jsonValues.issues[i].fields.priority.getName() + "\"/>" + jsonValues.issues[i].fields.priority.getName() + "</td>");
                    else    htmlOutput.append("<td></td>");
                    if( null != jsonValues.issues[i].fields.status )
                    		htmlOutput.append("<td nowrap><img src=\"" + jsonValues.issues[i].fields.status.getIconUrl() + "\" title=\"" + jsonValues.issues[i].fields.status.getName() + "\"/>&nbsp;" + jsonValues.issues[i].fields.status.getName() + "</td>");
                    else	htmlOutput.append("<td></td>");
                    htmlOutput.append("<td>");
                    if(null == jsonValues.issues[i].fields.resolution)
                    		htmlOutput.append("<span class=\"redText\">Unresolved</span>");
                    else	htmlOutput.append(jsonValues.issues[i].fields.resolution.getName());
                    htmlOutput.append("</td>");
                    htmlOutput.append("<td>");
                    if(null != jsonValues.issues[i].fields.getCreated()){
                    	try {
							calendar.setTime(restDateFormat.parse(jsonValues.issues[i].fields.getCreated()));
							htmlOutput.append(myDateFormat.format(calendar.getTime()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
                    htmlOutput.append("</td>");
                    htmlOutput.append("<td>");
                    if(null != jsonValues.issues[i].fields.getUpdated()){
                    	try {
							calendar.setTime(restDateFormat.parse(jsonValues.issues[i].fields.getUpdated()));
							htmlOutput.append(myDateFormat.format(calendar.getTime()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
                    htmlOutput.append("</td>");
                    htmlOutput.append("<td>");
                    if(null != jsonValues.issues[i].fields.getDuedate()){
                    	try {
							calendar.setTime(restDateFormat.parse(jsonValues.issues[i].fields.getDuedate()));
							htmlOutput.append(myDateFormat.format(calendar.getTime()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
                    htmlOutput.append("</td>");
                    htmlOutput.append("</tr>");
            }
			if( "alltickets" == fType ) htmlOutput.append("<tr><td colspan=11>" + pagesNav + "</td></tr>");

		} else {
			
			noFmsg = prop.getProperty("msg.noFindIssues");
			
			htmlOutput.append("<table width=\"80%\" align=\"center\"><tr class=\"grid titlerow\"><td class=\"jiraformheader\"><h3 class=\"formtitle\">");
			htmlOutput.append(this.fName);
			htmlOutput.append("</h3></td></tr><tr><td colspan=\"10\"><div class=\"notify info\">");
			htmlOutput.append(noFmsg);
			htmlOutput.append("</div>");	
		}
			
		htmlOutput.append("</td></tr></table>");
		htmlOutput.append("</table><table><tr height=15 class=\"ihatemyselffordoingthis\"><td></td></tr></table>");
			
		return htmlOutput.toString();
	}
	
	private String pagesNavMenu(){
		
		StringBuffer htmlpagesNavMenu = new StringBuffer();
		Integer startPos = null;
		Integer pageCounter = (int) Math.ceil(jsonValues.total/(float)this.iNumPerPage);
		
		htmlpagesNavMenu.append("<p class=\"pagination\">");
		for(int i = 1; i <= pageCounter; i = i+1){
			startPos = new Integer(i*iNumPerPage-iNumPerPage);
			if( !this.startPos.toString().equals(startPos.toString()) ){
				htmlpagesNavMenu.append("<a href=\"./AllTickets?action=alltickets&start=" + startPos + "\">" + i + "</a>");
			} else {
				htmlpagesNavMenu.append("&nbsp;<span style=\"background-color: #3c78b5;color: #ffffff; font-size: 12pt;\">&nbsp;" + i + "&nbsp;</span>&nbsp;");
			}
		}
		htmlpagesNavMenu.append("</p>");
	
		return htmlpagesNavMenu.toString();
	}
}
