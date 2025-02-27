package fr.insa.ms.userInfoService.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.ms.userInfoService.config.DatabaseConnection;
import fr.insa.ms.userInfoService.model.UserInfos;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/all")
    public List<UserInfos> getAllUsers() throws SQLException {
        List<UserInfos> users = new ArrayList<>();
        String query = "SELECT * FROM UserInfos";
        
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
        	
        	 
            while (resultSet.next()) {
                users.add(new UserInfos(
                		resultSet.getInt("id"),
                        resultSet.getString("lastName"),
                        resultSet.getString("firstName"),
                        resultSet.getString("location"),
                        resultSet.getString("helpStatus"),
                        resultSet.getString("password")
                ));
            }
        }
        return users;
    }

    @GetMapping("/{id}")
    public UserInfos getUserById(@PathVariable int id) throws SQLException {
        String query = "SELECT * FROM UserInfos WHERE id = ?";      
        //Connection connection = DatabaseConnection.getConnection();
        try (Connection connection = DatabaseConnection.getConnection();
        	PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {
                return new UserInfos(
                		resultSet.getInt("id"),
                        resultSet.getString("lastName"),
                        resultSet.getString("firstName"),
                        resultSet.getString("location"),
                        resultSet.getString("helpStatus"),
                        resultSet.getString("password"));
            }
        }
        return null;
    }
    
    @PostMapping("/addUser")
    public void addNewUser(@RequestBody UserInfos UserInfos) throws SQLException {
    	
        Connection connection = DatabaseConnection.getConnection();
        
        try {
            String insertQuery = "INSERT INTO UserInfos (id, firstName, lastName, location, helpStatus, password) VALUES (?, ?, ?, ?, ?, ?)";
        
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
          
            	Random random = new Random();
            	int randomId = random.nextInt(1_000_000) + 1; 
            	UserInfos.setId(randomId);
            	
                preparedStatement.setInt(1, UserInfos.getId());
                preparedStatement.setString(2, UserInfos.getFirstName());
                preparedStatement.setString(3, UserInfos.getLastName());
                preparedStatement.setString(4, UserInfos.getLocation());
                preparedStatement.setString(5, UserInfos.getHelpStatus());
                preparedStatement.setString(6, UserInfos.getPassword());

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println("User added successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
	
	@GetMapping("/{lastName}/{firstName}")
    public UserInfos getIdByName(@PathVariable String lastName, @PathVariable String firstName) throws SQLException {
		Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM UserInfos WHERE lastName = ? AND firstName = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                   return  new UserInfos(resultSet.getInt("id"), resultSet.getString("lastName"),
                		   resultSet.getString("firstName"), 
                		   resultSet.getString("location"),
                		   resultSet.getString("helpStatus"), 
                		   resultSet.getString("password")
                		   );

                } else {
                    System.out.println("No matching user found.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException :" + e.getMessage());
            return null;
        }
    }
}
