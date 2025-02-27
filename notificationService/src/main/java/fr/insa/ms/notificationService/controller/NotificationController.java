package fr.insa.ms.notificationService.controller;

import java.sql.Connection;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.ms.notificationService.config.DatabaseConnection;
import fr.insa.ms.notificationService.model.Notification;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
	
	@PostMapping("/create")
	public void addNotification(@RequestBody Notification notification) throws SQLException {
		String query = "INSERT INTO Notification (id, idUser, message, status, date) VALUES (?, ?, ?, ?, ?)";
		Connection connection = DatabaseConnection.getConnection();
	    
		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        	Random random = new Random();
        	int randomId = random.nextInt(1_000_000) + 1; 
        	notification.setId(randomId);
        	
        	String unread = "UNREAD";
        	notification.setStatus(unread);
        	
        	statement.setInt(1, notification.getId());
            statement.setInt(2, notification.getIdUser());
            statement.setString(3, notification.getMessage());
            statement.setString(4,  notification.getStatus());
            statement.setString(5, notification.getDate());
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Notification added successfully");
            } else {
                System.out.println("Failed to add notification");
            }
		} catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
	}
	
	@GetMapping("/{idUser}")
	public List<Notification> getNotificationOfUser(@PathVariable int idUser) throws SQLException {
		String query = "SELECT * FROM Notification WHERE idUser = ?";
		
		List<Notification> notifications= new ArrayList<>();
		
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idUser);
				try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	            	Notification notification = new Notification();
	            	notification.setId(resultSet.getInt("id"));
	            	notification.setIdUser(resultSet.getInt("idUser"));
	            	notification.setMessage(resultSet.getString("message"));
	            	notification.setStatus(resultSet.getString("status"));
	            	notification.setDate(resultSet.getString("date"));

	            	notifications.add(notification);
	            }
				}
	    }
	    return notifications;
	}
	

}
