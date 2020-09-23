package com.ecommerce.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.application.IChatlistService;
import com.ecommerce.domain.repository.IChatlistRepository;
@Service
public class ChatlistService implements IChatlistService{

	@Autowired
	private IChatlistRepository chatlistRepo;
	@Override
	public boolean insert(String roomname) {
		return chatlistRepo.insert(roomname);
	}

	@Override
	public String select(String no) {
		return chatlistRepo.select(no);
	}

	@Override
	public String selectno(String roomname) {
		return chatlistRepo.selectno(roomname);
	}

}
