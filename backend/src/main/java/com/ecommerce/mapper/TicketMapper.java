package com.ecommerce.mapper;

import java.util.List;

import com.ecommerce.domain.Ticket;
import com.ecommerce.domain.TicketDetail;
import com.ecommerce.domain.TicketList;

public interface TicketMapper {
	List<Ticket> getByPid(long pid);
	List<TicketList> getByUid(long uid);
	List<Ticket> getByPidAndDateAndTime(long pid, String date, String time);
	Ticket getByPidAndDateAndTimeAndSeatNumber(long pid, String date, String time, int seatNumber);
	TicketDetail get(long tid);
	long create(Ticket ticket);
	int update(long tid, long uid);
	Ticket getByTid(long tid);
	int updateContract(long tid, String contractAddress);
}
