package com.ecommerce.application;

import com.ecommerce.domain.Ticket;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface ITicketService {
	List<Ticket> getByPid(long uid);
	List<Ticket> getByUid(long uid);
	@Transactional
	Ticket get(long tid);
	@Transactional
	Ticket create(Ticket ticket);
	
}