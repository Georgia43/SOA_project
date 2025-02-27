package fr.insa.ms.helpRequestService.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	 private static final String URL = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_064";
	 private static final String USER = "projet_gei_064";
	 private static final String PASSWORD = "ieCh9aes";


	 public static Connection getConnection() throws SQLException {
	 try {

		 Connection connect =DriverManager.getConnection(URL, USER, PASSWORD);
		 System.out.println("Connected to database: "+connect);
		 return connect;
	   
	   }
	 catch (SQLException e) {
	    throw new SQLException("Connection failed", e);
	    }
	 }
	 
	 public static void closeConnection() throws SQLException {
        try {
        	DriverManager.getConnection(URL, USER, PASSWORD).close();
            System.out.println("Disconnected from the database");
        } catch (SQLException e) {
        	throw new SQLException("Diconnection failed", e);
        }
    }
}


