package com.dhanabal.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(Long userId) {

		return Jwts.builder().setSubject(String.valueOf(userId)).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();

	}

	public Long extractUserId(String token) {

		return Long.parseLong(Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
				.getBody().getSubject());

	}

	public boolean validateToken(String token) {

		try {

// ---->This is the problem in validating token
//			Jwts.parserBuilder()
//			.setSigningKey(getSigningKey())
//			.build()
//			.parseClaimsJwt(token);

			extractUserId(token);

//			Jwts.parserBuilder()
//			.setSigningKey(getSigningKey())
//			.build()
//			.parseClaimsJws(token)
//			.getBody()
//			.getSubject();

			return true;

		} catch (Exception e) {
			return false;
		}

	}

}