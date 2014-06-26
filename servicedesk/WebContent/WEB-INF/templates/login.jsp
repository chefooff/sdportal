<%@ page import="
                 java.util.Properties,com.chefooff.servicedesk.resource.PropertyConf" 
%>
<%
Properties prop = PropertyConf.getPropfile();
%>
<table cellspacing="0" cellpadding="10" border="0" width="100%" height="512" bgcolor="#ffffff">
	<tbody>
		<tr>
			<td>
				<div class="intform">
				<%--
				<form action="./<?=(!empty($_GET['action'])?$conf['tabSwitch'][$_GET['action']].'?action='.$_GET['action']:'my_tickets.php?action='.MYTICKETS)?>" method="post" name="jiraform" onsubmit="if (this.submitted) return false; this.submitted = true; return true" enctype="multipart/form-data" class="aui gdt">
				<form action="${pageContext.request.contextPath}/Logon" method="post" name="jiraform" onsubmit="if (this.submitted) return false; this.submitted = true; return true" enctype="multipart/form-data" class="aui gdt">
				--%>
				<form action="${pageContext.request.contextPath}/Logon" method="post" name="jiraform" onsubmit="if (this.submitted) return false; this.submitted = true; return true" class="aui gdt">
				<table class="jiraform" border=0>
					<tbody>
						<tr class="titlerow">
							<td bgcolor="#369">
								<h3 style="color: #FFFFFF;"><%=prop.getProperty("msg.loginform")%></h3>
							</td>
						</tr>
						<tr class="descriptionrow">
							<td class="jiraformheade">
							
<%
							String errMsg = (String)request.getAttribute("errMsg");
							if ( null != errMsg ) {
								out.write("<div class=\"desc-wrap\">" + errMsg + "</div>");
								out.write("<div class=\"notify info\">" + errMsg + "</div>");
							}
%>
							</td>
						</tr>
						<tr class="ihatemyselffordoingthis">
							<td></td>
						</tr>
						<tr class="ihatemyselffordoingthis">
							<td>
							       <fieldset>
								    <div>
									<label id="usernamelabel" for="usernameinput" accesskey="u"><u>U</u>sername</label>
									<input class="text medium-field" type="text" id="usernameinput" name="os_username" tabindex="1">
									<span id="usernameerror" class="error"></span>

										    <span id="publicmodeoff" class="description"><span id="publicmodeoffmsg">Not a member? Contact an <a href="https://jira.melexis.com/jira/secure/Administrators.jspa" target="_parent">Administrator</a> to request an account.</span></span>
								    </div>
								    <div>
									<label id="passwordlabel" for="os_password" accesskey="p"><u>P</u>assword</label>
									<input class="text medium-field" type="password" name="os_password" id="os_password" tabindex="2">
								    </div>
								    <div id="captcha" style="display: none; "></div>
								    <div class="checkbox" id="rememberme">
									<input type="checkbox" name="os_cookie" id="os_cookie_id" tabindex="4" value="true">
									<label id="remembermelabel" for="os_cookie_id" accesskey="r"><u>R</u>emember my login on this computer</label>
								    </div>
								    <div class="submit">
									<input id="login" class="button" type="submit" value="Log In" tabindex="5">
									<a id="forgotpassword" href="https://jira.melexis.com/jira/secure/ForgotLoginDetails!default.jspa" target="_parent" style="display: none; ">Can't access your account?</a>
								    </div>
								</fieldset>

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
