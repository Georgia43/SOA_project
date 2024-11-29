package fr.insa.ms.FeedbackService.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.ms.FeedbackService.Config.DatabaseConnection;
import fr.insa.ms.FeedbackService.model.Feedback;


@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
	
	 private final JdbcTemplate jdbcTemplate;

	 @Autowired
	 public FeedbackController(DataSource dataSource) {
	     this.jdbcTemplate = new JdbcTemplate(dataSource);
	 }

	
	@PostMapping("/create")
	public void addNotification(@RequestBody Feedback feedback) throws SQLException {
		String query = "INSERT INTO Feedback (id, idUser, content, rating) VALUES (?, ?, ?, ?)";
		//Connection connection = DatabaseConnection.getConnection();
	    
		//try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        	Random random = new Random();
        	int randomId = random.nextInt(1_000_000) + 1; 
        	feedback.setId(randomId);
        	
        	try {
                int rowsAffected = jdbcTemplate.update(query, feedback.getId(), feedback.getIdUser(), feedback.getContent(), feedback.getRating());
                if (rowsAffected > 0) {
                    System.out.println("Feedback added successfully.");
                } else {
                    System.out.println("No rows were inserted. Possible issue with the query or data.");
                }
            } catch (Exception e) {
                System.err.println("An error occurred while adding feedback: " + e.getMessage());
                e.printStackTrace();
                
                throw new RuntimeException("Failed to add feedback. Please try again later.", e);
            }
        	
        	/*statement.setInt(1, feedback.getId());
            statement.setInt(2, feedback.getIdUser());
            statement.setString(3, feedback.getContent());
            statement.setInt(4,  feedback.getRating());
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Feedback added successfully");
            } else {
                System.out.println("Failed to add feedback");
            }
		} catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }*/
	}
	
	@GetMapping("/{idUser}")
	public List<Feedback> getFeedbackOfUser(@PathVariable int idUser) throws SQLException {
		String query = "SELECT * FROM Feedback WHERE idUser = ?";
		
		//List<Feedback> feedbackList= new ArrayList<>();
		
		/*try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idUser);
				try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	            	Feedback feedback = new Feedback();
	            	feedback.setId(resultSet.getInt("id"));
	            	feedback.setIdUser(resultSet.getInt("idUser"));
	            	feedback.setContent(resultSet.getString("content"));
	            	feedback.setRating(resultSet.getInt("rating"));

	            	feedbackList.add(feedback);
	            }
				}
	    }*/
		
		RowMapper<Feedback> rowMapper = (resultSet, rowNum) -> {
	        Feedback feedback = new Feedback();
	        feedback.setId(resultSet.getInt("id"));
	        feedback.setIdUser(resultSet.getInt("idUser"));
	        feedback.setContent(resultSet.getString("content"));
	        feedback.setRating(resultSet.getInt("rating"));
	        return feedback;
	    };
	    
	    try {
	        List<Feedback> feedbackList = jdbcTemplate.query(query, rowMapper, idUser);

	        if (feedbackList.isEmpty()) {
	            System.out.println("No feedback found for this user: " + idUser);
	        }

	        return feedbackList;

	    } catch (Exception e) {
	        System.err.println("Error while retrieving feedback: " + e.getMessage());
	        e.printStackTrace();
	        return new ArrayList<>(); 
	    }
	}
}
