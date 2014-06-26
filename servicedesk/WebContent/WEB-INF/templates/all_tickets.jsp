<%@ page import="java.util.Properties,java.util.ArrayList,com.chefooff.servicedesk.resource.HtmlGenerator,com.chefooff.servicedesk.resource.PropertyConf,com.chefooff.servicedesk.resource.json.*" %>
<%
Properties prop = PropertyConf.getPropfile();

ArrayList<HtmlGenerator> htmlFilterGen = (ArrayList<HtmlGenerator>)request.getAttribute("htmlFgen");
String errMsg = (String)request.getAttribute("errMsg");
%>
<div class="dashboard-content">
<div class="intform">
	<table>
		<tr height=15 class="ihatemyselffordoingthis">
			<td></td>
		</tr>
	</table>
<%
if( null != request.getParameter("crtsucc") ){
%>
	<table width="80%" align="center">
		<tr>
			<td colspan="10">
				<div class="notify info"><%=prop.getProperty("msg.crtsucc")%></div>
			</td>
		</tr>
	</table>
	<table>
		<tr height=15 class="ihatemyselffordoingthis">
			<td></td>
		</tr>
	</table>
<%
}

if( null == errMsg ){
	for (HtmlGenerator fHtml : htmlFilterGen) {
		out.write(fHtml.getIssues());
	}
} else {
%>
        <table width="80%" align="center">
                <tr class="grid titlerow">
                        <td class="jiraformheader">
                                <h3 class="formtitle">All Tickets</h3></td>
                </tr>
                <tr>
                        <td colspan="10">
                                <div class="notify info"><%=errMsg%></div>
                        </td>
                </tr>
        </table>
<%
}
%>
	<table>
		<tr height=15 class="ihatemyselffordoingthis">
			<td></td>
		</tr>
	</table>
</div>
</div>