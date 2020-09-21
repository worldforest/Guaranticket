package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.Deal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DealFactory
{
	public static Deal create(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		Deal deal = new Deal();
		deal.setDid(rs.getLong("did"));
		deal.setTid(rs.getLong("tid"));
		deal.setBuyer(rs.getLong("buyer"));
		deal.setSeller(rs.getLong("seller"));
		deal.setDate(rs.getDate("date").toString());
		deal.setTime(rs.getTime("time").toString());
		deal.setGrade(rs.getString("grade"));
		deal.setPrice(rs.getString("price"));
		return deal;
	}
}
