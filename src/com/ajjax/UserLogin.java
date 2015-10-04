package com.ajjax;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONArray;
import org.json.JSONObject;
@Path("loginclass/example")
public class UserLogin {
	
	Response resp=null;
	
	
	@Path("/ajax")
	@POST	
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response userService(
			@Context UriInfo uriInfo,
			@FormParam("username") String username,
			@FormParam("password") String password
			) throws SQLException {


		
		Connection con=null;
		PreparedStatement ps=null;
		String statement="";
		//String retString="";
		
		
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			System.out.println("Driver Loaded");
			
			con=DriverManager.getConnection("jdbc:mysql://localhost/smart_maps","smartguy","smartguy");
			
			System.out.println("Connected To MySql");
			

			
			statement="SELECT user_name,password FROM users "
					+ "WHERE user_name='"+username+"' AND password='"+password+"';";
			
			System.out.println("STATEMENT : "+statement);
			ps=con.prepareStatement(statement);
			
			ResultSet rs=ps.executeQuery();
			
			JSONArray jsona=new JSONArray();
			JSONObject json = new JSONObject();
			
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
				
		        resp=Response.ok(x).build();
		        return resp;
				
		        
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
				
				resp=Response.ok(x).build();
				return resp; 
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
		return null;
     
     
	
	}
}







