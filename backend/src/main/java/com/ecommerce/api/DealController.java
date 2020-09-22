package com.ecommerce.api;

import com.ecommerce.application.IDealService;
import com.ecommerce.application.IPerformanceService;
import com.ecommerce.application.IPerformanceSubmissonService;
import com.ecommerce.domain.Deal;
import com.ecommerce.domain.DealJoinData;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.Performance;
import com.ecommerce.domain.PerformancePrice;
import com.ecommerce.domain.PerformanceSubmission;
import com.ecommerce.domain.User;
import com.ecommerce.domain.exception.EmptyListException;
import com.ecommerce.domain.exception.NotFoundException;
import com.ecommerce.domain.repository.IPerformanceSubmissionRepository;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class DealController
{
	public static final Logger logger = LoggerFactory.getLogger(DealController.class);

	private IDealService dealService;

	@Autowired
	public DealController(IDealService dealService) {
		Assert.notNull(dealService, "dealService 개체가 반드시 필요!");
		this.dealService = dealService;
	}

	@ApiOperation(value = "모든 거래 검색")
	@RequestMapping(value = "/deal", method = RequestMethod.GET)
	public List<DealJoinData> list() {
		List<DealJoinData> list = dealService.list();

		if (list == null || list.isEmpty())
			throw new NotFoundException("거래 정보를 찾을 수 없습니다.");

		return list;
	}
	
	@ApiOperation(value = "거래번호별 거래 상세보기")
	@RequestMapping(value = "/deal/did/{did}", method = RequestMethod.GET)
	public Deal getByDid(@PathVariable long did) {
		Deal deal = dealService.get(did);
		if (deal == null) {
			logger.error("NOT FOUND ID: ", did);
			throw new NotFoundException(did + " 거래 정보를 찾을 수 없습니다.");
		}
		return deal;
	}
	
	@ApiOperation(value = "거래 등록")
	@RequestMapping(value = "/deal", method = RequestMethod.POST)
	public Deal create(@RequestBody Deal deal) {
		return dealService.create(deal);
	}
	
	@ApiOperation(value = "사용자 판매내역 보기")
	@RequestMapping(value = "/deal/seller/{seller}", method = RequestMethod.GET)
	public List<Deal> getBySeller(@PathVariable long seller) {
		List<Deal> list = dealService.getBySeller(seller);

		if (list == null || list.isEmpty())
			throw new NotFoundException(seller + " 판매 내역 정보를 찾을 수 없습니다.");

		return list;
	}
}
