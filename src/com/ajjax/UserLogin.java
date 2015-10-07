package com.ajjax;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
@Path("loginclass/example")
public class UserLogin {
	
	Response resp=null;
	
	
	@Path("/ajax")
	@POST	
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response userService(
			@Context HttpHeaders hh,  //HttpHeader For Reading Cookies From Request
			//@Context UriInfo uriInfo,    
			@FormParam("username") String username,
			@FormParam("password") String password
			) {

		//String retString="";
		
		
		try {
			Map<String, Cookie> cookieMap = hh.getCookies();
			Set<String> keys=cookieMap.keySet();
			//String st;
			//if(!keys.isEmpty())
			//{
				if(keys.contains("status")) {
					for(String st:keys) { 
						if(cookieMap.get(st).getValue().equals("logged_in")&&!cookieMap.get("token").getValue().isEmpty())
						{
							// return already loggedin response
							JSONArray x=new JSONArray();
							JSONObject x2=new JSONObject();
							x2.put("status", "logged_in");
							resp=Response.ok(x).build();
							 
							 return resp;			
						}
					}
				}
				else { //if user not loggedin
					Login_Validate loginValidate=new Login_Validate();
					String x=loginValidate.loginUser(username,password);
					 resp=Response.ok(x).build();
					 
					 return resp;
					 
				}
			//}
			
			/*for(String s:keys) {
				System.out.println("Cooikie "+pathParams.get(s));
				
				System.out.println("Key "+s);
			}*/ 
			

		} catch(NullPointerException | SQLException | JSONException n) {
			n.printStackTrace();
		}
		
		
				return null;
     
     
	
	}
}







