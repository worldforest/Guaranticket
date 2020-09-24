package com.ecommerce.domain.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface IChatlistRepository {
	public boolean insert(String roomname);

	public String select(String no);

	public String selectno(String roomname);
}
