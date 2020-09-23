package com.ecommerce.mapper;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ChatMemberMapper {
	public boolean insert(String no, String name);

	public List<String> selectAll(String name);

	public String select(String myname, String yourname);
}
