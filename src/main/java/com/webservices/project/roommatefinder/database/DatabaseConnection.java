package com.webservices.project.roommatefinder.database;

import java.sql.*;

public class DatabaseConnection {
	String URL = "jdbc:mysql://localhost:3306/roommatefinder";
	String USER = "root";
	String PWD = "";
	
	Connection con;
	
	public DatabaseConnection(){
		try {
			//Register JDBC Driver
			Class.forName("com.mysql.jdbc.Driver");
			//Open connection
			con = DriverManager.getConnection(URL, USER, PWD);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public int createUser(String userName, String password) throws Exception{
		System.out.println("START :: createUser");
		int userId = 0;
		String query = "INSERT INTO USERMASTER (USERNAME, PASSWORD) VALUES ('" + userName + "', '" + password + "')";
		//String query = "INSERT INTO USERMASTER (USERNAME, PASSWORD) VALUES ('user7', 'pwd')";
		//Create Statement
		PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ResultSet generatedKeys = null;
		//Execute Query
		int insertResult = stmt.executeUpdate();
		if(insertResult == 1)
		{
			//Populating ResultSet
			generatedKeys = stmt.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            userId = generatedKeys.getInt(1);
	        } else {
	            System.out.println("No generated key obtained.");
	        }
		}
		if(generatedKeys != null)
			generatedKeys.close();
		stmt.close();
		System.out.println("END :: createUser");
		return userId;
	}
	
	public boolean authenticateUser(String userName, String password) throws Exception{
		System.out.println("START :: authenticateUser");
		String query = "SELECT * FROM USERMASTER where USERNAME = ? AND PASSWORD = ?";
		//Create Statement
		PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, userName);
		stmt.setString(2, password);
		
		//Execute Query
		ResultSet result = null;
		result = stmt.executeQuery();
		int count = 0;
		if(result.next())
		{
			count++;
		}
		System.out.println("Users fetched: " + count);
		if(result != null)
			result.close();
		stmt.close();
		System.out.println("END :: authenticateUser");
		return count == 1 ? true : false;
	}
	
}
