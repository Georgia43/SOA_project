package fr.insa.ms.authentificationService.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwUtil {
	
	 public static String generateToken(int userId) {
	        return JWT.create()
	                .withSubject(String.valueOf(userId))  // Set user ID as the subject
	                .withIssuedAt(new Date())  // Set issued date
	                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // Set expiration (1 hour)
	                .sign(Algorithm.HMAC256("secret_key"));  // Sign with the secret key
	    }

}
