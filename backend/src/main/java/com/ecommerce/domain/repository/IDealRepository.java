package com.ecommerce.domain.repository;

import com.ecommerce.domain.Deal;
import com.ecommerce.domain.DealJoinData;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IDealRepository
{
	List<DealJoinData> list();
	Deal get(long did);
	
	@Transactional
	long create(Deal deal);
	List<DealJoinData> getBySeller(long seller);
}
