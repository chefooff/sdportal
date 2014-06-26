package com.chefooff.servicedesk.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import javax.servlet.http.Part;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.multipart.MultiPart;
import com.sun.jersey.multipart.MultiPartMediaTypes;
import com.sun.jersey.multipart.impl.MultiPartWriter;

public class JiraRest {

	private WebResource webResource;
	private Client client;
	private ClientConfig cc;
	static Properties prop;
	private String authBase64;
	private String jiraURL;
	private String jiraAPI;
	String action = "";
		
	public JiraRest(String jiraUser, String jiraPass) throws FileNotFoundException, IOException {
		
		authBase64 = new String(Base64.encode(jiraUser+":"+jiraPass));
		
		//Get configuration properties
		prop = PropertyConf.getPropfile();
		jiraURL = prop.getProperty("jira.srv." + SrvEnv.getEnv());
		jiraAPI = prop.getProperty("jira.api");
		
		try {
			//Create client
			cc = new DefaultClientConfig();
			
			TrustManager[] trustAllCerts = new TrustManager[] {
					new X509TrustManager() {
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {return null;}
						public void checkClientTrusted(X509Certificate[] certs, String authType) {}
						public void checkServerTrusted(X509Certificate[] certs, String authType) {}
					}
			};

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			
			cc.getClasses().add(MultiPartWriter.class);
			
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
		client = Client.create(cc);
	}

	public String invokeGetMethod(final String jiraRes) throws ClientHandlerException, AuthenticationException {
		
		ClientResponse response = createBuilder(jiraRes).get(ClientResponse.class);
		checkResponseStatus(response, jiraRes);
		
	    return response.getEntity(String.class);
	}

	public String invokePostMethod(final String jiraRes, final MultiPart data) throws ClientHandlerException, AuthenticationException {
		
		ClientResponse response = createBuilder(jiraRes).post(ClientResponse.class, data);
		checkResponseStatus(response, jiraRes);
		
	    return response.getEntity(String.class);
	}
	
	public String invokePostMethod(final String jiraRes, final String data) throws ClientHandlerException, AuthenticationException {
		
		ClientResponse response = createBuilder(jiraRes).post(ClientResponse.class, data);
		checkResponseStatus(response, jiraRes);
		
	    return response.getEntity(String.class);
	}
	
	private Builder createBuilder(final String jiraRes){
		webResource = client.resource(jiraURL+jiraAPI+jiraRes);
		
		//attachments
		if( jiraRes.contains("attachments") ) action = "attach";
		
		switch(action){
			case "attach":
				return webResource.type(MultiPartMediaTypes.createFormData())
						   .header("Authorization", "Basic " + authBase64)
				 		   .header("X-Atlassian-Token", "nocheck");
		default:
				return webResource.type("application/json")
						   .header("Authorization", "Basic " + authBase64)
						   .accept("application/json");
		} 
	}
	
	private void checkResponseStatus(ClientResponse response, final String jiraRes) throws ClientHandlerException, AuthenticationException {

		Integer httpStat = response.getStatus();
		
		switch(jiraRes){
			case "issue":
				if (httpStat != 201) {
					throw new ClientHandlerException(prop.getProperty("msg.failedIssueCr"));
				}
				break;
			default:
				if (httpStat != 200) {
		
			    	//Check for auth response
			    	switch(response.getHeaders().get("X-Seraph-LoginReason").toString()){
			    		case "[AUTHENTICATION_DENIED]":
			    			throw new AuthenticationException(prop.getProperty("msg.captcha"));
			    		case "[OK, OUT]":
			    			///Triabva da go opravia - tuk ne trqbva da e authExeption
			    			throw new AuthenticationException(prop.getProperty("msg.noFindIssues"));
			    		case "[AUTHENTICATED_FAILED, AUTHENTICATED_FAILED, OUT]":
			    		default:
			    			throw new AuthenticationException(prop.getProperty("msg.authErr"));
			    	}
			    }
		}
	}
	
	public String getFilename(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}
}
