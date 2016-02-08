package com.mindtree.service;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.mindtree.jdbc.JdbcConnection;

@Path("/")
public class Service1 {
	
	public Connection connec;
	public Service1()
	{}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/display/{table}/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response display(InputStream incomingData,@PathParam("name") String name,@PathParam("table") String table)  {
		JSONObject jsonObject = new JSONObject();
		try {
	    connec=JdbcConnection.getConnectionObject();
		Statement stmt=connec.createStatement();
		String query = "select * from "+table+" where name='"+name+"'";
	    ResultSet rs =  stmt.executeQuery(query);
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columnsNumber = rsmd.getColumnCount();
	    
	    while (rs.next()) {
	        for (int i = 1;i <= columnsNumber; i++) {
	            String columnValue = rs.getString(i);
	            System.out.println(columnValue);
	            jsonObject.put(rsmd.getColumnName(i), columnValue);
	        }
	    }} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(""+jsonObject.toString()).build(); 
	}
	
	@GET
	@Path("/insertEmp/{name}/{age}/{gender}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response insertEmp(InputStream incomingData,@PathParam("name") String name,@PathParam("age") int age,@PathParam("gender") String gender)  {
		try {
	    connec=JdbcConnection.getConnectionObject();
		Statement stmt=connec.createStatement();
		String query = "insert into employee values('"+name+"',"+age+",'"+gender+"')";
		stmt.executeUpdate(query);
	  } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Response.status(200).build();
	}
	
	@GET
	@Path("/insertOrder/{name}/{description}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response insertOrder(InputStream incomingData,@PathParam("name") String name,@PathParam("description") String description)  {
		try {
	    connec=JdbcConnection.getConnectionObject();
		Statement stmt=connec.createStatement();
		String query = "insert into orders values('"+name+"','"+description+"')";
		stmt.executeUpdate(query);
	  } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(200).build();
	}
	
	
	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public Response displayS(InputStream incomingData) throws ClassNotFoundException, SQLException {
		String str="Hello World";
		return Response.status(200).entity(""+str).build();
	}

}
