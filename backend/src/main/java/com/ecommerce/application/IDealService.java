package com.ecommerce.application;

import com.ecommerce.domain.Deal;
import com.ecommerce.domain.DealJoinData;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface IDealService {
	List<DealJoinData> list();
	Deal get(long did);
	@Transactional
	Deal create(Deal deal);
	List<Deal> getBySeller(long seller);
}