package com.ecommerce.domain.repository;

import com.ecommerce.domain.Deal;
import com.ecommerce.domain.DealDetail;
import com.ecommerce.domain.DealList;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IDealRepository
{
	List<DealList> list();
	DealDetail get(long did);
	
	@Transactional
	long create(Deal deal);
	List<DealList> getBySeller(long seller);
}
