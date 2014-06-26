package com.chefooff.servicedesk.createticket;


import java.io.IOException;
import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.ws.rs.core.MediaType;

import com.chefooff.servicedesk.resource.JiraRest;
import com.chefooff.servicedesk.resource.PropertyConf;
import com.chefooff.servicedesk.resource.json.JsonValues;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.MultiPart;

/**
 * Servlet implementation class CreateTicket
 */

//TODO napravi dolmite parametri da sa conf options
@MultipartConfig(fileSizeThreshold=1024*1024*45,    // 10 MB 
                 maxFileSize=1024*1024*50,          // 50 MB
                 maxRequestSize=1024*1024*100)      // 100 MB
public class CreateTicket extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
		
		JsonValues jsonValuesComponents = null;
		String jiraUser                 = null;
		String jiraPass                 = null;
		String jiraSDProject            = null;
		String errMsg                   = null;
		String getComponents            = null;
		String attchMaxSize             = null;
		String issueKey                 = null;
		final String FILE_ATTACHMENT_CONTROL_NAME = "file";
		
		Properties prop = PropertyConf.getPropfile();
		HttpSession session = request.getSession();

		if ( session.getAttribute("jiraUser") == null && session.getAttribute("jiraPass") == null ){
			response.sendRedirect("./Logon");
		} else {
			jiraUser      = session.getAttribute("jiraUser").toString();
			jiraPass      = session.getAttribute("jiraPass").toString();
			jiraPass      = session.getAttribute("jiraPass").toString();
			jiraSDProject = prop.getProperty("srvdsk.prjct");
			
			JiraRest jr   = new JiraRest(jiraUser, jiraPass);
			Gson gson     = new Gson();
			
			try{
				getComponents = "{\"components\":" + jr.invokeGetMethod("project/"+jiraSDProject+"/components") + "}";
				jsonValuesComponents = gson.fromJson(getComponents, JsonValues.class);
				attchMaxSize = gson.fromJson(jr.invokeGetMethod("attachment/meta"),JsonValues.class).uploadLimit();
			} catch (AuthenticationException e) {
				e.printStackTrace();
				errMsg = e.getMessage();
			} catch (ClientHandlerException e) {
				e.printStackTrace();
				errMsg = prop.getProperty("msg.jiraNotAvailable");
			}
			
			String sbmt = request.getParameter("sbmt");
			if( sbmt != null && request.getParameter("action") != null ){

				String customfield_10081 = request.getParameter("customfield_10081"); //Location
				String summary           = request.getParameter("summary");
				String customfield_10022 = request.getParameter("customfield_10022"); //Business impact
				String customfield_10031 = request.getParameter("customfield_10031"); //Urgency				
				String description       = request.getParameter("description");
				String components        = request.getParameter("components");
				String customfield_10023 = request.getParameter("customfield_10023"); //Business impact details
				String customfield_10032 = request.getParameter("customfield_10032"); //Urgency details
				String customfield_10033 = request.getParameter("customfield_10033"); //Requested date
				String customfield_10171 = request.getParameter("customfield_10171"); //Service Desk Escalated
				String customfield_10172 = request.getParameter("customfield_10172"); //Service Desk Line

				if( summary != null && !summary.isEmpty() && 
						customfield_10081 != null && !customfield_10081.isEmpty() &&
							customfield_10031 != null && !customfield_10031.isEmpty() &&
								customfield_10022 != null && !customfield_10022.isEmpty()  ){
					
					StringBuffer issueInput = new StringBuffer();
					
					//Creation json string with all required fields
					issueInput.append("{"
										+ "\"fields\":"
											 + "{\"project\":{\"key\":\""+jiraSDProject+"\"},"
											 + "\"summary\":\""+summary+"\","
											 + "\"issuetype\":{\"name\":\"Call\"},"
											 + "\"customfield_10081\":\""+customfield_10081+"\","
											 + "\"customfield_10022\":{\"value\":\""+customfield_10022+"\"},"
											 + "\"customfield_10031\":{\"value\":\""+customfield_10031+"\"}");
					
					if( description != null && !description.isEmpty() )
						issueInput.append(",\"description\":\""+description+"\"");
					
					if( components != null && !components.isEmpty() )
						issueInput.append(",\"components\":[{\"value\":\""+components+"\"}]");
					
					//Requested date
					if( customfield_10033 != null && !customfield_10033.isEmpty() )
						issueInput.append(",\"customfield_10033\":\""+customfield_10033+"\"");
					
					//Business impact details
					if( customfield_10023 != null && !customfield_10023.isEmpty() )
						issueInput.append(",\"customfield_10023\":\""+customfield_10023+"\"");
					
					//Urgency details
					if( customfield_10032 != null && !customfield_10032.isEmpty() )
						issueInput.append(",\"customfield_10032\":\""+customfield_10032+"\"");
					
					/*--START temporary commented section 
					//Service Desk Escalated
					//if( customfield_10171 != null && !customfield_10171.isEmpty() )
						//issueInput.append(",\"customfield_10171\":{\"value\":\""+customfield_10171+"\"}");
					
					//Service Desk Line
					//if( customfield_10172 != null && !customfield_10172.isEmpty() )
						//issueInput.append(",\"customfield_10172\":{\"value\":\""+customfield_10172+"\"}");
					--END temporary commented section*/
					
					issueInput.append("}}");
					
					//--START Issue creation phase
					try {
						String jiraIssueResponse = jr.invokePostMethod("issue", issueInput.toString());
						issueKey = gson.fromJson(jiraIssueResponse, JsonValues.class).getIssueKey();

					} catch (AuthenticationException | ClientHandlerException e1) {
						e1.printStackTrace();
						errMsg = prop.getProperty("msg.failedIssueCr");
					}
					//--END Issue creation phase
					
					//--START Attachments section
					try {
						Part filePart = request.getPart("attachment_field");
						
						if( filePart.getSize() > 0 ){
							if( Integer.parseInt(attchMaxSize) > filePart.getSize() ){
								BodyPart bp = new BodyPart(filePart.getInputStream(), MediaType.APPLICATION_OCTET_STREAM_TYPE);
								FormDataContentDisposition.FormDataContentDispositionBuilder dispositionBuilder = 
										FormDataContentDisposition.name(FILE_ATTACHMENT_CONTROL_NAME);
								dispositionBuilder.fileName(jr.getFilename(filePart));
					
								FormDataContentDisposition formDataContentDisposition = dispositionBuilder.build();
								bp.setContentDisposition(formDataContentDisposition);
					
								MultiPart multiPartInput = new MultiPart();
								multiPartInput.bodyPart(bp);
					
								jr.invokePostMethod("issue/"+issueKey+"/attachments", multiPartInput);
							} else errMsg = prop.getProperty("msg.AttachFileSize");
						}
					} catch (AuthenticationException | ClientHandlerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						errMsg = prop.getProperty("msg.crtsucc") + " " + prop.getProperty("msg.failedAttachFile");
					}
					//--END Attachments section
					
					//if there is no errors, redirect to MyTickets servlet
					if( errMsg == null ) response.sendRedirect("./MyTickets?action=mytickets&crtsucc=1");					
					
				} else errMsg = prop.getProperty("msg.missedFields");
			}
		}
		
		request.setAttribute("errMsg", errMsg);
		request.setAttribute("displayName",session.getAttribute("displayName"));
		request.setAttribute("jsonValues", jsonValuesComponents);
		request.setAttribute("attchMaxSize", attchMaxSize);
		
		try {
			
			// Include common header
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/templates/common-header.jsp");
			dispatcher.include(request, response);
    	
			// Include body
			dispatcher = request.getRequestDispatcher("/WEB-INF/templates/create_issue_call.jsp");
			dispatcher.include(request, response);
        
			// Include common footer
			dispatcher = request.getRequestDispatcher("/WEB-INF/templates/common-footer.jsp");
			dispatcher.include(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("errMsg", "");
	}
}
