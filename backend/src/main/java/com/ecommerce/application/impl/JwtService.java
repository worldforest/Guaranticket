package com.ecommerce.application.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ecommerce.application.IJwtService;
import com.ecommerce.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtService implements IJwtService {
	@Value("${jwt.salt}")
	private String salt;
	@Value("${jwt.expmin}")
	private Long expireMin;
	
	
	@Override
	public String create(long uid) {
		final JwtBuilder builder = Jwts.builder();
		// JWT Token = Header + Payload + Signature
		
		// Header Setting
		builder.setHeaderParam("typ", "JWT");
		
		// Payload Setting - claim 정보 포함
		builder.setSubject("LOGIN TOKEN")
		.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
		.claim("uid", uid);

		// signature - secret key 를 이용한 암호화
		builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());
		
		// 마지막 직렬화 처리
		final String jwt = builder.compact();
		
		return jwt;
	}

	@Override
	public void checkValid(String jwt) {
		Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
	}

	@Override
	public Map<String, Object> get(String jwt) {
		Jws<Claims> claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
		return claims.getBody();
	}

}
