package com.ecommerce.mapper;

import java.util.List;


import com.ecommerce.domain.User;

public interface UserMapper {
	public List<User> list();
	public User getByEmail(String email);
	public User getById(long id);
	public long create(User user);
	public int update(User user);
	public void delete(long id);
}
