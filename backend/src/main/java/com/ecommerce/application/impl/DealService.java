package com.ecommerce.application.impl;

import com.ecommerce.application.IDealService;
import com.ecommerce.domain.Deal;
import com.ecommerce.domain.DealDetail;
import com.ecommerce.domain.DealList;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.repository.IDealRepository;
import com.ecommerce.domain.repository.IItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealService implements IDealService
{
	public static final Logger logger = LoggerFactory.getLogger(DealService.class);

	private IDealRepository idealDealRepository;

	@Autowired
	public DealService(IDealRepository idealDealRepository) {
		this.idealDealRepository = idealDealRepository;
	}

	@Override
	public DealDetail get(long did) {
		// TODO Auto-generated method stub
		return this.idealDealRepository.get(did);
	}

	@Override
	public DealDetail create(Deal deal) {
		long did = this.idealDealRepository.create(deal);
		return this.idealDealRepository.get(did);
	}

	@Override
	public List<DealList> list() {
		// TODO Auto-generated method stub
		return this.idealDealRepository.list();
	}

	@Override
	public List<DealList> getBySeller(long seller) {
		// TODO Auto-generated method stub
		return this.idealDealRepository.getBySeller(seller);
	}

}
