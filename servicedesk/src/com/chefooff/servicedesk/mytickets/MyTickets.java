package com.chefooff.servicedesk.mytickets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.servlet.*;
import javax.servlet.http.*;

import com.chefooff.servicedesk.resource.*;
import com.sun.jersey.api.client.ClientHandlerException;

/**
 * Servlet implementation class MyTickets
 */
public class MyTickets extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
		
		String jiraUser       = null;
		String jiraPass       = null;
		String errMsg         = null;
		String restResponse   = null;
		String jql            = null;
		String jqlFields      = null;
		String jqlParameters  = null;
		String restDefResource = null; //Get default rest api resource
		String[] myFilters;
		
		Properties prop = PropertyConf.getPropfile();
		HttpSession session = request.getSession();
		ArrayList<HtmlGenerator> htmlFilterGen = new ArrayList<HtmlGenerator>();
		
		if ( session.getAttribute("jiraUser") == null && session.getAttribute("jiraPass") == null ){
			response.sendRedirect("./Logon");
		} else {
			jiraUser = session.getAttribute("jiraUser").toString();
			jiraPass = session.getAttribute("jiraPass").toString();
				
			JiraRest jr = new JiraRest(jiraUser, jiraPass);

			try{			
				jqlFields = prop.getProperty("jql.fields");
				jqlParameters = prop.getProperty("jql.parameters");
				restDefResource = prop.getProperty("jql.default.resource");
				myFilters = prop.getProperty("jql.my.issues.list").toString().split(",");				
				for (String fName : myFilters) {
					jql = prop.getProperty("jql.q."+fName);
					restResponse = jr.invokePostMethod(restDefResource, "{"+jql+jqlParameters+jqlFields+"}" );
					htmlFilterGen.add(new HtmlGenerator(restResponse, fName));
				}
				
			} catch (AuthenticationException e) {
				e.printStackTrace();
				errMsg = e.getMessage();
			} catch (ClientHandlerException e) {
				e.printStackTrace();
				errMsg = prop.getProperty("msg.jiraNotAvailable");
			}
			
			request.setAttribute("errMsg", errMsg);
			request.setAttribute("htmlFgen", htmlFilterGen);
			request.setAttribute("displayName",session.getAttribute("displayName"));
		}

		try {
			
			// Include common header
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/templates/common-header.jsp");
			dispatcher.include(request, response);
    	
			// Include body
			dispatcher = request.getRequestDispatcher("/WEB-INF/templates/my_tickets.jsp");
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