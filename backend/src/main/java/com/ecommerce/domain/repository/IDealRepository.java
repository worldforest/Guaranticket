package com.ecommerce.domain.repository;

import com.ecommerce.domain.Deal;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IDealRepository
{
	List<Deal> list();
	Deal get(long did);
	
	@Transactional
	long create(Deal deal);

}
