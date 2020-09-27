package com.ecommerce.api;

import com.ecommerce.application.IDealService;
import com.ecommerce.domain.Deal;
import com.ecommerce.domain.DealDetail;
import com.ecommerce.domain.DealList;
import com.ecommerce.domain.exception.NotFoundException;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
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
	public List<DealList> list() {
		List<DealList> list = dealService.list();

		if (list == null || list.isEmpty())
			throw new NotFoundException("거래 정보를 찾을 수 없습니다.");

		return list;
	}
	
	@ApiOperation(value = "거래번호별 거래 상세보기")
	@RequestMapping(value = "/deal/did/{did}", method = RequestMethod.GET)
	public DealDetail getByDid(@PathVariable long did) {
		DealDetail dealDetail = dealService.get(did);
		if (dealDetail == null) {
			logger.error("NOT FOUND ID: ", did);
			throw new NotFoundException(did + " 거래 정보를 찾을 수 없습니다.");
		}
		return dealDetail;
	}
	
	@ApiOperation(value = "거래 등록")
	@RequestMapping(value = "/deal", method = RequestMethod.POST)
	public DealDetail create(@RequestBody Deal deal) {
		return dealService.create(deal);
	}
	
	@ApiOperation(value = "사용자 판매내역 보기")
	@RequestMapping(value = "/deal/seller/{seller}", method = RequestMethod.GET)
	public List<DealList> getBySeller(@PathVariable long seller) {
		List<DealList> list = dealService.getBySeller(seller);

		if (list == null || list.isEmpty())
			throw new NotFoundException(seller + " 판매 내역 정보를 찾을 수 없습니다.");

		return list;
	}
}
