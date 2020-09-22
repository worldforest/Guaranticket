package com.ecommerce.domain.repository;

import com.ecommerce.domain.Ticket;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface ITicketRepository
{
	List<Ticket> getByPid(long pid);
	List<Ticket> getByUid(long uid);
	@Transactional
	Ticket get(long tid);
	@Transactional
	long create(Ticket ticket);

}
