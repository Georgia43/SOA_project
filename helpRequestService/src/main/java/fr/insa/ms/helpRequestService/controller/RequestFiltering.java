package fr.insa.ms.helpRequestService.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.ms.helpRequestService.config.DatabaseConnection;
import fr.insa.ms.helpRequestService.model.HelpRequest;

@RestController
@RequestMapping("/api/request-filtering")
public class RequestFiltering {
	
	  @GetMapping("/pending")
	    public List<HelpRequest> filterHelpRequests() throws SQLException {
		  String  query ="SELECT * FROM HelpRequest WHERE status = 'PENDING'";
	
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            try (ResultSet resultSet = statement.executeQuery()) {
	                List<HelpRequest> helpRequests = new ArrayList<>();
	                while (resultSet.next()) {
	                    HelpRequest helpRequest = new HelpRequest();
	                    helpRequest.setId(resultSet.getInt("id"));
	                    helpRequest.setIdUser(resultSet.getInt("idUser"));
	                    helpRequest.setContent(resultSet.getString("content"));
	                    helpRequest.setLocation(resultSet.getString("location"));
	                    helpRequest.setStatus(resultSet.getString("status"));
	                    helpRequest.setDate(resultSet.getString("date"));
	                    helpRequest.setIdHelper(resultSet.getInt("idHelper"));
	                    helpRequests.add(helpRequest);
	                }
	                return helpRequests;
	            }
	        }
	    }
	  
	  @GetMapping("/pending/location")
	  public List<HelpRequest> filterHelpRequestsByLocation(@RequestParam (required = false) String location) throws SQLException {
		  StringBuilder query = new StringBuilder("SELECT * FROM HelpRequest WHERE status = 'PENDING'");
	      
	      if (location != null && !location.isEmpty()) {
	          query.append(" AND location = ?");
	      }
	      
	      try (Connection connection = DatabaseConnection.getConnection();
	           PreparedStatement statement = connection.prepareStatement(query.toString())) {
	          if (location!=null && !location.isEmpty()) {
	        	  statement.setString(1, location);
	          }
	         
	          
	          try (ResultSet resultSet = statement.executeQuery()) {
	              List<HelpRequest> helpRequests = new ArrayList<>();
	              while (resultSet.next()) {
	                  HelpRequest helpRequest = new HelpRequest();
	                  helpRequest.setId(resultSet.getInt("id"));
	                  helpRequest.setIdUser(resultSet.getInt("idUser"));
	                  helpRequest.setContent(resultSet.getString("content"));
	                  helpRequest.setLocation(resultSet.getString("location"));
	                  helpRequest.setStatus(resultSet.getString("status"));
	                  helpRequest.setDate(resultSet.getString("date"));
	                  helpRequest.setIdHelper(resultSet.getInt("idHelper"));
	                  helpRequests.add(helpRequest);
	              }
	              return helpRequests;
	          }
	      }
	  }
	  
	  @GetMapping("/pending/date")
	  public List<HelpRequest> filterByDate(@RequestParam(required = false) String date) throws SQLException {
	      StringBuilder query = new StringBuilder("SELECT * FROM HelpRequest WHERE status = 'PENDING'");
	      
	      if (date != null && !date.isEmpty()) {
	          query.append(" AND date = ?");
	      }

	      try (Connection connection = DatabaseConnection.getConnection();
	           PreparedStatement statement = connection.prepareStatement(query.toString())) {

	          if (date != null && !date.isEmpty()) {
	              statement.setString(1, date);
	          }

	          try (ResultSet resultSet = statement.executeQuery()) {
	              List<HelpRequest> helpRequests = new ArrayList<>();
	              while (resultSet.next()) {
	                  HelpRequest helpRequest = new HelpRequest();
	                  helpRequest.setId(resultSet.getInt("id"));
	                  helpRequest.setIdUser(resultSet.getInt("idUser"));
	                  helpRequest.setContent(resultSet.getString("content"));
	                  helpRequest.setLocation(resultSet.getString("location"));
	                  helpRequest.setStatus(resultSet.getString("status"));
	                  helpRequest.setDate(resultSet.getString("date"));
	                  helpRequest.setIdHelper(resultSet.getInt("idHelper"));
	                  helpRequests.add(helpRequest);
	              }
	              return helpRequests;
	          }
	      }
	  }


	}
