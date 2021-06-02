package com.RestApplication.project;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Class to create connection with database
 * @author Patrikas
 *
 */
public class EstablishConnection {
	
	public Connection connection = null;
	
	public EstablishConnection() {
		openConnection();
	}
	
	private void openConnection() {
		String connectionUrl = "jdbc:mysql://localhost:3306/krepšinisdb";
		String dbUserName = "root";
		String dbPassword = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(connectionUrl, dbUserName, dbPassword);
		} catch (Exception e) {
			System.out.println("Error opening connection: " + e.getMessage());
		}
	}
	
}
