<%@ page import="
                 java.util.Properties,
                 java.util.ArrayList,com.chefooff.servicedesk.resource.HtmlGenerator,com.chefooff.servicedesk.resource.PropertyConf,com.chefooff.servicedesk.resource.SrvEnv,com.chefooff.servicedesk.resource.json.*,
                 java.util.HashMap
                " 
%>
<%
Properties prop = PropertyConf.getPropfile();

String customfield_10081 = null; //Location
String summary           = null;
String customfield_10022 = null; //Business impact
String customfield_10031 = null; //Urgency				
String description       = null;
String components        = null;
String customfield_10023 = null; //Business impact details
String customfield_10032 = null; //Urgency details
String customfield_10033 = null; //Requested date
String customfield_10171 = null; //Service Desk Escalated
String customfield_10172 = null; //Service Desk Line
String sbmt              = null;

//--ATTRIBUTE part
JsonValues jsonValues    = (JsonValues)request.getAttribute("jsonValues");
String errMsg            = (String)request.getAttribute("errMsg");
Integer attchMaxSize     = Integer.parseInt((String)request.getAttribute("attchMaxSize"));
//--END ATTRIBUTE part

//--GET part
sbmt                     = request.getParameter("sbmt");
//--END GET part

//--POST part
customfield_10081        = request.getParameter("customfield_10081"); //Location
summary                  = request.getParameter("summary");
customfield_10022        = request.getParameter("customfield_10022"); //Business impact
customfield_10031        = request.getParameter("customfield_10031"); //Urgency				
description              = request.getParameter("description");
components               = request.getParameter("components");
customfield_10023        = request.getParameter("customfield_10023"); //Business impact details
customfield_10032        = request.getParameter("customfield_10032"); //Urgency details
customfield_10033        = request.getParameter("customfield_10033"); //Requested date
customfield_10171        = request.getParameter("customfield_10171"); //Service Desk Escalated
customfield_10172        = request.getParameter("customfield_10172"); //Service Desk Line
//--END POST part

StringBuffer dynComps = new StringBuffer();
String compSelected = "";
for(int i = 0; i < jsonValues.components.length; i++){
	compSelected = (jsonValues.components[i].getComId().equals(components))?"selected ":"";
	dynComps.append("<option value=\""+jsonValues.components[i].getComId()+"\" title=\""+jsonValues.components[i].getComName()+"\" "+compSelected+">"+jsonValues.components[i].getComName()+"</option>");
}
%>
<table cellspacing="0" cellpadding="10" border="0" width="100%" bgcolor="#f7f7f7">
	<tbody>
		<tr>
			<td>
				<div class="intform">
				
				<form action="${pageContext.request.contextPath}/CreateTicket?action=call&sbmt=1" method="post" name="jiraform" onsubmit="if (this.submitted) return false; this.submitted = true; return true" enctype="multipart/form-data">
				<table class="jiraform maxWidth">
					<tbody>
						<tr class="titlerow">
							<td colspan="2" class="jiraformheader">
								<h3 class="formtitle">Create Issue</h3></td>
						</tr>
						<tr class="descriptionrow">
							<td colspan="2" class="jiraformheader">
								<div class="desc-wrap"><%=(null == errMsg?"Enter the details of the issue...":errMsg)%></div>
								<div class="notify info"><%=(null == errMsg?"Enter the details of the issue...":errMsg)%></div>
							</td>
						</tr>
						<tr class="ihatemyselffordoingthis">
							<td colspan="2"></td>
						</tr>
						<tr>
							<td class="fieldLabelArea">Project:</td>
							<td bgcolor="#ffffff" class="fieldValueArea"><span id="project/string(&#39;name&#39;)">.Service Desk</span></td>
						</tr>
						<tr>
							<td class="fieldLabelArea">Issue Type:</td>
							<td class="fieldValueArea">
							<img src="<%=prop.getProperty("jira.srv." + SrvEnv.getEnv())%>images/icons/genericissue.gif" height="16" width="16" border="0" align="absmiddle" alt="Call" title="Call - Customer inquiry to IT">&nbsp;Call</td>
						</tr>
						<tr class="fieldArea" id="reporterFieldArea">
							<td class="fieldLabelArea">
								<label for="reporter">
									<span class="errMsg" style="font-weight:normal;" title="Fields in italics are required"><sup>*</sup>Reporter:</span>
								</label>
							</td>
							<td class="fieldValueArea">
								<div class="ajax_autocomplete" id="reporter_container">
									<input type="text" name="reporter" id="reporter" value="<%=request.getAttribute("displayName")%>" style="width: 30%;" disabled>
									<div id="reporter_results" class="ajax_results"></div>
								</div>
							</td>
						</tr>
