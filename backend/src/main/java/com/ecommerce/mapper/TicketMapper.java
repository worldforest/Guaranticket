package com.ecommerce.mapper;

import java.util.List;

import com.ecommerce.domain.Ticket;
import com.ecommerce.domain.TicketDetail;
import com.ecommerce.domain.TicketList;

public interface TicketMapper {
	List<Ticket> getByPid(long pid);
	List<TicketList> getByUid(long uid);
	List<Ticket> getByPidAndDateAndTime(long pid, String date, String time);
	TicketDetail get(long tid);
	long create(Ticket ticket);
}
