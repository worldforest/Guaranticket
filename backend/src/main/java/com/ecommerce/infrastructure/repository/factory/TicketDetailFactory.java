package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.TicketDetail;
import com.ecommerce.domain.TicketList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDetailFactory
{
	public static TicketDetail create(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		TicketDetail ticketDetail = new TicketDetail();
		ticketDetail.setPid(rs.getLong("pid"));
		ticketDetail.setSeatNumber(rs.getInt("seat_number"));
		ticketDetail.setDate(rs.getString("date"));
		ticketDetail.setTime(rs.getString("time"));
		ticketDetail.setUid(rs.getLong("uid"));
		ticketDetail.setTid(rs.getLong("tid"));
		ticketDetail.setGrade(rs.getString("grade"));
		ticketDetail.setPrice(rs.getString("price"));
		ticketDetail.setCategory(rs.getNString("category"));
		ticketDetail.setTitle(rs.getString("title"));
		ticketDetail.setPoster(rs.getString("poster"));
		ticketDetail.setPlace(rs.getString("place"));
		
		return ticketDetail;
	}
}