<%
					if( sbmt != null && (customfield_10081 == null || customfield_10081.isEmpty()) ){
%>
						<tr class="formErrors formErrorArea">
							<td>&nbsp;</td>
							<td>
								<span class="errMsg">Location is required.</span>
							</td>
						</tr>
<%
					}
%>
						<tr class="fieldArea" id="customfield_10081FieldArea">
							<td class="fieldLabelArea <%=(sbmt != null && (customfield_10081 == null || customfield_10081.isEmpty()))?"formErrors":""%>">
								<label for="customfield_10081">
									<span class="errMsg" style="font-weight:normal;" title="Fields in italics are required"><sup>*</sup>Location:</span>
								</label>
							</td>
							<td class="fieldValueArea <%=(sbmt != null && (customfield_10081 == null || customfield_10081.isEmpty()))?"formErrors":""%>">
								<textarea name="customfield_10081" id="customfield_10081" rows="4" wrap="virtual" cols="40" class="textarea"><%=(null!=customfield_10081)?customfield_10081:""%></textarea>
								<div class="fieldDescription">Please provide the site you are currently residing on.</div>
							</td>
						</tr>
						<tr class="fieldArea" id="componentsFieldArea">
							<td class="fieldLabelArea">
								<label for="components">Component/s:</label>
							</td>
							<td class="fieldValueArea">
								<select name="components" id="components" size="4">
									<option value="-1">Unknown</option>
<%
//									Print componens
									out.write(dynComps.toString());
%>
								</select>
							</td>
						</tr>
<%
					if( sbmt != null && (summary == null || summary.isEmpty()) ){
%>
						<tr class="formErrors formErrorArea">
							<td>&nbsp;</td>
							<td>
								<span class="errMsg">You must specify a summary of the issue.</span>
							</td>
						</tr>
<%
					}
%>
						<tr class="fieldArea" id="summaryFieldArea">
							<td class="fieldLabelArea  <%=(sbmt != null && (summary == null || summary.isEmpty()))?"formErrors":""%>">
								<label for="summary">
									<span class="errMsg" style="font-weight:normal;" title="Fields in italics are required"><sup>*</sup>Summary:</span>
								</label>
							</td>
							<td class="fieldValueArea  <%=(sbmt != null && (summary == null || summary.isEmpty()))?"formErrors":""%>">
								<input type="text" name="summary" id="summary" class="textfield" value="<%=(null!=summary)?summary:""%>" maxlength="255">
							</td>
						</tr>
						<tr class="fieldArea" id="descriptionFieldArea">
							<td class="fieldLabelArea">
								<label for="description">Description:</label>
							</td>
							<td class="fieldValueArea">
								<textarea name="description" id="description" rows="12" wrap="virtual" cols="9999" class="textarea"><%=(null!=description)?description:""%></textarea>
							</td>
						</tr>
						<tr class="fieldArea" id="customfield_10033FieldArea">
							<td class="fieldLabelArea">
								<label for="customfield_10033">Requested date:</label>
							</td>
							<td class="fieldValueArea">
								<input type="text" size="20" name="customfield_10033" id="customfield_10033" value="<%=(null!=customfield_10033)?customfield_10033:""%>">
								<img id="customfield_10033_trigger_c" src="<%=prop.getProperty("jira.srv." + SrvEnv.getEnv())%>images/icons/cal.gif" width="16" height="16" border="0" alt="Select a date" title="Select a date" /> 
								<script type="text/javascript"> 
								    Calendar.setup({
									firstDay    : (1) -1,              // first day of the week sdi
									inputField  : "customfield_10033",  // id of the input field
									button      : "customfield_10033_trigger_c", // trigger for the calendar (button ID)
									align       : "Tl",           // alignment (defaults to "Bl")
									singleClick : true,
									date : new Date(),
									useISO8601WeekNumbers : false, // use ISO8061 date/time standard    
									ifFormat    : "20%y-%m-%d"      // our date only format
								    });
								</script>
								<div class="fieldDescription">By when is the end customer expecting this change to be implemented.</div>
							</td>
						</tr>
