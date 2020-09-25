package com.ecommerce.application;

import com.ecommerce.domain.Ticket;
import com.ecommerce.domain.TicketDetail;
import com.ecommerce.domain.TicketList;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface ITicketService {
	List<Ticket> getByPid(long uid);
	List<TicketList> getByUid(long uid);
	List<Ticket> getByPidAndDateAndTime(long pid,String date, String time);
	@Transactional
	TicketDetail get(long tid);
	@Transactional
	TicketDetail create(Ticket ticket);
	
}