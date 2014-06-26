package com.chefooff.servicedesk.logon;

import java.io.*;
import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.servlet.*;
import javax.servlet.http.*;

import com.chefooff.servicedesk.resource.JiraRest;
import com.chefooff.servicedesk.resource.PropertyConf;
import com.chefooff.servicedesk.resource.json.JsonValues;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientHandlerException;

/**
 * Servlet implementation class ServiceDesk
 */
public class Logon extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.

		String jiraUser = null;
		String jiraPass = null;
		String errMsg   = null;
		String restResponse;
		
		Properties prop = PropertyConf.getPropfile();
		HttpSession session = request.getSession();
		
		if ( null != request.getParameter("action") && request.getParameter("action").equals("logout") ) {
			session.removeAttribute("jiraUser");
			session.removeAttribute("jiraPass");
			session.removeAttribute("displayName");
			session.removeAttribute("active");
			session.removeAttribute("emailAddress");
		}
		
		if ( "POST".equals(request.getMethod()) ) {
			session.setAttribute("jiraUser", request.getParameter("os_username").trim());
			session.setAttribute("jiraPass", request.getParameter("os_password").trim());
		}
		
		if ( session.getAttribute("jiraUser") != null && session.getAttribute("jiraPass") != null ){
			jiraUser = session.getAttribute("jiraUser").toString();
			jiraPass = session.getAttribute("jiraPass").toString();
			
			JiraRest jr = new JiraRest(jiraUser, jiraPass);
			try {
				restResponse = jr.invokeGetMethod("user?username=" + jiraUser );
				Gson gson = new Gson();
				JsonValues jsonValues = gson.fromJson(restResponse, JsonValues.class);
				
				//Add some additional session variables
				session.setAttribute("displayName", jsonValues.getDisplayName());
				session.setAttribute("active", jsonValues.getActive());
				session.setAttribute("emailAddress", jsonValues.getEmailAddress());
				
				//Redirect to default servlet
				response.sendRedirect(prop.getProperty("dfl.redirect"));
				
			} catch (AuthenticationException e) {
				e.printStackTrace();
				errMsg = e.getMessage();
			} catch (ClientHandlerException e) {
				e.printStackTrace();
				errMsg = prop.getProperty("msg.jiraNotAvailable");
			}
			
			request.setAttribute("errMsg", errMsg);
		}
		
		try {
			// Include common footer
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/templates/common-header.jsp");
        	dispatcher.include(request, response);

    		// Include body
        	dispatcher = request.getRequestDispatcher("/WEB-INF/templates/login.jsp");
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
