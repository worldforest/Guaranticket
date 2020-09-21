package com.ecommerce.application.impl;

import com.ecommerce.application.IUserService;
import com.ecommerce.domain.User;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.repository.IUserRepository;
import com.ecommerce.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

	private IUserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	public UserService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> list() {
		return this.userMapper.list();
	}

	@Override
	public User get(long id) {
		return this.userMapper.getById(id);
	}

	@Override
	public User get(String email) {
		return this.userMapper.getByEmail(email);
	}

	@Override
	public User add(User user) {
		this.userMapper.create(user);
		return this.userMapper.getById(user.getId());
	}

	@Override
	public User update(User user) {
		User found = this.userMapper.getByEmail(user.getEmail());
		if (found == null)
			throw new ApplicationException("회원 정보를 찾을 수 없습니다.");
		int affected = this.userMapper.update(user);
		if (affected == 0)
			throw new ApplicationException("회원정보수정 처리가 반영되지 않았습니다.");

		return this.userMapper.getById(user.getId());
	}

	@Override
	public void delete(long id) {
		System.out.println(id);
		this.userMapper.delete(id);
	}
}
