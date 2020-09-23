package com.ecommerce.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.application.IChatmemberService;
import com.ecommerce.domain.repository.IChatmemberRepository;
import com.ecommerce.mapper.ChatMemberMapper;

@Service
public class ChatmemberService implements IChatmemberService {

	@Autowired
	private ChatMemberMapper chatmemberMapper;

	@Override
	public boolean insert(String no, String name) {
		return chatmemberMapper.insert(no, name);
	}

	@Override
	public List<String> selectAll(String name) {
		return chatmemberMapper.selectAll(name);
	}

	@Override
	public String select(String myname, String yourname) {
		return chatmemberMapper.select(myname, yourname);
	}

}
