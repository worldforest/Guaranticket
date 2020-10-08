package com.ecommerce.application.impl;

import com.ecommerce.application.ITicketService;
import com.ecommerce.domain.Ticket;
import com.ecommerce.domain.TicketDetail;
import com.ecommerce.domain.TicketList;
import com.ecommerce.mapper.TicketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService implements ITicketService
{
	public static final Logger logger = LoggerFactory.getLogger(TicketService.class);

	@Autowired
	private TicketMapper ticketMapper;
	
	@Override
	public Ticket getByPidAndDateAndTimeAndSeatNumber(long pid, String date, String time, int seatNumber) {
		// TODO Auto-generated method stub
		return this.ticketMapper.getByPidAndDateAndTimeAndSeatNumber(pid, date, time, seatNumber);
	}
	
	@Override
	public List<TicketList> getByUid(long uid) {
		System.out.println(this.ticketMapper.getByUid(uid));
		return this.ticketMapper.getByUid(uid);
	}

	@Override
	public TicketDetail create(Ticket ticket) {
		long tid = this.ticketMapper.create(ticket);
		return this.ticketMapper.get(tid);
	}

	@Override
	public List<Ticket> getByPid(long pid) {
		// TODO Auto-generated method stub
		return this.ticketMapper.getByPid(pid);
	}

	@Override
	public TicketDetail get(long tid) {
		// TODO Auto-generated method stub
		return this.ticketMapper.get(tid);
	}
	
	@Override
	public Ticket getByTid(long tid) {
		return this.ticketMapper.getByTid(tid);
	}

	@Override
	public List<Ticket> getByPidAndDateAndTime(long pid, String date, String time) {
		// TODO Auto-generated method stub
		return this.ticketMapper.getByPidAndDateAndTime(pid, date, time);
	}

	@Override
	public TicketDetail update(long tid, long uid) {
		int res = this.ticketMapper.update(tid, uid);
		return this.get(tid);
	}

	@Override
	public Ticket updateContract(long tid, String contractAddress) {
		int affected = this.ticketMapper.updateContract(tid, contractAddress);
		return this.ticketMapper.getByTid(tid);
	}


}
