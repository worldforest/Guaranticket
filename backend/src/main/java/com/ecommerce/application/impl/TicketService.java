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
	private TicketMapper TicketMapper;
	
	@Override
	public List<TicketList> getByUid(long uid) {
		System.out.println(this.TicketMapper.getByUid(uid));
		return this.TicketMapper.getByUid(uid);
	}

	@Override
	public TicketDetail create(Ticket ticket) {
		long tid = this.TicketMapper.create(ticket);
		return this.TicketMapper.get(tid);
	}

	@Override
	public List<Ticket> getByPid(long pid) {
		// TODO Auto-generated method stub
		return this.TicketMapper.getByPid(pid);
	}

	@Override
	public TicketDetail get(long tid) {
		// TODO Auto-generated method stub
		return this.TicketMapper.get(tid);
	}

	@Override
	public List<Ticket> getByPidAndDateAndTime(long pid, String date, String time) {
		// TODO Auto-generated method stub
		return this.TicketMapper.getByPidAndDateAndTime(pid, date, time);
	}

	@Override
	public TicketDetail update(long tid, long uid) {
		// TODO Auto-generated method stub
		int res = this.TicketMapper.update(tid, uid);
		return this.get(tid);
	}


}
