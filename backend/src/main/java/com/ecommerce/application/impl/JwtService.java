package com.ecommerce.application.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ecommerce.application.IJwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService implements IJwtService {

	@Value("${jwt.salt}")
	private String salt;
	
	@Value("${jwt.expmin}")
	private Long expireMin;
	
	@Override
	public String create(long uid) {
		JwtBuilder builder = Jwts.builder();
		builder.setHeaderParam("typ", "JWT");
		
		builder.setSubject("LOGIN TOKEN")
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
				.claim("USER", uid);
		
		
		builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());
		
		String jwt = builder.compact();
		return jwt;
	}

	@Override
	public void checkValid(String jwt) {
		Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);

	}

	@Override
	public Map<String, Object> get(String jwt) {
		Jws<Claims> claims = null;
		claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
		return claims.getBody();
	}

}
