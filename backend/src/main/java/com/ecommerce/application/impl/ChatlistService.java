package com.ecommerce.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.application.IChatlistService;
import com.ecommerce.domain.repository.IChatlistRepository;
import com.ecommerce.mapper.ChatListMapper;
@Service
public class ChatlistService implements IChatlistService{

	@Autowired
	private ChatListMapper chatListMapper;
	@Override
	public boolean insert(String roomName) {
		return chatListMapper.insert(roomName);
	}

	@Override
	public String select(String no) {
		return chatListMapper.select(no);
	}

	@Override
	public String selectno(String roomName) {
		return chatListMapper.selectno(roomName);
	}

}