<%
										if( sbmt != null && (customfield_10022 == null || customfield_10022.isEmpty()) ){
%>
                                                <tr class="formErrors formErrorArea">
                                                        <td>&nbsp;</td>
                                                        <td>
                                                                <span class="errMsg">Business impact is required.</span>
                                                        </td>
                                                </tr>
<%
										}
%>
						<tr class="fieldArea" id="customfield_10022FieldArea">
							<td class="fieldLabelArea  <%=(sbmt != null && (customfield_10022 == null || customfield_10022.isEmpty()))?"formErrors":""%>">
								<label for="customfield_10022">
									<span class="errMsg" style="font-weight:normal;" title="Fields in italics are required"><sup>*</sup>Business impact:</span>
								</label>
							</td>
							<td class="fieldValueArea  <%=(sbmt != null && (customfield_10022 == null || customfield_10022.isEmpty()))?"formErrors":""%>">
								<select name="customfield_10022" id="customfield_10022">
									<option value="">None</option>
									<option value="Critical" <%=(null!=customfield_10022 && customfield_10022.equals("Critical"))?"selected":""%>>Critical</option>
									<option value="High" <%=(null!=customfield_10022 && customfield_10022.equals("High"))?"selected":""%>>High</option>
									<option value="Medium" <%=(null!=customfield_10022 && customfield_10022.equals("Medium"))?"selected":""%>>Medium</option>
									<option value="Low" <%=(null!=customfield_10022 && customfield_10022.equals("Low"))?"selected":""%>>Low</option>
								</select>
								<div class="fieldDescription">Critical: Problems that impact all users or business critical processes.<br>
								High: Problems that impact groups of users or essential functionality.<br>
								Medium: Problems that impact a single user or systems that are not related to daily business operations.<br>
								Low: Problems that partially impact a single user or systems that are not related to daily business operations. </div>
							</td>
						</tr>
						<tr class="fieldArea" id="customfield_10023FieldArea">
							<td class="fieldLabelArea">
								<label for="customfield_10023">Business impact details:</label>
							</td>
							<td class="fieldValueArea">
								<textarea name="customfield_10023" id="customfield_10023" rows="4" wrap="virtual" cols="40" class="textarea"><%=(null!=customfield_10023)?customfield_10023:""%></textarea>
								<div class="fieldDescription">Please explain how you have come to the business impact given.</div>
							</td>
						</tr>
<%
                                        	if( sbmt != null && (customfield_10031 == null || customfield_10031.isEmpty()) ){
%>
                                                <tr class="formErrors formErrorArea">
                                                        <td>&nbsp;</td>
                                                        <td>
                                                                <span class="errMsg">Urgency is required.</span>
                                                        </td>
                                                </tr>
<%
											}
