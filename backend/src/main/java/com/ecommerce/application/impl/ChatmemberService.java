package com.ecommerce.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.application.IChatmemberService;
import com.ecommerce.domain.repository.IChatmemberRepository;
@Service
public class ChatmemberService implements IChatmemberService{

	@Autowired
	private IChatmemberRepository chatmemberRepo;
	@Override
	public boolean insert(String no, String name) {
		return chatmemberRepo.insert(no, name);
	}

	@Override
	public List<String> selectAll(String name) {
		return chatmemberRepo.selectAll(name);
	}

	@Override
	public String select(String myname, String yourname) {
		return chatmemberRepo.select(myname, yourname);
	}

}
