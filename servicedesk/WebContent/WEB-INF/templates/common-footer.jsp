<%@ page import="
                 java.util.Properties,com.chefooff.servicedesk.resource.PropertyConf" 
%>
<%
Properties prop = PropertyConf.getPropfile();
%>
<div class="footer">
	<div class="poweredbymessage">
		<%=prop.getProperty("msg.footer")%>
	</div>
</div>
<div id="inline-dialog-shadow" style="display: none; ">
	<div class="tl"></div>
	<div class="tr"></div>
	<div class="l"></div>
	<div class="r"></div>
	<div class="bl"></div>
	<div class="br"></div>
	<div class="b"></div>
</div>
<div class="shadow" style="margin-top: -100px; margin-left: -216px; width: 432px; height: 229px; display: none; ">
	<div class="tl"></div>
	<div class="tr"></div>
	<div class="l" style="height: 183px; "></div>
	<div class="r" style="height: 183px; "></div>
	<div class="bl"></div>
	<div class="br"></div>
	<div class="b" style="width: 374px; "></div>
</div>
<div class="popup" id="delete-dshboard" style="margin-top: -100px; margin-left: -200px; width: 400px; height: 200px; background-image: initial; background-attachment: initial; background-origin: initial; background-clip: initial; background-color: rgb(255, 255, 255); display: none; background-position: initial initial; background-repeat: initial initial; "></div>
<div id="inline-dialog-create_issue_popup" class="aui-inline-dialog">
	<div class="contents" style="width: 250px; "></div>
	<div id="arrow-create_issue_popup" class="arrow"></div>
</div>
</body>
</html>
