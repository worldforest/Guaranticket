package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.TicketList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketListFactory
{
	public static TicketList create(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		TicketList ticket = new TicketList();
		ticket.setPid(rs.getLong("pid"));
		ticket.setSeatNumber(rs.getInt("seat_number"));
		ticket.setDate(rs.getString("date"));
		ticket.setTime(rs.getString("time"));
		ticket.setUid(rs.getLong("uid"));
		ticket.setTid(rs.getLong("tid"));
		ticket.setGrade(rs.getString("grade"));
		ticket.setPrice(rs.getString("price"));
		ticket.setCategory(rs.getNString("category"));
		ticket.setTitle(rs.getString("title"));
		return ticket;
	}
}
