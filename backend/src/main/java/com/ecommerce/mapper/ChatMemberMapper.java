package com.ecommerce.mapper;
import java.util.List;

import org.springframework.stereotype.Repository;

public interface ChatMemberMapper {
	public boolean insert(String no, String userName);

	public List<String> selectAll(String userName);

	public String select(String myName, String yourName);
}
