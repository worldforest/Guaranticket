package com.ecommerce.application;

import java.util.Map;

import com.ecommerce.domain.User;

public interface IJwtService {
	public String create(long uid);
	public void checkValid(String jwt);
	public Map<String, Object> get(String jwt);
}