%>
						<tr class="fieldArea" id="customfield_10031FieldArea">
							<td class="fieldLabelArea <%=(sbmt != null && (customfield_10031 == null || customfield_10031.isEmpty()))?"formErrors":""%>">
								<label for="customfield_10031">
									<span class="errMsg" style="font-weight:normal;" title="Fields in italics are required"><sup>*</sup>Urgency:</span>
								</label>
							</td>
							<td class="fieldValueArea <%=(sbmt != null && (customfield_10031 == null || customfield_10031.isEmpty()))?"formErrors":""%>">
								<select name="customfield_10031" id="customfield_10031">
									<option value="">None</option>
									<option value="Urgent" <%=(null!=customfield_10031 && customfield_10031.equals("Urgent"))?"selected":""%>>Urgent</option>
									<option value="High" <%=(null!=customfield_10031 && customfield_10031.equals("High"))?"selected":""%>>High</option>
									<option value="Medium" <%=(null!=customfield_10031 && customfield_10031.equals("Medium"))?"selected":""%>>Medium</option>
									<option value="Low" <%=(null!=customfield_10031 && customfield_10031.equals("Low"))?"selected":""%>>Low</option>
								</select>
							</td>
						</tr>
						<tr class="fieldArea" id="customfield_10032FieldArea">
							<td class="fieldLabelArea">
								<label for="customfield_10032">Urgency details:</label>
							</td>
							<td class="fieldValueArea">
								<textarea name="customfield_10032" id="customfield_10032" rows="4" wrap="virtual" cols="40" class="textarea"><%=(null!=customfield_10032)?customfield_10032:""%></textarea>
							</td>
						</tr>
						<tr class="fieldArea" id="attachmentFieldArea">
							<td class="fieldLabelArea">
								<label for="attachment">Attachment:</label>
							</td>
							<td class="fieldValueArea">
<script type="text/javascript" language="JavaScript">
function openAnother(index, nextIndex)
{
    var currentBox = document.getElementById('attachment_box.' + index);
    var currentValue = currentBox.value;
    if (currentValue != null)
    {
        currentValue = currentValue.replace(/^[\s]+/g,"");
        currentValue = currentValue.replace(/[\s]+$/g,"");

        if (currentValue.length > 0)
        {
            var nextDiv = document.getElementById('attachment_div.' + nextIndex);
            if (nextDiv != null)
            {
                nextDiv.style.display = 'block';
            }
        }
    }
}
</script>
								<div id="attachment_div.1">
									<input id="attachment_box" type="file" name="attachment_field" value="" size="50"><br>
								</div>
								<span class="small">The maximum file upload size is <%=Math.round(attchMaxSize/1024/1024)%>MB. Please zip files larger than this or if you have more than one.</span><br>
							</td>
						</tr>
						<tr class="fieldArea" id="customfield_10171FieldArea"> 
							<td  class="fieldLabelArea" > 
								<label for="customfield_10171">Service Desk Escalated:</label> 
							</td> 
							<td class="fieldValueArea"> 
								<select name="customfield_10171" id="customfield_10171"> 
									<option value="-1">None</option> 
									<option value="No" selected>No</option> 
									<option value="Yes">Yes</option> 
								</select> 
							</td> 
						</tr>
						<tr class="fieldArea" id="customfield_10172FieldArea"> 
							<td  class="fieldLabelArea" > 
								<label for="customfield_10172">Service Desk Line:</label> 
							</td> 
							<td class="fieldValueArea"> 
								<select name="customfield_10172" id="customfield_10172"> 
									<option value="-1">None</option> 
									<option value="1st line" selected>1st line</option> 
									<option value="2nd line">2nd line</option> 
									<option value="3rd line">3rd line</option> 
								</select> 
							</td> 
						</tr> 
						<tr class="hidden">
							<td>
								<input type="hidden" id="pid" name="pid" value="10093">
							</td>
						</tr>
						<tr class="hidden">
							<td>
								<input type="hidden" id="issuetype" name="issuetype" value="10">
							</td>
						</tr>
						<tr>
							<td class="jiraformfooter">&nbsp;</td>
							<td colspan="1">
								<input type="submit" name="Create" value="Create" id="create_submit" accesskey="s" title="Press Alt+s to submit form" class="spaced ">
								<input id="cancelButton" type="button" accesskey="`" title="Cancel (Alt + `)" name="default.jsp" value="Cancel" onclick="location.href=&#39;default.jsp&#39;">
							</td>
						</tr>
					</tbody>
				</table>
				</form>
<script language="javascript" type="text/javascript">
jQuery(function () {
AJS.$("form[name='jiraform'] :input:visible:first").focus();
});
</script>
				</div>
			</td>
		</tr>
	</tbody>
</table>
