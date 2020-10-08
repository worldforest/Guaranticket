package com.ecommerce.application.impl;

import com.ecommerce.application.IDealService;
import com.ecommerce.domain.Deal;
import com.ecommerce.domain.DealDetail;
import com.ecommerce.domain.DealList;
import com.ecommerce.mapper.DealMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealService implements IDealService
{
	public static final Logger logger = LoggerFactory.getLogger(DealService.class);

	@Autowired
	private DealMapper dealMapper;
	
	@Override
	public DealDetail get(long did) {
		// TODO Auto-generated method stub
		return this.dealMapper.get(did);
	}

	@Override
	public DealDetail create(Deal deal) {
		long did = this.dealMapper.create(deal);
		return this.dealMapper.get(did);
	}

	@Override
	public List<DealList> list() {
		// TODO Auto-generated method stub
		return this.dealMapper.list();
	}

	@Override
	public List<DealList> getBySeller(long seller) {
		// TODO Auto-generated method stub
		return this.dealMapper.getBySeller(seller);
	}

	@Override
	public DealDetail update(long did, long buyer) {
		int res = this.dealMapper.update(did, buyer);
		return this.get(did);
	}

}
