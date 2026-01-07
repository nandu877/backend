package com.blog.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;



@Component
public class JwtUtils {
	@Value("${jwt.secret}")
	private String secret;
	
	public String generateToken(String email) {
		return JWT.create().withSubject(email)
					.withIssuedAt(new Date())
					.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60))
					.sign(Algorithm.HMAC256(secret));
		
	}
	
	public boolean validateToken(String token) {
		try {
			JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String extractEmail(String token) {
		return JWT.require(Algorithm.HMAC256(secret)).build().verify(token).getSubject().toString();
	}
}
