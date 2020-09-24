package com.ecommerce.domain.repository;

import com.ecommerce.domain.Ticket;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface ITicketRepository
{
	List<Ticket> getByPid(long pid);
	List<Ticket> getByUid(long uid);
	List<Ticket> getByPidAndDateAndTime(long pid, String date, String time);
	@Transactional
	Ticket get(long tid);
	@Transactional
	long create(Ticket ticket);

}
