package com.webservices.project.roommatefinder.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.webservices.project.roommatefinder.database.DatabaseConnection;
import com.webservices.project.roommatefinder.model.*;

@Path("register")
public class Registration {
	
	DatabaseConnection dbConnect = new DatabaseConnection();
	/*
	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public User fetchUser(@PathParam("userId") String userId){
		return "User registered " + userId;
	}*/
	
	@POST
	@Path("/signin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String authenticateUser(User user){
		boolean isLegitUser = false;
		try{
			isLegitUser = dbConnect.authenticateUser(user.getUserName(), user.getPassword());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "User registered with " + isLegitUser + " : " + user.getPassword();
	}
	
	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User registerUser(User user){
		int userId = 0;
		try{
			userId = dbConnect.createUser(user.getUserName(), user.getPassword());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		User newUser = new User();
		newUser.setUserId(userId);
		newUser.setPassword(user.getPassword());
		newUser.setUserName(user.getUserName());
		return newUser;
	}
	
}
