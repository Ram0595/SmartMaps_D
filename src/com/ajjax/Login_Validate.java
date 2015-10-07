package com.ajjax;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;


public class Login_Validate {

	Connection con;
	PreparedStatement ps;
	String statement;
	
	JSONArray jsona;
	JSONObject json;
	
	public Login_Validate() {
		// TODO Auto-generated constructor stub
		con=null;
		ps=null;
		statement="";
		jsona=new JSONArray();
		json = new JSONObject();
	
	}
	
	
String loginUser(String username,String password) throws SQLException {
	

	try {
		Class.forName("com.mysql.jdbc.Driver");
		
		System.out.println("Driver Loaded");
		
		con=DriverManager.getConnection("jdbc:mysql://localhost/smart_maps","smartguy","smartguy");
		
		System.out.println("Connected To MySql");
		

		
		statement="SELECT user_name,password FROM users "
				+ "WHERE user_name='"+username+"' AND password='"+password+"';";
		ResultSet rs=ps.executeQuery();
		
		
		//ClientResponse response = resource.path("rest").path("vtn").path("addEmplyee")
		 //       .type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, mapper.writeValueAsString(employee)); 
		
		int count=0;
		while(rs.next())
		{
			count=count+1;
		}
		if(count==0)
		{
			
			System.out.println("No User Found!! ");
			
			
			json.append("status","nouser");
			
			ps.close();
			con.close();
			
			
			System.out.println(" Status  "+json.get("status"));
			System.out.println(json);
			jsona.put(json);
			String x=jsona.toString();
			
	       
	        return x;
			
	        
		}
		else
		{	 
			 String uuid = UUID.randomUUID().toString().substring(20);
			 System.out.println("TOKEN :  uuid = " + uuid);
		

				json.append("status","logged_in");
				json.append("token", uuid);
			
			System.out.println(" User Found!!");
			
			
			ps.close();
			con.close();
			
			System.out.println(json);
			System.out.println(" Status  "+json.get("status"));
			jsona.put(json);
			
			//ResponseBuilder responseBuilder = Response.created(uriInfo.getAbsolutePath().resolve("VAMSEEEE"));
			  //return responseBuilder.entity(jsona).build();
			
			String x=jsona.toString();
			
			
			return x; 
		}
		
		
		
		
		  
		
		
		//ps.close();
		//con.close();
		//return retString;
		
	}catch(Exception e) {
		e.printStackTrace();
		
	}
	finally {
		if(con!=null)
			con.close();
	}
	
	ps.close();
	con.close();

	
	return "";
	
}
}
