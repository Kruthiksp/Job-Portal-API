package com.kruthik.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24;
	private static final String SECRET = "My-super-secret-key-is-Job-portal-API";
	private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

	public String generateToken(String username) {
		return Jwts.builder()
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
				.signWith(secretKey)
				.compact();
	}
	
	public Claims extractClaims(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
	}

	public String extractUsernameFromToken(String token) {
		return extractClaims(token).getSubject();
	}
	
	public boolean validateToken(UserDetails user, String username, String token) {
		boolean equals = username.equals(user.getUsername());
		boolean isTokenExpired = isTokenExpired(token);
		return equals && !isTokenExpired;
	}
	
	public boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date());
	}

}
