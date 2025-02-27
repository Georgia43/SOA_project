package fr.insa.ms.authentificationService.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fr.insa.ms.authentificationService.model.Authentification;
import fr.insa.ms.authentificationService.model.UserInfos;
import fr.insa.ms.authentificationService.config.JwUtil;


@RestController
@RequestMapping("/api/authentification")
public class AuthentificationController {

	
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping("/register")
	public void registerUser(@RequestBody Authentification authentification) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder);
        String hashedPassword = encoder.encode(authentification.getPassword());
        System.out.println(hashedPassword);
        
        Map<String, Object> user = new HashMap<>();
        user.put("firstName",authentification.getFirstName());
        user.put("lastName", authentification.getLastName());
        user.put("password", hashedPassword);
        user.put("helpStatus", authentification.getHelpStatus());
        user.put("location", authentification.getLocation());
        restTemplate.postForObject("http://userInfoService/api/users/addUser", user, Void.class);
        
        System.out.println("user registered successfully");
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Authentification authentification) {
		String url = "http://userInfoService/api/users/{lastName}/{firstName}";
		UserInfos user = restTemplate.getForObject(url, UserInfos.class, authentification.getLastName(), authentification.getFirstName());
		 if (user != null && new BCryptPasswordEncoder().matches(authentification.getPassword(), user.getPassword())) {
	            // Generate JWT token if credentials are valid
	            String token = JwUtil.generateToken(user.getId());
	            return "Bearer " + token;
	        }
	        return "Invalid credentials";
	}
	
	

	
}
