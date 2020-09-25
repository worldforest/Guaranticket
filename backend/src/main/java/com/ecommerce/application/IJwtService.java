package com.ecommerce.application;

import java.util.Map;

import com.ecommerce.domain.User;

public interface IJwtService {
	
	public String create(final long uid);
	public void checkValid(final String jwt);
	public Map<String, Object> get(final String jwt);

}
