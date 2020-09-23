package com.ecommerce.application.impl;

import com.ecommerce.application.IDealService;
import com.ecommerce.domain.Deal;
import com.ecommerce.domain.DealJoinData;
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
	public Deal get(long did) {
		// TODO Auto-generated method stub
		return this.idealDealRepository.get(did);
	}

	@Override
	public Deal create(Deal deal) {
		long did = this.idealDealRepository.create(deal);
		return this.idealDealRepository.get(did);
	}

	@Override
	public List<DealJoinData> list() {
		// TODO Auto-generated method stub
		return this.idealDealRepository.list();
	}

	@Override
	public List<DealJoinData> getBySeller(long seller) {
		// TODO Auto-generated method stub
		return this.idealDealRepository.getBySeller(seller);
	}

}
