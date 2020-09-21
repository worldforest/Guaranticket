package com.ecommerce.application.impl;

import com.ecommerce.application.IDealService;
import com.ecommerce.application.ITicketService;
import com.ecommerce.domain.Deal;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.Ticket;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.repository.IDealRepository;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.domain.repository.ITicketRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService implements ITicketService
{
	public static final Logger logger = LoggerFactory.getLogger(TicketService.class);

	private ITicketRepository iTicketRepository;

	@Autowired
	public TicketService(ITicketRepository iTicketRepository) {
		this.iTicketRepository = iTicketRepository;
	}

	@Override
	public List<Ticket> getByUid(long uid) {
		// TODO Auto-generated method stub
		return this.iTicketRepository.getByUid(uid);
	}

	@Override
	public Ticket create(Ticket ticket) {
		return null;
	}

	@Override
	public List<Ticket> getByPid(long pid) {
		// TODO Auto-generated method stub
		return this.iTicketRepository.getByPid(pid);
	}


}
