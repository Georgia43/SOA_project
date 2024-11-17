package fr.insa.ms.helpRequestService.model.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.ms.helpRequestService.config.DatabaseConnection;
import fr.insa.ms.helpRequestService.model.HelpRequest;

@RestController
@RequestMapping("/api/help-request")
public class RequestController {

    @PostMapping("/create")
    public void addNewRequest(@RequestBody HelpRequest helpRequest) throws SQLException {
        String query = "INSERT INTO HelpRequest (id, idUser, idHelper, content, location, status, date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        	Random random = new Random();
        	int randomId = random.nextInt(1_000_000) + 1; 
        	helpRequest.setId(randomId);
        	
        	String pending = "PENDING";
        	helpRequest.setStatus(pending);
        	
        	statement.setInt(1, helpRequest.getId());
            statement.setInt(2, helpRequest.getIdUser());
            statement.setInt(3, 0);
            statement.setString(4, helpRequest.getContent());
            statement.setString(5, helpRequest.getLocation());
            statement.setString(6, helpRequest.getStatus());
            statement.setString(7, helpRequest.getDate());

            //int rowsAffected = statement.executeUpdate();
           
            System.out.println("Request added successfully");
        
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }


    // accept a request
    @PutMapping("/accept/{id}")
    public void acceptRequest(@PathVariable int id, @RequestParam int idHelper) throws SQLException {
        String query = "UPDATE HelpRequest SET idHelper = ?, status = ? WHERE id = ?";
        Connection connection = DatabaseConnection.getConnection();
        
        try (PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, idHelper);  
            statement.setString(2, "ACCEPTED"); 
            statement.setLong(3, id);  

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Request updated.");
             } else {
                throw new SQLException("Help request not found with ID: " + id);
            }
        }
    }

    @GetMapping("/{id}")
    public HelpRequest getRequestById(@PathVariable int id) throws SQLException {
        String query = "SELECT * FROM HelpRequest WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                	HelpRequest helpRequest = new HelpRequest();
                	helpRequest.setId(resultSet.getInt("id"));
                	helpRequest.setIdUser(resultSet.getInt("idUser"));
                	helpRequest.setContent(resultSet.getString("content"));
                	helpRequest.setDate(resultSet.getString("date"));
                	helpRequest.setStatus(resultSet.getString("status"));
                    helpRequest.setIdHelper(resultSet.getInt("idHelper"));
                    return helpRequest;
                } else {
                    throw new SQLException("Help request not found with ID: " + id);
                }
            }
        }
    }

    //get all help requests
    @GetMapping("/all")
    public List<HelpRequest> getAllRequests() throws SQLException {
        List<HelpRequest> requests = new ArrayList<>();
        String query = "SELECT * FROM HelpRequest";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
            	HelpRequest helpRequest = new HelpRequest();
            	helpRequest.setId(resultSet.getInt("id"));
            	helpRequest.setIdUser(resultSet.getInt("idUser"));
            	helpRequest.setContent(resultSet.getString("content"));
            	helpRequest.setDate(resultSet.getString("date"));
            	helpRequest.setStatus(resultSet.getString("status"));
                helpRequest.setIdHelper(resultSet.getInt("idHelper"));
                requests.add(helpRequest);
            }
        }
        return requests;
    }
}
