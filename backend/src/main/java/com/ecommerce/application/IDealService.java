package com.ecommerce.application;

import com.ecommerce.domain.Deal;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface IDealService {
	List<Deal> list();
	Deal get(long did);
	@Transactional
	Deal create(Deal deal);
	
}