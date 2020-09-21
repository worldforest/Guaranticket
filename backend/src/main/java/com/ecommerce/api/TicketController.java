package com.ecommerce.api;

import com.ecommerce.application.IDealService;
import com.ecommerce.application.IPerformanceService;
import com.ecommerce.application.IPerformanceSubmissonService;
import com.ecommerce.application.ITicketService;
import com.ecommerce.domain.Deal;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.Performance;
import com.ecommerce.domain.PerformancePrice;
import com.ecommerce.domain.PerformanceSubmission;
import com.ecommerce.domain.Ticket;
import com.ecommerce.domain.User;
import com.ecommerce.domain.exception.EmptyListException;
import com.ecommerce.domain.exception.NotFoundException;
import com.ecommerce.domain.repository.IPerformanceSubmissionRepository;
import com.ecommerce.domain.repository.ITicketRepository;

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
public class TicketController
{
	public static final Logger logger = LoggerFactory.getLogger(TicketController.class);

	private ITicketService ticketService;

	@Autowired
	public TicketController(ITicketService ticketService) {
		Assert.notNull(ticketService, "dealService 개체가 반드시 필요!");
		this.ticketService = ticketService;
	}

	@ApiOperation(value = "공연별 모든 예매 검색")
	@RequestMapping(value = "/ticket/pid/{pid}", method = RequestMethod.GET)
	public List<Ticket> getByPid(@PathVariable long pid) {
		List<Ticket> list = ticketService.getByPid(pid);

		if (list == null || list.isEmpty()) {
			throw new NotFoundException(pid + "공연의 예매 내역을 찾을 수 없습니다.");
		}

		return list;
	}
	
	@ApiOperation(value = "사용자 예매 내역 검색")
	@RequestMapping(value = "/ticket/uid/{uid}", method = RequestMethod.GET)
	public List<Ticket> getByUid(@PathVariable long uid) {
		
		List<Ticket> list = ticketService.getByUid(uid);

		if (list == null || list.isEmpty())
			throw new NotFoundException(uid + "유저의 예매 내역을 찾을 수 없습니다.");

		return list;
	}
	
//	@ApiOperation(value = "예매 하기")
//	@RequestMapping(value = "/ticket/{uid}", method = RequestMethod.POST)
//	public Deal create(@PathVariable long uid) {
//		return ticketService.create(deal);
//	}
	
}
