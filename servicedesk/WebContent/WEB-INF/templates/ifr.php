<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">body,td,div,span,p{font-family:arial,sans-serif;}a {color:#0000cc;}a:visited {color:#551a8b;}a:active {color:#ff0000;}body{margin: 0px;padding: 0px;background-color:white;}</style>

</head>
<body dir="ltr" class="">
        
<link href="./portlet_files/com.atlassian.auiplugin-ajs.css" media="all" rel="stylesheet" type="text/css">
<link href="./portlet_files/com.atlassian.jira.gadgets-common.css" media="all" rel="stylesheet" type="text/css">
<link href="./portlet_files/global(1).css" media="all" rel="stylesheet" type="text/css">
<script src="./portlet_files/com.atlassian.auiplugin-ajs.js" type="text/javascript"></script>
<script src="./portlet_files/com.atlassian.jira.gadgets-common.js" type="text/javascript"></script>

<div class="gadget default">
	<div class="view">
		<div id="content" class="">
			    <form id="loginform" method="POST" action="./my_tickets?action=<?=MYTICKETS?>" name="loginform" class="aui gdt">
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
			    </form>
        	</div>
	</div>
</div>
</body>
</html>
