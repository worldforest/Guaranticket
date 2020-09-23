package com.ecommerce.domain.repository;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface IChatmemberRepository {
	public boolean insert(String no, String name);

	public List<String> selectAll(String name);

	public String select(String myname, String yourname);
}
