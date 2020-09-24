package com.ecommerce.application;

import com.ecommerce.domain.Deal;
import com.ecommerce.domain.DealDetail;
import com.ecommerce.domain.DealList;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface IDealService {
	List<DealList> list();
	DealDetail get(long did);
	@Transactional
	DealDetail create(Deal deal);
	List<DealList> getBySeller(long seller);
}