package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketFactory
{
	public static Ticket create(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		Ticket ticket = new Ticket();
		ticket.setPid(rs.getLong("pid"));
		ticket.setSeatNumber(rs.getInt("seat_number"));
		ticket.setDate(rs.getString("date"));
		ticket.setTime(rs.getString("time"));
		ticket.setUid(rs.getLong("uid"));
		ticket.setTid(rs.getLong("tid"));
		return ticket;
	}
}
