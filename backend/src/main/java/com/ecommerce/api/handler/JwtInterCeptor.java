package com.ecommerce.api.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.ecommerce.application.impl.JwtService;

public class JwtInterCeptor implements HandlerInterceptor {

	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println(request.getMethod() + " : " + request.getServletPath());

		if (request.getMethod().equals("OPTIONS")) {
			return true;

		} else {
			String token = request.getHeader("jwt-auth-token");
			if (token != null && token.length() > 0) {
				jwtService.checkValid(token);
				return true;
			} else {
				throw new RuntimeException("인증 토큰이 없습니다.");
			}
		}

	}

}
