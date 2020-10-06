package com.ecommerce.mapper;

import java.util.List;
import com.ecommerce.domain.Deal;
import com.ecommerce.domain.DealDetail;
import com.ecommerce.domain.DealList;

public interface DealMapper {
	List<DealList> list();
	DealDetail get(long did);
	long create(Deal deal);
	List<DealList> getBySeller(long seller);
	int update(long did, long buyer);
}
