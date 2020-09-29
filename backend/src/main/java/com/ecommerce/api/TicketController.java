package com.ecommerce.api;

import com.ecommerce.application.IJwtService;
import com.ecommerce.application.ITicketService;
import com.ecommerce.domain.Ticket;
import com.ecommerce.domain.TicketDetail;
import com.ecommerce.domain.TicketList;
import com.ecommerce.domain.exception.NotFoundException;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TicketController
{
	public static final Logger logger = LoggerFactory.getLogger(TicketController.class);

	private ITicketService ticketService;
	private IJwtService jwtService;
	
	@Autowired
	public TicketController(ITicketService ticketService,
			IJwtService jwtService) {
		Assert.notNull(ticketService, "dealService 개체가 반드시 필요!");
		this.ticketService = ticketService;
		this.jwtService = jwtService;
	}

	@ApiOperation(value = "공연(날짜+시간)별 예매 내역 검색")
	@RequestMapping(value = "/ticket/pid/{pid}/date/{date}/time/{time}", method = RequestMethod.GET)
	public List<Ticket> getByPidAndDateAndTime(@PathVariable long pid,
			@PathVariable String date,
			@PathVariable String time) {
		List<Ticket> list = ticketService.getByPidAndDateAndTime(pid,date,time);
		if (list == null || list.isEmpty()) {
			throw new NotFoundException(pid + "공연의 "+date+" 날짜의 예매 내역을 찾을 수 없습니다.");
		}
		return list;
	}
	@ApiOperation(value = "공연별 예매 내역 검색")
	@RequestMapping(value = "/ticket/pid/{pid}", method = RequestMethod.GET)
	public List<Ticket> getByPid(@PathVariable long pid) {
		
		List<Ticket> list = ticketService.getByPid(pid);

		if (list == null || list.isEmpty())
			throw new NotFoundException(pid + "공연의 예매 내역을 찾을 수 없습니다.");

		return list;
	}
	@ApiOperation(value = "사용자 예매 내역리스트 검색")
	@RequestMapping(value = "/ticket/uid", method = RequestMethod.GET)
	public List<TicketList> getByUid(HttpServletRequest request) {
		String token = request.getHeader("jwt-auth-token");
		Map<String,Object>userinfo = jwtService.get(token);
		long uid = (Long.parseLong(userinfo.get("USER").toString()));
		
		List<TicketList> list = ticketService.getByUid(uid);
		if (list == null || list.isEmpty())
			throw new NotFoundException(uid + "유저의 예매 내역을 찾을 수 없습니다.");
		return list;
	}
	
	@ApiOperation(value = "공연 예매 하기")
	@RequestMapping(value = "/ticket", method = RequestMethod.POST)
	public TicketDetail create(@RequestBody Ticket ticket, HttpServletRequest request) {
		String token = request.getHeader("jwt-auth-token");
		Map<String,Object>userinfo = jwtService.get(token);
		long uid = (Long.parseLong(userinfo.get("USER").toString()));
		ticket.setUid(uid);
		return ticketService.create(ticket);
	}
	
	@ApiOperation(value = "티켓 상세정보 검색")
	@RequestMapping(value = "/ticket/tid/{tid}", method = RequestMethod.GET)
	public TicketDetail getBytid(@PathVariable long tid) {
		
		TicketDetail ticketDetail = ticketService.get(tid);

		if (ticketDetail == null)
			throw new NotFoundException(tid + "의 티켓 정보를 찾을 수 없습니다.");

		return ticketDetail;
	}
}
