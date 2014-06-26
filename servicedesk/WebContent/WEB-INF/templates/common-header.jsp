<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- saved from url=(0080)https://jira.melexis.com/jira/secure/CreateIssue.jspa?pid=10093&issuetype=10 -->
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title> Service Desk </title>
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Expires" content="-1">
	<meta id="atlassian-token" name="atlassian-token" content="roDr51vKlH">
	<link rel="shortcut icon" href="https://jira.melexis.com/jira/s/531/3/_/favicon.ico">
	<link rel="icon" type="image/png" href="https://jira.melexis.com/jira/s/531/3/_/images/icons/favicon.png">
	<script type="text/javascript">var contextPath = '/jira';</script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/theme/com.atlassian.auiplugin-ajs.css" media="all">
	<!--[if IE]>
	<link type="text/css" rel="stylesheet" href="/jira/s/531/3/2.1.3/_/download/batch/com.atlassian.auiplugin:ajs/com.atlassian.auiplugin:ajs.css?ieonly=true" media="all">
	<![endif]-->
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/theme/jira.webresources-autocomplete.css" media="all">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/theme/calendar.css" media="all">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/theme/jira.webresources-global-static.css" media="all">
	<!--[if IE]>
	<link type="text/css" rel="stylesheet" href="/jira/s/531/3/1.0/_/download/batch/jira.webresources:global-static/jira.webresources:global-static.css?ieonly=true" media="all">
	<![endif]-->
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/theme/jira.webresources-global-static(1).css" media="print">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/theme/global.css" media="all">
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/com.atlassian.auiplugin-ajs.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jira.webresources-jira-global.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jira.webresources-autocomplete.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jira.webresources-calendar.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jira.webresources-calendar-en.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jira.webresources-key-commands.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jira.webresources-header.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/shortcuts.js"></script>
</head>
<body id="jira">
<div id="header">
	<div id="header-top">
		<a id="logo" href="#"><h2 style="color: #FFFFFF;">&nbsp<img class="logo" src="${pageContext.request.contextPath}/images/sd-logo-icon128.png" height="30" border="0" alt="Xtrion Issuetracker"> Service Desk</h3></a>
		<div id="header-details-user" class="aui-dd-parent header-link-container dd-allocated" style="">
<%
String errMsg = (String)request.getAttribute("errMsg");
if ( null != request.getAttribute("displayName") )
	 out.write(request.getAttribute("displayName") + "&nbsp");
else out.write("<a class=\"lnk\" href=\"./Logon\">Log In&nbsp</a>");
%>
			<span class="drop-wrap">
				<a class="drop aui-dd-link" href="#"><span>Access more options</span></a>
			</span>
			<div class="aui-dropdown standard hidden">
				<ul id="jira-help" class="">
					<li><a id="view_help" class="" title="Goto the online documentation for JIRA" href="http://docs.atlassian.com/jira/docs-041/Home?clicked=jirahelp">Online Help</a></li>
					<li><a  id="user_history"   class=""  title="Get more information about JIRA" href="http://www.atlassian.com/software/jira">About JIRA</a></li>
				</ul>
<%
if ( null != request.getAttribute("displayName") ) {
%>	
				<ul id="system" class="last">
					<li><a  id="log_out"   class=""  title="Log out and cancel any automatic login." href="./Logon?action=logout">Log Out</a></li>
				</ul>
<%
}
%>			
			</div>
		</div>
	</div>
	<div id="header-bottom">
		<div id="menu">
<%
if ( null != request.getAttribute("displayName") ) {
	String action = request.getParameter("action");
	if ( null != action ) {
		String myTickets  = action.equals("mytickets")?"selected":"lazy";
		String allTickets = action.equals("alltickets")?"selected":"lazy";
		String call       = action.equals("call")?"selected":"lazy";
%>	
				<ul id="main-nav" class="menu-bar">
				<li class="aui-dd-parent <%out.write(myTickets);%>">
					<a class="lnk" href="./MyTickets?action=mytickets" id="browse_link" accessKey="b" title="Show My Tickets">My Tickets</a>
				</li> 
				<li style="display:block;float:left;height:24px;line-height:1.5;margin-left:.4em;width:auto;">
<% 
				if( !action.equals("mytickets") && !action.equals("alltickets") ){
%>
					<img style="background-position:0 2px;background-repeat:no-repeat;display:inline-block;position:relative;top:5px;" src="${pageContext.request.contextPath}/theme/pic/header-separator2.png"/>
<%
				}
%>
					</li> 
					<li class="aui-dd-parent <%out.write(allTickets);%>">
						<a class="lnk" href="./AllTickets?action=alltickets" id="browse_link" accessKey="b" title="Show AllTickets">All Tickets</a>
					</li>
					<li style="display:block;float:left;height:24px;line-height:1.5;margin-left:.4em;width:auto;">
<% 
				if( !action.equals("call") && !action.equals("alltickets") ){
%>
					<img style="background-position:0 2px;background-repeat:no-repeat;display:inline-block;position:relative;top:5px;" src="${pageContext.request.contextPath}/theme/pic/header-separator2.png"/>
<%
				}
%>
					</li> 
					<li class="aui-dd-parent <%out.write(call);%>">
						<a class="lnk" href="./CreateTicket?action=call" id="browse_link" accessKey="b" title="Create Service Desk issue">Create Ticket</a>
					</li>
					<li style="display:block;float:left;height:24px;line-height:1.5;margin-left:.4em;width:auto;">
					</li>
				</ul>
<%
	}
}
%>

		</div>
	</div>
</div>
