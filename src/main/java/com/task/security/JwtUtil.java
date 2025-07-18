package com.task.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.task.model.User;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expiration}")
	private long jwtExpiration;

	public String generateToken(User user) {

		Map<String, Object> claims = new HashMap<>();
		claims.put("role", user.getRole().name());

		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(user.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	public String extractUsername(String token) {
		return parseToken(token).getSubject();
	}

	public String extractRole(String token) {
		return parseToken(token).get("role", String.class);
	}

	public boolean isTokenValid(String token) {
		try {
			parseToken(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private Claims parseToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
